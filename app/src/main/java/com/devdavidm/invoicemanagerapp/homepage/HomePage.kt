package com.devdavidm.invoicemanagerapp.homepage

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.devdavidm.invoicemanagerapp.button.Button
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun HomePage(navController: NavController, auth: FirebaseAuth){
    OptionsMenuDrawer(navController, auth)
}

@Composable
fun getScreenWidth(): Int{
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}

@Composable
fun OptionsMenuDrawer(navController: NavController, auth: FirebaseAuth){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf("Inicio", "Perfil", "Cerrar sesión")
    val selectedItem = remember { mutableStateOf(items[0])}
    val screenWidth = getScreenWidth()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Surface(
                color = Color(0xFFFAFAFA),
                modifier = Modifier
                    .width((screenWidth * 0.8).dp)
            ) {
            Column(
                modifier = Modifier.padding(20.dp, 0.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                    Spacer(modifier = Modifier.height(35.dp))
                    items.forEach {
                            item ->
                        NavigationDrawerItem(
                            shape = RoundedCornerShape(10.dp),
                            label = { Text(item, textAlign = TextAlign.Center) },
                            selected = selectedItem.value == item,
                            onClick = {
                                if(item == "Cerrar sesión"){
                                    auth.signOut()
                                    navController.navigate("login")
                                }
                                selectedItem.value = item
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                }
            }
        },
        content = {
            Surface(color = Color(0xFFFAFAFA)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.End
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .padding(20.dp, 20.dp)
                            .height(40.dp)
                            .width(40.dp)
                            .clickable { scope.launch { drawerState.open() } }
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(Color.Black)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PagesRender(selectedItem.value, navController, auth)
                }
            }
        }
    )
}

@Composable
fun PagesRender(value: String, navController: NavController, auth: FirebaseAuth) {
    when(value){
        "Inicio" -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Inicio")
            }
        }
        "Perfil" -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Perfil")
            }
        }
        "Cerrar sesión" -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Cerrar sesión")
            }
        }
    }
}
