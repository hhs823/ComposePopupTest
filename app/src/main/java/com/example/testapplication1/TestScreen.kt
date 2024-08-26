package com.example.testapplication1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testapplication1.component.AnimationTest
import com.example.testapplication1.component.ClickTest
import com.example.testapplication1.component.HorizontalPagerTest
import com.example.testapplication1.ui.theme.TestApplication1Theme

fun ComposeView.test(viewModel: TestViewModel) {
    setContent {
        TestApplication1Theme {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            TestScreen(uiState)
        }
    }
}

@Composable
fun TestScreen(
    uiState: TestUiState,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray),
    ) {
        item { ClickTest() }
        item { Spacer(Modifier.height(12.dp)) }
        item { AnimationTest(uiState.color, uiState.time) }
        item { Spacer(Modifier.height(12.dp)) }
        item { HorizontalPagerTest() }
        item { Spacer(Modifier.height(12.dp)) }
        items(
            count = 51,
            key = { it },
        ) {
            val background = Color.Red.copy(it / 50f)
                .compositeOver(Color.White)
            Column {
                Text(
                    text = "${2 * it}%",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(background)
                        .wrapContentSize(Alignment.Center),
                    color = Color.Black,
                    fontSize = 20.sp,
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTestScreen() {
    TestApplication1Theme {
        TestScreen(TestUiState())
    }
}
