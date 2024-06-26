import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import presentation.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kmm_Birds",
    ) {
        App()
    }
}