package com.devdavidm.invoicemanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.devdavidm.invoicemanagerapp.loginpage.LoginPage
import com.devdavidm.invoicemanagerapp.ui.theme.InvoiceManagerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InvoiceManagerAppTheme {
                LoginPage()
            }
        }
    }
}