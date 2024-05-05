package com.devdavidm.invoicemanagerapp.productspage

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
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NewProductPage(navController: NavHostController, db: FirebaseFirestore) {
    BackHandler {
        navController.navigate("home/Productos"){
            popUpTo("new_product"){
                inclusive = true
            }
        }
    }
    Surface(
        color = Color(0xFFFAFAFA),
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // Title
            Text(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, bottom = 16.dp),
                fontSize = 30.sp,
                text = "Nuevo Producto",
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Color.Black)
            )
            NewProductForm(navController, db)
        }
    }
}

@Composable
fun NewProductForm(navController: NavHostController, db: FirebaseFirestore) {
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp
    // Form
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        // brand
        val brandValue = remember { mutableStateOf(TextFieldValue()) }
        TextInput(label = "Marca", mutableText = brandValue)

        // description
        val descriptionValue = remember { mutableStateOf(TextFieldValue()) }
        TextInput(label = "Descripción", mutableText = descriptionValue)

        // name
        val nameValue = remember { mutableStateOf(TextFieldValue()) }
        TextInput(label = "Nombre", mutableText = nameValue)

        // price
        val priceValue = remember { mutableStateOf(TextFieldValue()) }
        TextInput(label = "Precio", mutableText = priceValue, number = true)

        // units
        val unitsValue = remember { mutableStateOf(TextFieldValue()) }
        TextInput(label = "Unidades", mutableText = unitsValue, number = true)

        // type
        val typeValue = remember { mutableStateOf(TextFieldValue()) }
        TextInput(label = "Tipo", mutableText = typeValue)

        // submit button
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            modifier = Modifier.height((screenHeight*0.06).dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFFFAFAFA),
                containerColor = Color(0xFF000000)
            ),
            onClick = {
                val valid = validateInfo(
                    brandValue.value.text,
                    descriptionValue.value.text,
                    nameValue.value.text,
                    priceValue.value.text,
                    unitsValue.value.text,
                    typeValue.value.text
                )
                if (valid) {
                    // Count the number of products
                    val tmpCountQuery = db.collection("Products").count()
                    tmpCountQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            db.collection("Products").add(
                                hashMapOf(
                                    "brand" to brandValue.value.text,
                                    "description" to descriptionValue.value.text,
                                    "name" to nameValue.value.text,
                                    "price" to priceValue.value.text.toInt(),
                                    "units" to unitsValue.value.text.toInt(),
                                    "type" to typeValue.value.text,
                                    "num" to task.result.count.toInt() + 1
                                )
                            ).addOnSuccessListener {
                                Toast.makeText(context, "Producto guardado", Toast.LENGTH_SHORT).show()
                                navController.navigate("home/Productos"){
                                    popUpTo("new_product"){
                                        inclusive = true
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(context, "Error al guardar el producto", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Guardar")
        }
    }
}

fun validateInfo(
    brand: String,
    description: String,
    name: String,
    price: String,
    units: String,
    type: String
): Boolean {
    return !(brand.isEmpty() || description.isEmpty() || name.isEmpty() || price.isEmpty() || units.isEmpty() || type.isEmpty())
}
