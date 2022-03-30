# Compose Web 中的 Style DSL

在本教程中，我们将了解如何使用 Style DSL 为组件设置样式。它是样式表的类型安全 DSL，您可以使用它在 Kotlin 代码中表达 CSS 规则，甚至可以根据 Compose 应用程序的状态修改样式。

## 内联样式 Inline Style

您可以通过组件的 `style` 块声明内联样式

```kotlin
Div({
    style {
        display(DisplayStyle.Flex)
        padding(20.px)
        
        // 自定义属性（或不支持开箱即用）
        property("font-family", "Arial, Helvetica, sans-serif")
    }
}) { /* content goes here */ }
```

在 HTML 中，它将如下所示：

```html
<div style="display: flex; padding: 20px; font-family: Arial, Helvetica, sans-serif;"></div>
```

## 样式表 Stylesheet

另一种方法是定义包含规则的样式表：

```kotlin
object AppStylesheet : StyleSheet() {
    val container by style { // container 是一个 class 类
        display(DisplayStyle.Flex)
        padding(20.px)

        // 自定义属性（或不支持开箱即用）
        property("font-family", "Arial, Helvetica, sans-serif")
    }
}

// Stylesheet 样式表需要被 mounted 挂载:
renderComposable("root") {
    Style(AppStylesheet)
    
    Container {
        Text("Content")
    }
}

@Composable
fun Container(content: @Composable () -> Unit) {
    Div(
        attrs = { classes(AppStylesheet.container) }
    ) {
        content()
    }
}
```

在 HTML 中，它将如下所示：

```html
<style></style>
<div class="AppStylesheet-container">Content</div>
```

## 选择器示例 Selectors

Style DSL 还提供了一种组合和统一选择器的方法：

```kotlin
object AppStylesheet : StyleSheet() {
    
    init {
        // `universal` can be used instead of "*": `universal style {}`
        "*" style { 
            fontSize(15.px)
            padding(0.px)
        }
        
        // raw selector 原始选择器
        "h1, h2, h3, h4, h5, h6" style {
            property("font-family", "Arial, Helvetica, sans-serif")
            
        }

        // combined selector 组合选择器
        type("A") + attr( // 选择所有标签 <a> 并且 href 包含 'jetbrains' 的
            name = "href",
            value = "jetbrains",
            operator = CSSSelector.Attribute.Operator.Equals
        ) style {
            fontSize(25.px)
        }
    }
    
    // 便捷地方式创建一个 class selector 类选择器
    // AppStylesheet.container can be used as a class in component attrs
    val container by style {
        color(Color.red)
        
        // hover selector for a class
        self + hover() style { // self is a selector for `container`
            color(Color.green)
        }
    }
}
```

## 媒体查询示例 Media query

要指定媒体查询，您可以使用 `media` 函数，它接受相关查询和一组样式：

```kotlin
object AppStylesheet : StyleSheet() {
    val container by style {
        padding(48.px)

        media(maxWidth(640.px)) {
            self style {
                padding(12.px)
            }
        }
    }
}
```

## CSS 变量

样式 DSL 还提供对 CSS 变量的支持。

```kotlin
object MyVariables {
    // declare a variable
    val contentBackgroundColor by variable<CSSColorValue>()
}

object MyStyleSheet: StyleSheet() {
    
    val container by style {
        //set variable's value for the `container` scope
        MyVariables.contentBackgroundColor(Color("blue"))
    }
    
    val content by style {
        // get the value
        backgroundColor(MyVariables.contentBackgroundColor.value())
    }

    val contentWithDefaultBgColor by style {
        // default value can be provided as well
        // default value is used when the value is not previously set
        backgroundColor(MyVariables.contentBackgroundColor.value(Color("#333")))
    }
}
```

## 可运行示例

```kotlin
import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

object MyVariables {
    // declare a variable
    val contentBackgroundColor by variable<CSSColorValue>()
}

object MyStyleSheet: StyleSheet() {

    val container by style {
        //set variable's value for the `container` scope
        MyVariables.contentBackgroundColor(Color("blue"))
    }

    val content by style {
        // get the value
        backgroundColor(MyVariables.contentBackgroundColor.value())
    }

    val contentWithDefaultBgColor by style {
        // default value can be provided as well
        // default value is used when the value is not previously set
        backgroundColor(MyVariables.contentBackgroundColor.value(Color("#333")))
    }
}

object AppStylesheet : StyleSheet() {
    val container by style { // container is a class
        display(DisplayStyle.Flex)
        padding(20.px)

        // custom property (or not supported out of a box)
        property("font-family", "Arial, Helvetica, sans-serif")
    }
}

@Composable
fun Container(content: @Composable () -> Unit) {
    Div(
        attrs = { classes(AppStylesheet.container) }
    ) {
        content()
    }
}

fun main() {
    renderComposable(rootElementId = "root") {
        Div({
            style {
                display(DisplayStyle.Flex)
                padding(20.px)

                // custom property (or not supported out of a box)
                property("font-family", "Arial, Helvetica, sans-serif")
            }
        }) { /* content goes here */ }


        Style(AppStylesheet)

        Container {
            Text("Content")
        }
    }
}
```
