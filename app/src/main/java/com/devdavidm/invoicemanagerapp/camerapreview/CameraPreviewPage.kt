package com.devdavidm.invoicemanagerapp.camerapreview

import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

@Composable
fun cameraPermissionGranted(): Boolean {
    val context = LocalContext.current
    return ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED
}

@Composable
fun CameraPreviewPage(navController: NavController) {
    val context = LocalContext.current
    val permissionsGranted = cameraPermissionGranted()
    val cameraPermissionGranted = remember { mutableStateOf(permissionsGranted) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permiso concedido", Toast.LENGTH_SHORT).show()
            cameraPermissionGranted.value = true
        } else {
            // Please grant the permission to use the camera
            Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            navController.navigate("home/Facturas")
        }
    }

    if (cameraPermissionGranted.value) {
        CameraPreview(navController)
    } else {
        LaunchedEffect(Unit) {
            launcher.launch(android.Manifest.permission.CAMERA)
        }
    }
}

@Composable
fun CameraPreview(navController: NavController) {
    val localContext = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(localContext) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val displayConfiguration = LocalConfiguration.current
    val displayHeight = displayConfiguration.screenHeightDp

    Surface(
        color = Color(0xFFFAFAFA),
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Add a camera preview
            AndroidView(
                modifier = Modifier
                    .height((displayHeight * 0.7).dp)
                    .clip(RoundedCornerShape(10.dp)),
                factory = { context ->
                val previewView = PreviewView(context)
                val cameraSelector = CameraSelector
                    .Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)

                }, ContextCompat.getMainExecutor(context))

                previewView
            })
            // Add a Circle button to take a picture
            Button(
                onClick = {
                    Toast.makeText(localContext, "Tomar foto", Toast.LENGTH_SHORT).show()
                    navController.navigate("home/Facturas")
                },
                shape = RoundedCornerShape(100),
                border = BorderStroke(2.dp, Color(0xFF000000)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFAFAFA),
                    contentColor = Color(0xFF000000)
                ),
                modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(50))
            ) {
                Text(
                    text = "Tomar foto",
                )
            }
        }
    }
}
