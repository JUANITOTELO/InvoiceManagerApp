package com.devdavidm.invoicemanagerapp.forgotpasswordpage

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devdavidm.invoicemanagerapp.loginpage.TextInput
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordPage(context: ComponentActivity, navController: NavController, auth: FirebaseAuth){
    Surface(color = Color(0xFFFAFAFA)) {
        ContainerFocus(context, navController, auth)
    }
}

@Composable
fun ContainerFocus(context: ComponentActivity, navController: NavController, auth: FirebaseAuth) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .pointerInput(UInt) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val emailValue = remember {
            mutableStateOf(TextFieldValue())
        }
        TextInput(label = "Correo", mutableText = emailValue)
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            modifier = Modifier.height((screenHeight*0.06).dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFFFAFAFA),
                containerColor = Color(0xFF000000)
            ),
            onClick = {
                forgotPassword(emailValue.value.text, auth, context, navController)
            }
        ) {
            Text(text = "Enviar correo")
        }
    }
}

fun forgotPassword(text: String, auth: FirebaseAuth, context: ComponentActivity, navController: NavController){
    if (text.isEmpty()){
        Toast.makeText(context, "Campo vacío", Toast.LENGTH_SHORT).show()
        return
    }
    auth.sendPasswordResetEmail(text)
        .addOnCompleteListener(context){task ->
            if (task.isSuccessful){
                navController.navigate("login") {
                    popUpTo("forgotpassword"){
                        inclusive = true
                    }
                }
            } else {
                Toast.makeText(context, "Correo no válido", Toast.LENGTH_SHORT).show()
            }
        }
}
