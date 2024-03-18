package com.devdavidm.invoicemanagerapp.registerpage

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.devdavidm.invoicemanagerapp.loginpage.login
import com.google.firebase.auth.FirebaseAuth

fun register(email: String, password: String, auth: FirebaseAuth, context: ComponentActivity, navController: NavController){
    auth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener(context){task ->
            if (task.isSuccessful){
                navController.navigate("home") {
                    popUpTo("register"){
                        inclusive = true
                    }
                }
            }
        }
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
        val confirmPasswordValue = remember {
            mutableStateOf(TextFieldValue())
        }
        Image(painter = logo, contentDescription = "Logo")
        TextInput(label = "Correo", mutableText = emailValue)
        Spacer(modifier = Modifier.height(7.dp))
        TextInput(label = "Contraseña", password = true, mutableText = passwordValue)
        Spacer(modifier = Modifier.height(7.dp))
        TextInput(label = "Confirmar Contraseña", password = true, mutableText = confirmPasswordValue)
        Spacer(modifier = Modifier.height(20.dp))
        Button(text = "Registrarse") {
            if (passwordValue.value.text == confirmPasswordValue.value.text) {
                register(
                    emailValue.value.text,
                    passwordValue.value.text,
                    auth,
                    context,
                    navController
                )
            }
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

@Composable
fun RegisterPage(context: ComponentActivity, navController: NavController, auth: FirebaseAuth){
    Surface(color = Color(0xFFFAFAFA)) {
        ContainerFocus(context, navController, auth)
    }
}
