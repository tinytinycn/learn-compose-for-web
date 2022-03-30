# Compose for Web 包含的模块 modules

## 模块 `compose.runtime`

它提供了 Compose 编程模型和状态管理的基本构建块。 Compose for Web 使用 Compose 的运行时实现。

## 模块 `compose.web.core`

它提供：

- 基于 HTML DOM 构建可组合组件的 DSL
- 全面的 CSS-in-Kotlin/JS API

请查看教程以了解有关 compose.web.core 的更多信息

## 模块 `compose.web.svg`

它提供了一组表示 SVG 元素的可组合函数。这些函数可用于构建基于 SVG 的 Composable Web UI 组件。

**实验性模块：此模块中的 API 尚未稳定，预计会发生重大变化。**

## 模块 `compose.web.testUtils`

它提供了一些实用函数来简化基于 HTML DOM 的可组合组件的单元测试。

```kotlin
// 假设应用了插件 id("org.jetbrains.compose")
sourceSets {
    val jsTest by getting {
        implementation(kotlin("test-js"))
        implementation(compose.web.testUtils)
    }
}
```