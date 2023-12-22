package com.meanwhile.leakedflows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meanwhile.leakedflows.ui.theme.LeakedFlowsTheme
import com.meanwhile.leakedflows.ui.theme.Typography
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LeakingViewModel by viewModels()
    //private val viewModel: FixedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeakedFlowsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CountScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun CountScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(UiState(0, 0))
    CountScreenStateless(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onButtonClick = { viewModel.refresh() }
    )
}

@Composable
fun CountScreenStateless(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    val colorAnim = remember { Animatable(Color.Gray) }
    LaunchedEffect(uiState.count) {
        colorAnim.animateTo(Color.Red, animationSpec = tween(50))
        colorAnim.animateTo(Color.Gray, animationSpec = tween(500))
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(modifier = modifier.padding(32.dp)) {
            Text(
                text = "${uiState.count}",
                style = Typography.headlineLarge,
                modifier = Modifier
                    .drawBehind { drawRect(colorAnim.value) }
                    .padding(16.dp)
            )
            Text(
                text = "Count since last refresh",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(onClick = onButtonClick) {
                Text(text = "Refresh")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LeakedFlowsTheme {
        CountScreenStateless(UiState(3, 999), onButtonClick = { })
    }
}
