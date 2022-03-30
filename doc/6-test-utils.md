# 使用 test-utils 进行 DOM DSL 单元测试

## 依赖

需要将 `compose.web.testUtils` 添加到 `jsTest` 依赖项中：

```kotlin
sourceSets {
    val jsMain by getting {
        dependencies {
            implementation(compose.web.core)
            implementation(compose.runtime)
            //....
        }
    }
    val jsTest by getting {
        implementation(kotlin("test-js"))
        implementation(compose.web.testUtils)
        //...
    }
}
```

## 例子

```kotlin
// 这个函数是你想进行测试test
@Composable
fun TestButton(text: String, onButtonClick: () -> Unit) {
    Button(attrs = {
        onClick { onButtonClick() }
    }) {
        Text(text)
    }
}
```

让我们添加一个测试以确保按钮具有正确的文本，并且它的 onClick 工作正常。

```kotlin
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.w3c.dom.HTMLButtonElement
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ComposeWebExperimentalTestsApi::class)
class TestsForButton {

    @Test
    fun testButton() = runTest {
        var counter by mutableStateOf(1)

        composition {
            TestButton(text = "$counter") {
                counter++
            }
        }

        assertEquals("<button>1</button>", root.innerHTML)

        (root.firstChild!! as HTMLButtonElement).click()
        waitForRecompositionComplete()
        assertEquals("<button>2</button>", root.innerHTML)

        counter = 10
        waitForRecompositionComplete()
        assertEquals("<button>10</button>", root.innerHTML)
    }
}
```

### 让我们分解一下上述代码：

#### `runTest { ... }`

为 TestScope 提供有用的功能来配置测试。

#### `composition { ... }`

采用带有我们要测试的内容的 `@Composable` 块。它会自动构建 DOM 并将其挂载到 `root` 元素中。

#### `root`

它不应该用于元素操作。对 html 内容（例如 `root.innerHtml`）进行断言非常有用。

#### `nextChild() and currentChild()`

在后台 `nextChild()` 迭代根子节点，提供对它们的方便访问。

`currentChild()` 不会移动迭代器并且每次都返回相同的元素，直到调用 `nextChild()`。

#### `waitForRecompositionComplete()`

它暂停直到重组完成。当状态改变时它很有用，我们也想测试内容更新。 `waitForRecompositionComplete` 需要在状态改变之后和断言之前调用。

#### `waitForChanges(id: String)`

它会挂起，直到具有 `id` 的元素发生任何更改。确保状态更改对内容进行相应更新也很有用。