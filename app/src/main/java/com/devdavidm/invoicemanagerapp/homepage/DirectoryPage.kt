package com.devdavidm.invoicemanagerapp.homepage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DirectoryPage(){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(10.dp, 20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        var startyear = 2013
        for (i in 1..8){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MyDirectory(text = startyear.toString())
                MyDirectory(text = (startyear+1).toString())
                startyear+=1
            }
        }
    }
}

@Composable
fun MyFloatingActionButton() {
    val context = LocalContext.current
    ExtendedFloatingActionButton(
        containerColor = Color(0xFF000000),
        contentColor = Color(0xFFFAFAFA),
        modifier = Modifier.width(65.dp).height(65.dp),
        shape = RoundedCornerShape(30),
        onClick = {
            Toast.makeText(context, "Nuevo directorio", Toast.LENGTH_SHORT).show()
        }
    ){
        Icon(Icons.Rounded.CreateNewFolder, contentDescription = "Nuevo directorio")
    }
}

@Composable
fun MyDirectory(text: String){
    Box(modifier = Modifier
        .width(150.dp)
        .height(150.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(color = Color(0xFFE0E0E0))
        .padding(10.dp),
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