package com.devdavidm.invoicemanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devdavidm.invoicemanagerapp.camerapreview.CameraPreviewPage
import com.devdavidm.invoicemanagerapp.forgotpasswordpage.ForgotPasswordPage
import com.devdavidm.invoicemanagerapp.homepage.HomePage
import com.devdavidm.invoicemanagerapp.homepage.NewClientPage
import com.devdavidm.invoicemanagerapp.invoicespages.NewInvoicePage
import com.devdavidm.invoicemanagerapp.loginpage.LoginPage
import com.devdavidm.invoicemanagerapp.onboardingpage.OnboardingPage
import com.devdavidm.invoicemanagerapp.productspage.NewProductPage
import com.devdavidm.invoicemanagerapp.productspage.UpdateProductPage
import com.devdavidm.invoicemanagerapp.registerpage.RegisterPage
import com.devdavidm.invoicemanagerapp.ui.theme.InvoiceManagerAppTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

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
    val db = Firebase.firestore
    NavHost(navController = navController, startDestination = "onboarding"){
        composable("onboarding"){ OnboardingPage(navController) }
        composable("login"){ LoginPage(context = context, navController, auth) }
        composable("forgot_password"){ ForgotPasswordPage(context = context, navController, auth) }
        composable("register"){ RegisterPage(context = context, navController, auth) }
        composable("home"){ HomePage(navController, auth, db) }
        composable("home/Facturas"){ HomePage(navController, auth, db, "Facturas") }
        composable("home/Clientes"){ HomePage(navController, auth, db, "Clientes") }
        composable("home/Productos"){ HomePage(navController, auth, db, "Productos") }
        composable("new_invoice"){ NewInvoicePage(navController, db) }
        composable("new_customer"){ NewClientPage(navController, db) }
        composable("new_product"){ NewProductPage(navController, db) }
        composable("update_product/{id}"){backStackEntry ->
            backStackEntry.arguments?.getString("id")
                ?.let { UpdateProductPage(navController, db, it) }
        }
        composable("cameraPreview"){ CameraPreviewPage(navController) }
    }
    if(auth.currentUser != null){
        navController.navigate("home"){
            popUpTo("onboarding"){
                inclusive = true
            }
        }
    }
}