package com.aditya.mywhatsappclone.presentation.userregistration


import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.aditya.mywhatsappclone.R
import com.aditya.mywhatsappclone.presentation.navigation.Routes
import com.aditya.mywhatsappclone.presentation.viewmodel.AuthState
import com.aditya.mywhatsappclone.presentation.viewmodel.PhoneAuthViewModel


@Composable
fun UserRegisterScreen(
    navHostController: NavHostController,
    phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()
) {


    val authState by phoneAuthViewModel.authState.collectAsState()
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    var otp by remember {
        mutableStateOf("")
    }

    var verificationId by remember {
        mutableStateOf<String?>(null)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedCountry by remember {
        mutableStateOf("India")
    }
    var countryCode by remember {
        mutableStateOf("+91")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter your phone number",
            color = colorResource(id = R.color.dark_green),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(text = "Whatsapp will need to verify your phone number")

            Spacer(modifier = Modifier.width(4.dp))

        }
        Row {
            Text(text = "What's", color = colorResource(id = R.color.light_green))

            Spacer(modifier = Modifier.width(4.dp))

            Text(text = "my number?", color = colorResource(id = R.color.light_green))
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.width(300.dp)) {
                Text(
                    text = selectedCountry,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    color = Color.Black,
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    tint = colorResource(id = R.color.light_green)

                )
            }

        }
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 66.dp),
            thickness = 2.dp,
            color = colorResource(id = R.color.light_green)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(200.dp)
        ) {
            listOf(
                "India",
                "Japan",
                "Canada",
                "USA",
                "China",
                "Australia",
                "Britain",
                "America",
                "Russia"
            ).forEach { country ->
                DropdownMenuItem(text = { Text(text = country) }, onClick = {
                    selectedCountry = country
                    expanded = false
                })
            }
        }

        when (val state = authState) {
            is AuthState.Ideal, is AuthState.Loading, is AuthState.CodeSent -> {
                if (state is AuthState.CodeSent) {
                    verificationId = state.verificationId

                }
                if (verificationId == null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = countryCode,
                            onValueChange = { countryCode = it },
                            modifier = Modifier.width(70.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(id = R.color.light_green)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        TextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = { Text("Phone Number") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = colorResource(id = R.color.light_green),
                            )
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (phoneNumber.isNotEmpty()) {
                                val fullPhoneNumber = "$countryCode$phoneNumber"
                                phoneAuthViewModel.sendVerificationCode(fullPhoneNumber, activity)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter your valid phone number",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }, shape = RoundedCornerShape(6.dp), colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.dark_green)
                        )
                    ) {

                        Text("Send OTP")
                    }

                    if (state is AuthState.Loading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }
                } else {

                    // OTP input UI
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        "Enter Otp",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.dark_green)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = otp,
                        onValueChange = { otp = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("OTP") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = R.color.light_green),
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(onClick = {

                        if (otp.isNotEmpty() && verificationId != null){
                            phoneAuthViewModel.verifyCode(otp, activity)

                        }else{
                            Toast.makeText(context, "Please enter valid otp", Toast.LENGTH_SHORT).show()
                        }
                    }, shape = RoundedCornerShape(6.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.dark_green)
                    )) {

                        Text("Verify OTP")
                    }
                    if (state is AuthState.Loading){
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }
                }

            }

            is AuthState.Success -> {
                Log.d("PhoneAuth", "LoginSuccessful")

                phoneAuthViewModel.resetAuthState()

                navHostController.navigate(Routes.UserProfileSetScreen){
                    popUpTo(Routes.UserRegistration){
                        inclusive = true
                    }
                }
            }
            is AuthState.Error -> {
                Log.d("PhoneAuth", "LoginError: ${state.message}")
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }

    }
}
