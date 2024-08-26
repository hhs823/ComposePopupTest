package com.example.testapplication1.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime

@Composable
fun AnimationTest(
    color: Color,
    dateTime: LocalDateTime,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 12.dp),
    ) {
        TimeAnimation(dateTime)
        ColorAnimation(color)
    }
}

@Composable
private fun TimeAnimation(
    dateTime: LocalDateTime,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedContent(
            dateTime.hour,
            label = "HOUR",
            transitionSpec = { slideInVertically { it } togetherWith slideOutVertically { -it } },
        ) {
            Text(text = it.toString(), fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
        }
        Text(text = " : ", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
        AnimatedContent(
            dateTime.minute,
            label = "MINUTE",
            transitionSpec = { slideInVertically { it } togetherWith slideOutVertically { -it } },
        ) {
            Text(text = it.toString(), fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
        }
        Text(text = " : ", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
        AnimatedContent(
            dateTime.second,
            label = "SECOND",
            transitionSpec = { slideInVertically { it } togetherWith slideOutVertically { -it } },
        ) {
            Text(text = it.toString(), fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun ColorAnimation(
    color: Color,
    modifier: Modifier = Modifier,
) {
    val colorState by animateColorAsState(color, label = "COLOR")
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(15.dp)
            .drawBehind { drawRect(colorState) }
    )
}
