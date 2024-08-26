package com.example.testapplication1.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClickTest(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            var text by remember { mutableIntStateOf(0) }
            Button(onClick = { text++ }) { Text("Button1") }
            Spacer(Modifier.width(20.dp))
            Text(text.toString(), fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            var text by remember { mutableIntStateOf(100) }
            CompositionLocalProvider(
                LocalRippleConfiguration provides RippleConfiguration(Color.Blue),
            ) {
                Button(
                    onClick = { text-- },
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.buttonColors().copy(containerColor = Color.Transparent),
                ) { Text("Button2", color = MaterialTheme.colorScheme.secondary) }
            }
            Spacer(Modifier.width(20.dp))
            Text(text.toString(), fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}
