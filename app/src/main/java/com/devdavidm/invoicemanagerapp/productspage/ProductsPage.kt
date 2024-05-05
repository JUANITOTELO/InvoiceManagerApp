package com.devdavidm.invoicemanagerapp.productspage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProductPage(db: FirebaseFirestore){
    val totalProducts = remember { mutableIntStateOf(0) }
    val productsList = remember { mutableStateOf(listOf<String>()) }
    val isRefreshing = remember { mutableStateOf(false) }
    val colRef = db.collection("Products")

    colRef.addSnapshotListener() { value, error ->
        if (error != null) {
            Log.w("TAG", "Listen failed.", error)
            return@addSnapshotListener
        }
        val tmpCountQuery = colRef.count()
        tmpCountQuery.get(AggregateSource.SERVER).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                totalProducts.intValue = task.result.count.toInt()
                Log.w("COUNTPRODUCTSQUERY", "Total products: ${totalProducts.intValue}")
            } else {
                Log.w("COUNTPRODUCTSQUERYERROR", "Error getting documents.", task.exception)
            }
        }
        val tempList = mutableListOf<String>()
        for (document in value!!) {
            tempList.add("Producto #${document.data["num"].toString()}: ${document.data["name"].toString()}")
        }
        productsList.value = tempList
    }

    LaunchedEffect(key1 = Unit) {
        isRefreshing.value = true
        db.collection("Products").get()
            .addOnSuccessListener { result ->
                val tempList = mutableListOf<String>()
                for (document in result) {
                    tempList.add("Producto #${document.data["num"].toString()}: ${document.data["name"].toString()}")
                }
                productsList.value = tempList
                isRefreshing.value = false
            }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
        onRefresh = {
            isRefreshing.value = true
            db.collection("Products").get()
                .addOnSuccessListener { result ->
                    val tempList = mutableListOf<String>()
                    for (document in result) {
                        tempList.add("Producto #${document.data["num"].toString()}: ${document.data["name"].toString()}")
                    }
                    productsList.value = tempList
                    isRefreshing.value = false
                }
        },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Text(
                    text = "Total de productos: ${totalProducts.intValue}",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color.Black)
                )
            }
            items(productsList.value.size) { index ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().selectable(selected = true, onClick = {
                        // Handle click on item
                        Log.d("PRODUCTCLICK", "Product ${productsList.value[index]} clicked")
                    })
                ) {
                    Text(
                        text = productsList.value[index],
                        fontSize = 18.sp,
                        modifier = Modifier.padding(18.dp)
                    )
                    val expanded = remember { mutableStateOf(false) }
                    Box{
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More options",
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable { expanded.value = true }
                        )
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            properties = PopupProperties(focusable = true)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Editar") },
                                onClick = {
                                // Handle click on Option 1
                                expanded.value = false
                            })
                            DropdownMenuItem(
                                text = { Text("Eliminar") },
                                onClick = {
                                // Handle click on Option 2
                                expanded.value = false
                            })
                            // Add more DropdownMenuItems for more options
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Black)
                )
            }
        }
    }
}

@Composable
fun NewProductFloatingButton(navController: NavController) {
    // Add a floating button to add a new product

        ExtendedFloatingActionButton(
            containerColor = Color(0xFF000000),
            contentColor = Color(0xFFFAFAFA),
            modifier = Modifier
                .width(65.dp)
                .height(65.dp),
            shape = RoundedCornerShape(30),
            onClick = {
                navController.navigate("new_product")

            }
        ){
            Icon(Icons.Rounded.Add, contentDescription = "Nuevo producto")
        }

}
