package com.devdavidm.invoicemanagerapp.loginpage

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devdavidm.invoicemanagerapp.R
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
    if(email.isEmpty() || password.isEmpty()){
        Toast.makeText(context, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
        return
    }
    if (!email.contains("@") && !email.contains(".")){
        Toast.makeText(context, "Correo inválido", Toast.LENGTH_LONG).show()
        return
    }
    auth.signInWithEmailAndPassword(email,password)
        .addOnCompleteListener(context){task ->
            if (task.isSuccessful){
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                navController.navigate("home") {
                    popUpTo("login"){
                        inclusive = true
                    }
                }
            } else {
                Toast.makeText(context, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        }
}
@Composable
fun ContainerFocus(context: ComponentActivity, navController: NavController, auth: FirebaseAuth) {
    val focusManager = LocalFocusManager.current
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val scrollState = rememberScrollState()
    val logo: Painter = painterResource(id = R.drawable.logo)
    Surface(color = Color(0xFFFAFAFA)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFF000000),
                radius = size.width+30,
                center = Offset(0f, 10f)
            )
            drawLine(
                color = Color(0xFFFAFAFA),
                start = Offset(0f, size.height*0.05f),
                end = Offset(size.width, size.height*0.05f),
                strokeWidth = 20f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(size.width*0.1f, size.width*0.05f), 0f)
            )
            drawCircle(
                color = Color(0xFF000000),
                radius = size.width+100,
                center = Offset(screenWidth.dp.toPx()/2, screenHeight.dp.toPx()+size.width),
                style = Stroke(
                    width = 10.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(size.width*0.1f, size.width*0.05f), 0f)
                )
            )
            drawCircle(
                color = Color(0xFFFAFAFA),
                radius = size.width+100,
                center = Offset(screenWidth.dp.toPx()/2, screenHeight.dp.toPx()+size.width)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
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
            Text(
                text = "Invoice Manager",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF000000)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextInput(label = "Correo", mutableText = emailValue)
            Spacer(modifier = Modifier.height(7.dp))
            TextInput(label = "Contraseña", password = true, mutableText = passwordValue)
            Spacer(modifier = Modifier.height(15.dp))
            Row{
                // Forgot password
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = Color(0xFF000000),
                    modifier = Modifier.clickable {
                        // Handle click event here
                        // For example, navigate to the forgot password screen
                        navController.navigate("forgot_password")
                    }
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                modifier = Modifier.height((screenHeight*0.06).dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFAFAFA),
                    containerColor = Color(0xFF000000)
                ),
                onClick = {
                    login(emailValue.value.text, passwordValue.value.text, auth, context, navController)
                }
            ) {
                Text(text = "Iniciar sesión")
            }
        }
    }
}


@Composable
fun TextInput(label: String, password: Boolean = false, number: Boolean = false, mutableText: MutableState<TextFieldValue>){
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
            keyboardType = if (password) KeyboardType.Password else if (number) KeyboardType.Number else KeyboardType.Email
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