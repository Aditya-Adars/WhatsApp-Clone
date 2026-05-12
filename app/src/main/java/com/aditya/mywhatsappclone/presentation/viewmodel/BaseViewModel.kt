package com.aditya.mywhatsappclone.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import com.aditya.mywhatsappclone.model.Message
import com.aditya.mywhatsappclone.presentation.chatbox.ChatDesignModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class BaseViewModel: ViewModel() {

    fun searchUserByPhoneNumber(phoneNumber: String, callback: (ChatDesignModel?) -> Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null){
            Log.e("BaseViewModel", "User not authenticated")
            callback(null)
            return
        }

        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.orderByChild("phoneNumber").equalTo(phoneNumber)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        val user =
                            dataSnapshot.children.first().getValue(ChatDesignModel::class.java)
                        callback(user)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error fetching user: ${error.message}, Details : ${error.details}")
                    callback(null)
                }
            })
    }

    fun getChatForUser(uid: String, callback: (List<ChatDesignModel>) -> Unit) {
        val chatRef= FirebaseDatabase.getInstance().getReference("users/$uid/chats")
        chatRef.orderByChild("uid").equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatList = mutableListOf<ChatDesignModel>()
                    for (chatSnapshot in snapshot.children) {
                        val chat = chatSnapshot.getValue(ChatDesignModel::class.java)
                        chat?.let { chatList.add(it) }
                    }
                    callback(chatList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseViewModel", "Error fetching chats: ${error.message}")
                    callback(emptyList())
                }
            })

    }

    private val _chatList = MutableStateFlow<List<ChatDesignModel>>(emptyList())
    val chatList = _chatList.asStateFlow()

    init {
        loadChatData()
    }

    private fun loadChatData(){
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId!=null){
            val chatRef = FirebaseDatabase.getInstance().getReference("chats")
            chatRef.orderByChild("uid").equalTo(currentUserId)
                .addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val chatList = mutableListOf<ChatDesignModel>()

                        for (chatSnapshot in snapshot.children){
                            val chat = chatSnapshot.getValue(ChatDesignModel::class.java)
                            if (chat!=null){
                                chatList.add(chat)

                            }
                        }
                        _chatList.value=chatList
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("BaseViewModel", "Error fetching chats: ${error.message}")
                    }
                })
        }
    }

    fun addChat(newChat: ChatDesignModel){

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (currentUserId!=null){
            val newChatRef = FirebaseDatabase.getInstance().getReference("chats").push()
            val chatWithUser = newChat.copy(userId = currentUserId)
            newChatRef.setValue(chatWithUser).addOnSuccessListener {
                Log.d("BaseViewModel", "Chat added successfully to firebase")
            }.addOnFailureListener { exception ->
                Log.e("BaseViewModel", "Error adding chat to firebase: ${exception.message}")
            }

        }else{
            Log.e("BaseViewModel", "User not authenticated")
        }

    }

    private val dataBaseReference=FirebaseDatabase.getInstance().reference

    fun sendMessage(senderPhoneNumber: String, receiverPhoneNumber: String, messageText: String){
        val messageId = dataBaseReference.push().key?: return
        val message =
            Message(
                senderPhoneNumber = senderPhoneNumber,
                receiverPhoneNumber = receiverPhoneNumber,
                message = messageText,
                timestamp = System.currentTimeMillis()
            )
        dataBaseReference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)
            .child(messageId)
            .setValue(message)

        dataBaseReference.child("messages")
            .child(receiverPhoneNumber)
            .child(senderPhoneNumber)
            .child(messageId)
            .setValue(message)


    }

    fun getMessage(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        newMessage: (Message) -> Unit
    ){
        val messageRef = dataBaseReference.child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        messageRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if (message!=null){
                    newMessage(message)
                }

            }

            override fun onChildChanged(
                snapshot:  DataSnapshot,
                previousChildName: String?
            ) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    fun fetchLastMessageForChat(
        senderPhoneNumber: String,
        receiverPhoneNumber: String,
        onLastMessageFetched: (String, String) -> Unit

    ){
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("messages")
            .child(senderPhoneNumber)
            .child(receiverPhoneNumber)

        chatRef.orderByChild("timestamp").limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val firstChild = snapshot.children.firstOrNull()
                        val lastMessage = firstChild?.child("message")?.value as? String
                        val timestampLong = firstChild?.child("timestamp")?.value as? Long
                        
                        val formattedTime = timestampLong?.let {
                            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(it))
                        } ?: "--:--"
                        
                        onLastMessageFetched(lastMessage?:"No message", formattedTime)
                    }else{
                        onLastMessageFetched("No message", "--:--")

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onLastMessageFetched("No message", "--:--")
                }
            })
    }

    fun loadChatList(
        currentUserPhoneNumber: String,
        onChatListLoaded: (List<ChatDesignModel>) -> Unit
    ){
        val chatList = mutableListOf<ChatDesignModel>()
        val chatRef = FirebaseDatabase.getInstance().reference
            .child("chats")
            .child(currentUserPhoneNumber)

        chatRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val childrenCount = snapshot.childrenCount.toInt()
                    snapshot.children.forEach { child->
                        val phoneNumber = child.key?: return@forEach
                        val name = child.child("name").value as? String?: "Unknown"
                        val profileImageStr = child.child("profileImage").value as? String ?: child.child("image").value as? String

                        fetchLastMessageForChat(currentUserPhoneNumber, phoneNumber){lastMessage, timestamp->
                            chatList.add(
                                ChatDesignModel(
                                    name = name,
                                    phoneNumber = phoneNumber,
                                    message = lastMessage,
                                    time = timestamp,
                                    profileImage = profileImageStr
                                )
                            )
                            if (chatList.size == childrenCount){
                                onChatListLoaded(chatList)
                            }
                        }

                    }
                }else{
                    onChatListLoaded(emptyList())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onChatListLoaded(emptyList())
            }

        })

    }

    private fun decodeBase64ToBitmap(base64Image:String): Bitmap?{
        return try {
            val bytes = Base64.decode(base64Image, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }catch (e: Exception){
            null
        }
    }

    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            val inputStream: InputStream = ByteArrayInputStream(imageBytes)
            BitmapFactory.decodeStream(inputStream)
        }catch (e: Exception){
            null
        }
    }

}
