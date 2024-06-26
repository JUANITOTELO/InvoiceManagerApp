package com.devdavidm.invoicemanagerapp.homepage

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CreateNewFolder
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DirectoryPage(db: FirebaseFirestore) {
    val customersList = remember { mutableStateOf(listOf<String>()) }
    val isRefreshing = remember { mutableStateOf(false) }
    val colRef = db.collection("Customers")
    colRef.addSnapshotListener() { value, error ->
        if (error != null) {
            Log.w("TAG", "Listen failed.", error)
            return@addSnapshotListener
        }
        val tempList = mutableListOf<String>()
        for (document in value!!) {
            tempList.add(document.data["firstname"].toString())
        }
        customersList.value = tempList
    }

    LaunchedEffect(key1 = Unit) {
        isRefreshing.value = true
        db.collection("Customers").get()
            .addOnSuccessListener { result ->
                val tempList = mutableListOf<String>()
                for (document in result) {
                    tempList.add(document.data["firstname"].toString())
                }
                customersList.value = tempList
                isRefreshing.value = false
            }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
        onRefresh = {
            isRefreshing.value = true
            db.collection("Customers").get()
                .addOnSuccessListener { result ->
                    val tempList = mutableListOf<String>()
                    for (document in result) {
                        tempList.add(document.data["firstname"].toString())
                    }
                    customersList.value = tempList
                    isRefreshing.value = false
                }
        },
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            items(customersList.value.chunked(2)) { customer ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceAround
                ) {
                    customer.forEach { name ->
                        MyDirectory(text = name, context = LocalContext.current)
                    }
                }
            }
        }
    }
}

@Composable
fun NewFolderFloatingButton(navController: NavController) {
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        containerColor = Color(0xFF000000),
        contentColor = Color(0xFFFAFAFA),
        modifier = Modifier
            .width(65.dp)
            .height(65.dp),
        shape = RoundedCornerShape(30),
        onClick = {
            navController.navigate("new_customer")
        }
    ){
        Icon(Icons.Rounded.CreateNewFolder, contentDescription = "Nuevo cliente")
    }
}
@Composable
fun MyDirectory(text: String, context: Context){
    val isClicked = remember { mutableStateOf(false) }
    val size = animateDpAsState(if (isClicked.value) 140.dp else 150.dp, label = "")
    val elevation = animateDpAsState(if (isClicked.value) 0.dp else 7.dp, label = "")
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier
        .width(size.value)
        .height(size.value)
        .padding(10.dp)
        .shadow(
            elevation = elevation.value,
            RoundedCornerShape(20.dp),
            spotColor = Color(0xFF000000)
        )
        .clip(RoundedCornerShape(20.dp))
        .clickable {
            isClicked.value = !isClicked.value
            scope.launch {
                delay(100)
                isClicked.value = !isClicked.value
            }

            Toast
                .makeText(context, "Carpeta $text", Toast.LENGTH_SHORT)
                .show()
        }
        .background(color = Color(0xFFE0E0E0)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Folder,
            contentDescription = "Folder",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color(0xFFE0E0E0)
        )
    }
}