package com.devdavidm.invoicemanagerapp.onboardingpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devdavidm.invoicemanagerapp.R
import com.devdavidm.invoicemanagerapp.button.Button

@Composable
fun OnboardingPage(navController: NavController){
    val topImagePainter: Painter = painterResource(id = R.drawable.intropage)
    Surface(color = Color(0xFFFAFAFA)) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = topImagePainter,
                contentDescription = "Onboarding Top Image",
                modifier = Modifier
                    .fillMaxSize(),
                alignment = androidx.compose.ui.Alignment.TopCenter
            )
        }
        Column(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 100.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ){
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                text = "Ingresar",
                onClick = {
                    navController.navigate("login")
                }
            )
            Button(
                foreColor = Color(0xFFFAFAFA),
                pressColor = Color(0xFF000000),
                text = "Registrarse",
                onClick = {
                    navController.navigate("register")
                }
            )
        }
    }
}