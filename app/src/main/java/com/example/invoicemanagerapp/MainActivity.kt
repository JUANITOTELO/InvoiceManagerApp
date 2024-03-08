package com.example.invoicemanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.invoicemanagerapp.loginpage.LoginPage
import com.example.invoicemanagerapp.ui.theme.InvoiceManagerAppTheme

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