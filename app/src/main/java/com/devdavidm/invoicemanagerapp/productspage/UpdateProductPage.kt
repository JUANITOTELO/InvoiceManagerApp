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
import androidx.compose.runtime.mutableIntStateOf
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
fun UpdateProductPage(navController: NavHostController, db: FirebaseFirestore, id: String) {
    BackHandler {
        navController.navigate("home/Productos"){
            popUpTo("update_product/$id"){
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
                text = "Actualizar Producto",
                color = Color(0xFF000000),
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(Color.Black)
            )
            UpdateProductForm(navController, db, id)
        }
    }
}

@Composable
fun UpdateProductForm(navController: NavHostController, db: FirebaseFirestore, id: String) {
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp
    // Fields
    val brandValue = remember { mutableStateOf(TextFieldValue()) }
    val descriptionValue = remember { mutableStateOf(TextFieldValue()) }
    val nameValue = remember { mutableStateOf(TextFieldValue()) }
    val priceValue = remember { mutableStateOf(TextFieldValue()) }
    val unitsValue = remember { mutableStateOf(TextFieldValue()) }
    val typeValue = remember { mutableStateOf(TextFieldValue()) }
    val numValue = remember { mutableStateOf("0") }

    val product = db.collection("Products").document(id)
    product.get().addOnSuccessListener {
            result ->
        if(result.data?.isEmpty() == false){
            brandValue.value = TextFieldValue(result.data?.get("brand").toString())
            descriptionValue.value = TextFieldValue(result.data?.get("description").toString())
            nameValue.value = TextFieldValue(result.data?.get("name").toString())
            priceValue.value = TextFieldValue(result.data?.get("price").toString())
            unitsValue.value = TextFieldValue(result.data?.get("units").toString())
            typeValue.value = TextFieldValue(result.data?.get("type").toString())
            numValue.value = result.data?.get("num").toString()
        }

    }
    // Form
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()
    ) {
        // brand
        TextInput(label = "Marca", mutableText = brandValue)

        // description
        TextInput(label = "Descripción", mutableText = descriptionValue)

        // name
        TextInput(label = "Nombre", mutableText = nameValue)

        // price
        TextInput(label = "Precio", mutableText = priceValue, number = true)

        // units
        TextInput(label = "Unidades", mutableText = unitsValue, number = true)

        // type
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
                    product.set(
                        hashMapOf(
                            "brand" to brandValue.value.text,
                            "description" to descriptionValue.value.text,
                            "name" to nameValue.value.text,
                            "price" to priceValue.value.text.toInt(),
                            "units" to unitsValue.value.text.toInt(),
                            "type" to typeValue.value.text,
                            "num" to numValue.value.toInt()
                        )
                    ).addOnSuccessListener {
                        Toast.makeText(context, "Producto guardado", Toast.LENGTH_SHORT).show()
                        navController.navigate("home/Productos"){
                            popUpTo("update_product/$id"){
                                inclusive = true
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Error al guardar el producto", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Guardar")
        }
    }
}
