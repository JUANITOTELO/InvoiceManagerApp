package com.devdavidm.invoicemanagerapp.homepage

import android.widget.Button
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devdavidm.invoicemanagerapp.button.Button
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun HomePage(navController: NavController, auth: FirebaseAuth){
    Surface(color = Color(0xFFFAFAFA)) {
        Row {
            OptionsMenuDrawer()
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Pagina de inicio")
            Button(text = "Salir"){
                auth.signOut()
                navController.navigate("login"){
                    popUpTo("home"){
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMenu() {
    TODO("Not yet implemented")
}

@Composable
fun OptionsMenuDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf("Inicio", "Perfil", "Cerrar sesión")
    val selectedItem = remember { mutableStateOf(items[0])}

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column {
                Spacer(Modifier.height(12.dp))
                items.forEach {
                    item -> NavigationDrawerItem(
                        label = { Text(item) },
                        selected = selectedItem.value == item,
                        onClick = {
                            selectedItem.value = item
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp,0.dp),
                horizontalAlignment = Alignment.End
            ){
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier
                        .padding(8.dp)
                        .height(48.dp)
                        .clickable { scope.launch { drawerState.open() } }
                )
            }
        }
    )
}