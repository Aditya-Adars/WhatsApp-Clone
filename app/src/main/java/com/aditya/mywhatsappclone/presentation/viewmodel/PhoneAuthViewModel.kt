package com.aditya.mywhatsappclone.presentation.viewmodel

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.aditya.mywhatsappclone.model.PhoneAuthUser
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import androidx.core.content.edit


@OptIn(ExperimentalEncodingApi::class)
@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Ideal)
    val authState = _authState.asStateFlow()

    private val userRef = firebaseDatabase.reference.child("users")

    fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        _authState.value = AuthState.Loading

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                Log.d("PhoneAuth", "onCodeSent triggered. verification id: $id")
                _authState.value = AuthState.CodeSent(id)
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signWithCredential(credential, activity)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                Log.d("PhoneAuth", "onVerificationFailed: ${exception.message}")
                _authState.value = AuthState.Error(exception.message.toString())
            }
        }

        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signWithCredential(credential: PhoneAuthCredential, activity: Activity) {
        _authState.value = AuthState.Loading

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val phoneAuthUser = PhoneAuthUser(
                        uid = user?.uid ?: "",
                        phoneNumber = user?.phoneNumber ?: ""
                    )

                    markUserAsSignedIn(activity)
                    _authState.value = AuthState.Success(phoneAuthUser)

                    fetchUserProfile(user?.uid ?: "")
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Signed-in failed")
                }
            }
    }

    private fun markUserAsSignedIn(activity: Activity){
        val sharedPreferences = activity.getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)
        sharedPreferences.edit { putBoolean("isSigned", true).apply() }
    }

    private fun fetchUserProfile(uid: String) {
        userRef.child(uid).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val user = snapshot.getValue(PhoneAuthUser::class.java)
                    user?.let {
                        _authState.value = AuthState.Success(it)
                    }
                } else {
                    _authState.value = AuthState.Error("User profile not found")
                }
            }
    }

    fun verifyCode(otp: String, activity: Activity) {
        val currentAuthState = _authState.value

        if (currentAuthState !is AuthState.CodeSent || currentAuthState.verificationId.isEmpty()) {
            Log.e("PhoneAuth", "Invalid verification ID")
            _authState.value = AuthState.Error("Invalid verification ID")
            return
        }
        val credential = PhoneAuthProvider.getCredential(currentAuthState.verificationId, otp)
        signWithCredential(credential, activity)
    }

    fun saveUserProfile(uid: String, name: String, status: String, image: Bitmap?) {
        val database = FirebaseDatabase.getInstance().reference

        val encodedImage = image?.let { convertBitmapToBase64(it) }
        val userProfile = PhoneAuthUser(
            uid = uid,
            name = name,
            status = status,
            phoneNumber = Firebase.auth.currentUser?.phoneNumber ?: "",
            image = encodedImage,
        )
        database.child("users").child(uid).setValue(userProfile)
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encode(byteArray)
    }

    fun resetAuthState(){
        _authState.value = AuthState.Ideal
    }

    fun signOut(activity: Activity){
        firebaseAuth.signOut()
        val sharedPreferences = activity.getSharedPreferences("MyPrefs", Activity.MODE_PRIVATE)
        sharedPreferences.edit { putBoolean("isSigned", false).apply() }
    }
}

sealed class AuthState {
    object Ideal : AuthState()
    object Loading : AuthState()
    data class CodeSent(val verificationId: String) : AuthState()
    data class Success(val user: PhoneAuthUser) : AuthState()
    data class Error(val message: String) : AuthState()
}
