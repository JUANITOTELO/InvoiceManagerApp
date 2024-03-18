package com.devdavidm.invoicemanagerapp.loginpage

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devdavidm.invoicemanagerapp.R
import com.devdavidm.invoicemanagerapp.button.Button
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun LoginPage(context: ComponentActivity, navController: NavController, auth: FirebaseAuth){
    Surface(color = Color(0xFFFAFAFA)) {
        ContainerFocus(context, navController, auth)
    }
}
fun login(email: String, password: String, auth: FirebaseAuth, context: ComponentActivity, navController: NavController){
    auth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener(context){task ->
            if (task.isSuccessful){
                Log.d("AUTH", "Success!")
                navController.navigate("home") {
                    popUpTo("login"){
                        inclusive = true
                    }
                }
            } else {
                Log.d("NOAUTH", "Failed: ${task.exception}")
            }
        }
    Log.d("LoginFunction","User: $email Password: $password")
}
@Composable
fun ContainerFocus(context: ComponentActivity, navController: NavController, auth: FirebaseAuth) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val logo: Painter = painterResource(id = R.drawable.logo)
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
        val passwordValue = remember {
            mutableStateOf(TextFieldValue())
        }
        Image(painter = logo, contentDescription = "logo")
        TextInput(label = "Correo", mutableText = emailValue)
        Spacer(modifier = Modifier.height(7.dp))
        TextInput(label = "Contraseña", password = true, mutableText = passwordValue)
        Button(text="Ingresar") {
            login(emailValue.value.text, passwordValue.value.text, auth, context, navController)
        }
    }
}


@Composable
fun TextInput(label: String, password: Boolean =false, mutableText: MutableState<TextFieldValue>){
    OutlinedTextField(
        value = mutableText.value,
        onValueChange = {mutableText.value=it},
        label = { Text(label) },
        visualTransformation = if (password) PasswordVisualTransformation()
        else VisualTransformation {
            TransformedText(
                text = AnnotatedString(mutableText.value.text),
                offsetMapping = OffsetMapping.Identity
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (password) KeyboardType.Password else KeyboardType.Email
        ),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFF000000),
            focusedBorderColor = Color(0xFF000000),
            cursorColor = Color(0xFF000000),
            focusedLabelColor = Color(0xFF000000),
            focusedTextColor = Color(0xFF282828),
            unfocusedTextColor = Color(0xFF000000)
        ),
        modifier = Modifier.focusable()
    )
}