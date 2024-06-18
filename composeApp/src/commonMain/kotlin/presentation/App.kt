package presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.navigator.Navigator
import daniel.avila.rnm.kmm.presentation.ui.theme.AppTheme
import di.initKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.ui.home.HomeScreen


@Composable
@Preview
fun App() {
    initKoin()
    AppTheme {
        val scope = rememberCoroutineScope()
        Navigator(HomeScreen())
    }

}