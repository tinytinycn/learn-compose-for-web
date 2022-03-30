# 使用 Compose Web 构建 UI

在本教程中，我们将查看几个使用可组合 DOM DSL 来描述 Web 应用程序用户界面的示例。

## Entry point 入口

Compose for Web 需要一个 HTML 节点作为其组合的根。在这个根节点内，Compose 然后管理它自己的 DOM 树。

```kotlin
renderComposable(rootElementId = "root") {
    // 内容写这里
}
```

## Compose DOM DSL 中的 HTML 标签

虽然 Compose for Web 的 DOM DSL 还没有为每个 HTML 标签提供 Composable，但最常用的 HTML 标签可以直接使用。

让我们看一下 Div 标签的 Composable（大多数其他标签具有相同的签名）：

```kotlin
Div(
    attrs = {
        // 在此处指定属性
        style {
            // 在此处指定内联样式
        }
    }
) {
    // div 内容写这里
}
```

为方便起见，一些标签（如 `Input`、`A`、`Form` 或 `Img`）允许您在签名中指定一些特定于相应 HTML 标签的额外参数。例如，让我们看一下 `Input` 标签：

```kotlin
Input(
    type = InputType.Text, // 所有 InputTypes 都支持
    attrs = {}
)
```

我们可以使用为方便起见提供的类型参数，也可以使用 attrs 块来指定输入类型： `Input(attrs = { type(InputType.Text) })`

## 文本

`Text` 允许您将文本内容添加到 HTML 标记。此外，它所代表的文本内容，它没有任何参数：`Text("Arbitrary text")`

如果要将样式应用于文本，则需要将其包装在应用了样式的容器中，例如 `Span` 或 `P`：

```kotlin
Span(
    attrs = { style { color(Color.red) } } // inline style
) {
    Text("Red text")
}
```

这对应于以下 HTML 代码：

```html
<span style="color: red;">Red text</span>
```

## 属性

attrs 参数（我们已经在前面的一些示例中看到过）允许我们指定元素的属性和属性。

定义属性最灵活的方法是使用 attr 函数，它允许您指定属性名称及其值。

```kotlin
Div(
    attrs = {
        attr(attr = "custom_attr", value = "its_value")
    }
) { /* content */ }
```

但是，使用这种方法，Web 版 Compose 无法验证该属性是否存在于 HTML 元素上，或者是否有效。这就是为什么我们还为公共属性提供了一组辅助函数。

### 共同属性 Common attributes

下面是一些可用于表示 HTML 标记的大多数 Composable 的常见属性示例：

```kotlin
attrs = {
    id("elementId")
    classes("cl1", "cl2")
    hidden(false)
    title("title")
    draggable(Draggable.Auto)
    dir(DirType.Auto)
    lang("en")
    contentEditable(true)
}
```

### 元素特定属性

根据您正在使用的元素，您可能还可以访问一些特定的属性——这些属性只对这个特定的标签有意义。例如，`A` 标签提供了一些特定于超链接的特定属性：

```kotlin
A(
    attrs = {
        href("https://localhost:8080/page2")
        target(ATarget.Blank)
        rel(ARel.Next)
        hreflang("en")
        download("https://...")
    }
) {}
```

提供特定属性的其他一些元素包括：

- Button
- Form
- Input
- Option
- Select
- OptGroup
- TextArea
- Img

要发现当前范围内可用的所有属性，您可以使用 IDE 的自动完成功能。随着我们对这些 API 的发展，我们还计划为它们添加详细的文档。

### 事件 events

您可以在 `attrs` 块中声明事件侦听器：

```kotlin
Button(
    attrs = {
        onClick { println("Button clicked") }
    }
) { Text("Button") }
```

## 样式 style

有几种方法可以设置组件的样式：

- 使用内联样式
- 使用样式表

您可以通过组件的样式块声明内联样式：

```kotlin
Div(
    attrs = {
        style {
            display(DisplayStyle.Flex)
            padding(20.px)

            // 自定义属性
            property("font-family", "Arial, Helvetica, sans-serif")
        }
    }
) { /* content goes here */ }
```

您可以在此处找到有关样式 DSL 的更详细概述以及其他示例 - [样式 DSL](https://github.com/JetBrains/compose-jb/blob/master/tutorials/Web/Style_Dsl/README.md)