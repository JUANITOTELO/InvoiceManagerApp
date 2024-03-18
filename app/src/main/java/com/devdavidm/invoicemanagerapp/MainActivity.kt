package com.devdavidm.invoicemanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devdavidm.invoicemanagerapp.homepage.HomePage
import com.devdavidm.invoicemanagerapp.loginpage.LoginPage
import com.devdavidm.invoicemanagerapp.onboardingpage.OnboardingPage
import com.devdavidm.invoicemanagerapp.registerpage.RegisterPage
import com.devdavidm.invoicemanagerapp.ui.theme.InvoiceManagerAppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvoiceManagerAppTheme {
                MyApp(this)
            }
        }
    }
}

@Composable
fun MyApp(context: ComponentActivity){
    val navController = rememberNavController()
    val auth = Firebase.auth
    NavHost(navController = navController, startDestination = "onboarding"){
        composable("onboarding"){ OnboardingPage(navController) }
        composable("login"){ LoginPage(context = context, navController, auth) }
        composable("register"){ RegisterPage(context = context, navController, auth) }
        composable("home"){ HomePage(navController, auth) }
    }
    if(auth.currentUser != null){
        navController.navigate("home"){
            popUpTo("login"){
                inclusive = true
            }
        }
    }
}