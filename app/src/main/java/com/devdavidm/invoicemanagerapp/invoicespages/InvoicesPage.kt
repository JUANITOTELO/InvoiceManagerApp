package com.devdavidm.invoicemanagerapp.invoicespages

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InvoicesPage(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Facturas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NewInvoiceFloatingButton(navController: NavController){
    val context = LocalContext.current
    val isOpen = remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(if (isOpen.value) 45f else 0f, label = "")
    val contentPadding: Float by animateFloatAsState(if (isOpen.value) 0f else 8f, label = "")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(
            visible = isOpen.value,
        ) {
            Column {
                FloatingActionButton(
                    containerColor = Color(0xFF000000),
                    contentColor = Color(0xFFFAFAFA),
                    onClick = {
                        navController.navigate("new_invoice")
                        Toast.makeText(context, "Nueva Factura", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        Icons.Rounded.Create,
                        contentDescription = "Nueva factura"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(
                    containerColor = Color(0xFF000000),
                    contentColor = Color(0xFFFAFAFA),
                    onClick = {
                        navController.navigate("cameraPreview")
                        Toast.makeText(context, "Nueva Factura (OCR)", Toast.LENGTH_SHORT).show()
                    }
                ){
                    Icon(
                        Icons.Rounded.Camera,
                        contentDescription = "Nueva factura (OCR)"
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        FloatingActionButton(
            containerColor = Color(0xFF000000),
            contentColor = Color(0xFFFAFAFA),
            modifier = Modifier.padding(bottom = contentPadding.dp).height(65.dp).width(65.dp),
            onClick = { isOpen.value = !isOpen.value },
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Expand", modifier = Modifier.rotate(angle))
        }
    }
}