package com.devdavidm.invoicemanagerapp.homepage

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devdavidm.invoicemanagerapp.loginpage.TextInput

@Composable
fun NewClientPage(navController: NavHostController, db: FirebaseFirestore){
    BackHandler {
        navController.navigate("home/Clientes"){
            popUpTo("new_customer"){
                inclusive = true
            }
        }
    }
    Surface(
        color = Color(0xFFFAFAFA),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // Title
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, bottom = 16.dp),
                fontSize = 30.sp,
                text = "Nuevo Cliente",
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Color.Black)
            )
            // Form
            NewClientForm(navController, db)
        }
    }
}

@Composable
fun NewClientForm(navController: NavHostController, db: FirebaseFirestore) {
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp

    // Form fields
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        // Firstname
        val firstnameValue = remember {
            mutableStateOf(TextFieldValue())
        }
        TextInput(label = "Nombre", mutableText = firstnameValue)
        // Lastname
        val lastnameValue = remember {
            mutableStateOf(TextFieldValue())
        }
        TextInput(label = "Apellido", mutableText = lastnameValue)
        // Email
        val emailValue = remember {
            mutableStateOf(TextFieldValue())
        }
        TextInput(label = "Correo", mutableText = emailValue)
        // Phone
        val phoneValue = remember {
            mutableStateOf(TextFieldValue())
        }
        TextInput(label = "Teléfono", mutableText = phoneValue, number = true)
        // Address
        val addressValue = remember {
            mutableStateOf(TextFieldValue())
        }
        TextInput(label = "Dirección", mutableText = addressValue)
        // ID
        val idValue = remember {
            mutableStateOf(TextFieldValue())
        }
        TextInput(label = "Documento", mutableText = idValue, number = true)
        // Save button
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            modifier = Modifier.height((screenHeight*0.06).dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFFFAFAFA),
                containerColor = Color(0xFF000000)
            ),
            onClick = {
                val valid = validateInfo(
                    firstnameValue.value.text,
                    lastnameValue.value.text,
                    emailValue.value.text,
                    phoneValue.value.text,
                    addressValue.value.text,
                    idValue.value.text,
                )
                if (valid){
                    db.collection("Customers").add(
                        hashMapOf(
                            "firstname" to firstnameValue.value.text,
                            "lastname" to lastnameValue.value.text,
                            "email" to emailValue.value.text,
                            "phone" to phoneValue.value.text.toInt(),
                            "address" to addressValue.value.text,
                            "id" to idValue.value.text.toInt()
                        )
                    ).addOnSuccessListener {
                        Toast.makeText(context, "Cliente guardado", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }.addOnFailureListener {
                        Toast.makeText(context, "Error al guardar el cliente", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Guardar")
        }
    }

}

fun validateInfo(
    firstname: String,
    lastname: String,
    email: String,
    phone: String,
    address: String,
    id: String
): Boolean {
    return (email.contains("@") &&
            email.contains(".")) &&
            !(firstname.isEmpty() ||
                    lastname.isEmpty() ||
                    email.isEmpty() ||
                    address.isEmpty() ||
                    phone.isEmpty() ||
                    id.isEmpty())
}


