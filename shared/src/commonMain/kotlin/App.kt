import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import de.dannyb.imnuri.networking.Api
import de.dannyb.imnuri.networking.screens.home.HomeScreen

@Composable
fun App() = MaterialTheme {
    val api by remember { mutableStateOf(Api()) }
    HomeScreen(api)
}