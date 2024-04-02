package com.devdavidm.invoicemanagerapp.onboardingpage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devdavidm.invoicemanagerapp.R

@Composable
fun OnboardingPage(navController: NavController){
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Surface(color = Color(0xFFFAFAFA)) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color(0xFF000000),
                    radius = size.width+30,
                    center = Offset(0f, 10f)
                )
                drawLine(
                    color = Color(0xFFFAFAFA),
                    start = Offset(0f, size.height*0.05f),
                    end = Offset(size.width, size.height*0.05f),
                    strokeWidth = 20f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(size.width*0.1f, size.width*0.05f), 0f)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 100.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ){
            Image(
                modifier = Modifier
                    .offset((-screenWidth*0.15).dp, (-screenHeight*0.2).dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo"
            )
        }
        Column(
            modifier = Modifier
                .padding(0.dp, 150.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier.height((screenHeight*0.06).dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFAFAFA),
                    containerColor = Color(0xFF000000)
                ),
                onClick = { navController.navigate("login") }
            ) {
                Text(text = "Iniciar sesión")
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier.height((screenHeight*0.06).dp),
                border = BorderStroke(3.dp, Color(0xFF000000)),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF000000),
                    containerColor = Color(0xFFFAFAFA)
                ),
                onClick = { navController.navigate("register") }
            ) {
                Text(text = "Registrarse")
            }
        }
    }
}