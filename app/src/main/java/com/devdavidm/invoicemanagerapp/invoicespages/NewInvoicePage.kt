package com.devdavidm.invoicemanagerapp.invoicespages

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devdavidm.invoicemanagerapp.registerpage.TextInput
import com.google.firebase.firestore.FirebaseFirestore
import io.github.joelkanyi.sain.Sain
import io.github.joelkanyi.sain.SignatureAction
import io.github.joelkanyi.sain.SignatureState

@Composable
fun NewInvoicePage(navController: NavHostController, db: FirebaseFirestore) {
    BackHandler {
        navController.navigate("home/Facturas"){
            popUpTo("new_invoice") {
                inclusive = true
            }
        }
    }
    Surface(color = Color(0xFFFAFAFA)) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp),
                fontSize = 30.sp,
                text = "Nueva Factura",
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
            InvoiceForm(navController, db)
        }
    }
}

@Composable
fun InvoiceForm(navController: NavHostController, db: FirebaseFirestore) {
    val companyName = remember { mutableStateOf(TextFieldValue("Regala")) }
    val address = remember { mutableStateOf(TextFieldValue("Bogotá, Colombia")) }
    val phone = remember { mutableStateOf(TextFieldValue("3173213885")) }
    var selectedSeller by remember { mutableStateOf("") }
    var selectedClient by remember { mutableStateOf("") }
    var selectedProducts by remember { mutableStateOf(listOf(("" to "1"))) }
    var paymentMethod by remember { mutableStateOf("Tarjeta") }
    val clientSignature = remember { mutableStateOf(TextFieldValue("")) }
    val amountToBePaid = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 10.dp, 16.dp, 5.dp)
            .verticalScroll(rememberScrollState()),

        ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Datos de la Empresa", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF000000))
        Spacer(modifier = Modifier.height(8.dp))
        TextInput(
            label = "Empresa",
            mutableText = companyName,
            enabled = false
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextInput(
            label = "Dirección",
            mutableText = address,
            enabled = false
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextInput(
            label = "Teléfono",
            mutableText = phone,
            enabled = false,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text("Proveedor", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF000000))
        Spacer(modifier = Modifier.height(2.dp))
        DropdownMenu(
            label = "Proveedor",
            options = listOf("P 1", "P 2", "P 3"),
            selectedOption = selectedSeller,
            onOptionSelected = { selectedSeller = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Cliente", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF000000))
        Spacer(modifier = Modifier.height(2.dp))
        DropdownMenu(
            label = "Cliente",
            options = listOf("C 1", "C 2", "C 3"),
            selectedOption = selectedClient,
            onOptionSelected = { selectedClient = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Productos", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF000000))
        Spacer(modifier = Modifier.height(8.dp))
        selectedProducts.forEachIndexed { index, product ->
            Column(
                modifier = Modifier
                    .padding(3.dp)
                    .border(BorderStroke(1.dp, SolidColor(Color.Black)), RoundedCornerShape(10.dp))
                ,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                DropdownMenu(
                    label = "Producto",
                    options = listOf("Producto 1", "Producto 2", "Producto 3"),
                    selectedOption = product.first,
                    onOptionSelected = {
                        selectedProducts = selectedProducts.toMutableList().apply {
                            this[index] = it to product.second
                        }
                        Log.w("ListOfProducts", "List: $selectedProducts")
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ){
                    OutlinedTextField(
                        value = product.second,
                        onValueChange = { newAmount ->
                            selectedProducts = selectedProducts.toMutableList().apply {
                                this[index] = product.first to newAmount
                            }
                            Log.w("ListOfProducts", "List: $selectedProducts")
                        },
                        label = { Text("Cantidad") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFF000000),
                            focusedBorderColor = Color(0xFF000000),
                            cursorColor = Color(0xFF000000),
                            focusedLabelColor = Color(0xFF000000),
                            focusedTextColor = Color(0xFF282828),
                            unfocusedTextColor = Color(0xFF000000)
                        ),
                        modifier = Modifier
                            .focusable()
                    )
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color(0xFF000000),
                            contentColor = Color(0xFFFAFAFA)
                        ),
                        onClick = {
                            selectedProducts = selectedProducts.toMutableList().apply {
                                removeAt(index)
                            }
                            Log.w("ListOfProducts", "List: $selectedProducts")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Remove,
                            contentDescription = "Eliminar producto"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFFFAFAFA),
                containerColor = Color(0xFF000000)
            ),
            onClick = {
            selectedProducts = selectedProducts + ("" to "1")
        }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Producto")
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Agregar producto")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Forma de pago", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF000000))
        Spacer(modifier = Modifier.height(2.dp))

        DropdownMenu(
            label = "Método de pago",
            options = listOf("Tarjeta", "Efectivo"),
            selectedOption = paymentMethod,
            onOptionSelected = { paymentMethod = it }
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text("Fírma del cliente", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF000000))
        Spacer(modifier = Modifier.height(8.dp))
        SignatureComponent(
            onSigned = {
                /* TODO */
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextInput(
            label = "Total a pagar",
            mutableText = amountToBePaid,
            enabled = false
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.size(100.dp, 50.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFAFAFA),
                    containerColor = Color(0xFF000000)
                ),
                onClick = { /* Handle form submission */ }) {
                Text("Crear")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var mySelectedOption by remember { mutableStateOf(selectedOption) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .padding(10.dp),
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = !expanded},
            modifier = Modifier
            .fillMaxWidth()
            .focusable()
        ) {
            TextField(
                value = mySelectedOption,
                onValueChange = {},
                readOnly = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF000000),
                    focusedBorderColor = Color(0xFF000000),
                    cursorColor = Color(0xFF000000),
                    focusedLabelColor = Color(0xFF000000),
                    focusedTextColor = Color(0xFF282828),
                    unfocusedTextColor = Color(0xFF000000)
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFFAFAFA)
                    )
            ) {
                options.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = { 
                            mySelectedOption = item
                            expanded = false
                            onOptionSelected(item)
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = Color(0xFF000000)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

}

@Composable
fun SignatureComponent(onSigned:(ImageBitmap)->Unit){
    val state = remember {
        SignatureState()
    }
    val context = LocalContext.current
    val stroke = Stroke(3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f,10f,10f)))
    Sain(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(250.dp)
            .drawBehind {
                drawRoundRect(
                    color = Color.Black,
                    style = stroke,
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            },
        onComplete = { signatureBitmap ->
            if(signatureBitmap != null){
                onSigned(signatureBitmap)
            } else {
                Toast.makeText(context, "No se agrego una firma", Toast.LENGTH_SHORT).show()
            }
        }
    ) { action ->
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceAround
        ) {
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFAFAFA),
                    containerColor = Color(0xFF737373)
                ),
                onClick = {
                    action(SignatureAction.CLEAR)
                }) {
                Text("Borrar")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFAFAFA),
                    containerColor = Color(0xFF000000)
                ),
                onClick = {
                    action(SignatureAction.COMPLETE)
                }) {
                Text("Guardar")
            }
        }
    }
}