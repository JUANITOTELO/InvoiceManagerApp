package com.devdavidm.invoicemanagerapp.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContactPage
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.TagFaces
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
    val items = listOf("Carpetas", "Facturas", "Perfil")
    val selectedItem = remember { mutableStateOf(items[0])}
    val screenWidth = getScreenWidth()
    val focusManager = LocalFocusManager.current

    ModalNavigationDrawer(
        modifier = Modifier.pointerInput(UInt) {
            detectTapGestures(
                onTap = {
                    focusManager.clearFocus()
                }
            )
        },
        drawerState = drawerState,
        drawerContent = {
            Surface(
                color = Color(0xFFFAFAFA),
                modifier = Modifier
                    .width((screenWidth * 0.8).dp)
            ) {
            Column(
                modifier = Modifier
                    .padding(20.dp, 0.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    items.forEach {
                            item ->
                        NavigationDrawerItem(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
                            shape = RoundedCornerShape(10.dp),
                            label = { Text(item, textAlign = TextAlign.Center) },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color(0xFFFAFAFA),
                                unselectedTextColor = Color(0xFF000000),
                                unselectedIconColor = Color(0xFF000000),
                                selectedContainerColor = Color(0xFFD3D3D3),
                                selectedTextColor = Color(0xFF000000),
                                selectedIconColor = Color(0xFF000000),
                            ),
                            icon = {
                                when(item){
                                    "Carpetas" -> {
                                        Icon(
                                            imageVector = Icons.Outlined.Folder,
                                            contentDescription = "Carpeta"
                                        )
                                    }
                                    "Facturas" -> {
                                        Icon(
                                            imageVector = Icons.Outlined.ContactPage,
                                            contentDescription = "Facturas"
                                        )
                                    }
                                    "Perfil" -> {
                                        Icon(
                                            imageVector = Icons.Outlined.TagFaces,
                                            contentDescription = "Perfil"
                                        )
                                    }
                                }
                            },
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp, 20.dp),
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    NavigationDrawerItem(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.width(screenWidth.dp * 0.5f),
                        label = { Text("Cerrar sesión", textAlign = TextAlign.Center) },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Cerrar sesión",
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        },
                        selected = selectedItem.value == "Cerrar sesión",
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color(0xFFF44336),
                            unselectedTextColor = Color(0xFFFAFAFA),
                            unselectedIconColor = Color(0xFFFAFAFA),
                            selectedContainerColor = Color(0xFFFF0000),
                            selectedTextColor = Color(0xFFFAFAFA),
                            selectedIconColor = Color(0xFFFAFAFA),
                        ),
                        onClick = {
                            auth.signOut()
                            navController.navigate("onboarding"){
                                popUpTo("home"){
                                    inclusive = true
                                }
                            }
                            selectedItem.value = "Cerrar sesión"
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )

                }
            }
        },
        content = {
            Surface(
                color = Color(0xFFFAFAFA)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.End
                ) {
                    Row {
                        if(selectedItem.value == "Carpetas" || selectedItem.value == "Facturas") {
                            SearchBar()
                        }
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Menu",
                            modifier = Modifier
                                .padding(20.dp, 20.dp)
                                .height(40.dp)
                                .width(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { scope.launch { drawerState.open() } }
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .background(Color.Black)
                    )
                    PagesRender(selectedItem.value, navController, auth)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                if (selectedItem.value == "Carpetas"){
                    NewFolderFloatingButton()
                } else if (selectedItem.value == "Facturas"){
                    NewInvoiceFloatingButton()
                }
            }
        }
    )
}

@Composable
fun PagesRender(value: String, navController: NavController, auth: FirebaseAuth) {
    when(value){
        "Carpetas" -> {
            DirectoryPage()
        }
        "Facturas" -> {
            InvoicesPage()
        }
        "Perfil" -> {
            val user = auth.currentUser
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Perfil")
                Text(text = "Nombre: ${user?.displayName}")
                Text(text = "Correo: ${user?.email}")
            }
        }
    }
}