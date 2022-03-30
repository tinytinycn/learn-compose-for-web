import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
//        AppBody()
//        BuildingUIDemo()
        EventHandlingDemo()
    }

}

@Composable
fun AppBody() {
    val counter = remember { mutableStateOf(0) }
    Div { Text("点击数: ${counter.value}") }

    Button(attrs = { onClick { counter.value++ } }){
        Text("点")
    }
}
