import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea

@Composable
fun EventHandlingDemo() {
    Button(
        attrs = {
            onClick { event ->
                println("按钮在( ${event.movementX}, ${event.movementY} )处被点击")
            }
        }
    ) {
        Text("按钮")
    }

    val text = remember { mutableStateOf("") }

    TextArea(
        value = text.value,
        attrs = {
            onInput {
                text.value = it.value
            }
        }
    )

    Span {
        Text("输入文本 = ${text.value}")
    }
}