package com.devdavidm.invoicemanagerapp.button

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.SnapSpec
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Button(text: String, onClick: ()->Unit) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()
    val containerButtonColor: Color by animateColorAsState(targetValue = if (isPressed) Color(0xFFD3D3D3) else Color(0xFF000000),
        label = "containerButtonColor", animationSpec = SnapSpec<Color>()
    )
    val contentButtonColor: Color by animateColorAsState(targetValue = if (!isPressed) Color(0xFFD3D3D3) else Color(0xFF000000),
        label = "contentButtonColor", animationSpec = SnapSpec<Color>()
    )
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = containerButtonColor,
            contentColor = contentButtonColor

        ),
        interactionSource = interactionSource,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .padding(all = 15.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .width(110.dp)
    ) {
        Text(text = text)
    }
}