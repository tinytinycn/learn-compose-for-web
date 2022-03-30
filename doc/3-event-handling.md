# Compose Web 中的事件处理

您可以在 attrs 块中添加事件侦听器：

### onClick

[鼠标事件 MouseEvent](https://developer.mozilla.org/en/docs/Web/API/MouseEvent)

```kotlin
Button(
    attrs = {
        onClick { event ->
            // 事件是 `SyntheticMouseEvent` 类型
            println("button clicked at ${event.movementX}, ${event.movementY}")

            val nativeEvent = event.nativeEvent // 
        }
    }
) {
    Text("Button")
}
```

### onInput

```kotlin
val text = remember { mutableStateOf("") }

TextArea(
    value = text.value,
    attrs = {
        onInput {
            text.value = it.value
        }
    }
)
```

## 其他事件处理程序

对于在 `attrs` 块中没有自己的配置函数的事件，您可以使用 `addEventListener` 和事件`name`、选项`options`，并传递一个接收 `SyntheticEvent` 的 `eventListener`
。在这个例子中，我们定义了一个表单元素在触发提交`submit`事件时的行为：

```kotlin
Form(attrs = {
    this.addEventListener("submit") {
        console.log("Hello, Submit!")
        it.preventDefault()
    }
})
```

开箱即用支持更多事件侦听器。我们计划稍后为他们添加文档。同时，您可以在[源代码](https://github.com/JetBrains/compose-jb/blob/master/web/core/src/jsMain/kotlin/org/jetbrains/compose/web/attributes/EventsListenerBuilder.kt)中找到所有支持的事件侦听器。

## 可运行的事例

```kotlin
import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Button(
            attrs = {
                onClick { event ->
                    println("button clicked at ${event.movementX}, ${event.movementY}")
                }
            }
        ) {
            Text("Button")
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
            Text("Typed text = ${text.value}")
        }
    }
}
```