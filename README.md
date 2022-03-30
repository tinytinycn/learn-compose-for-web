# 导航

- 开始使用 Compose for Web
- [模块介绍](doc/1-modules-intro.md)
- [构建UI](doc/2-building-ui.md)
- [事件处理](doc/3-event-handling.md)
- [受控与非受控输入组件](doc/4-controlled-and-uncontrolled-inputs.md)
- [Style DSL](doc/5-style-dsl.md)
- [测试](doc/6-test-utils.md)

----

# 开始使用 Compose for Web

在本教程中，我们将使用 Compose UI 框架创建一个简单的 Web UI 应用程序。

## 先决条件

在开始之前，您需要安装以下软件：

- JDK 11 或更新版本
- IntelliJ IDEA Community Edition 或 Ultimate Edition 2020.2 或更高版本（您可以使用其他编辑器，但对于本教程我们假设您使用的是 IntelliJ IDEA）

## 创建一个新项目

如果不想手动创建项目，可以在这里[下载模板](https://github.com/tinytinycn/learn-compose-for-web)

项目 wizard 尚不支持 Compose for web 项目，因此我们需要执行以下步骤：

1. 创建一个 Kotlin 多平台项目:

选择 Gradle >> 勾选 `kotlin DSL build script` >> 勾选 `kotlin/Multiplatform`

2. 更新 `setting.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
```

3. 更新 `build.gradle.kts`:

```kotlin
// 添加 compose gradle plugin 插件
plugins {
    kotlin("multiplatform") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}

// 添加 maven repo 仓库
repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

// 启用 JS(IR) target 并添加依赖项
kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
    }
}
```

4. 将以下目录添加到项目中:

- src/jsMain/kotlin
- src/jsMain/resources

5. 新增 `index.html` 文件到 `resources`:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sample</title>
</head>
<body>
<div id="root"></div>
<script src="替换成你的模块名称.js"></script>
</body>
</html>
```

6. 新增 `Main.kt` 文件到 `kotlin`:

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        AppBody()
    }

}

@Composable
fun AppBody() {
    val counter = remember { mutableStateOf(0) }
    Div { Text("点击数: ${counter.value}") }

    Button(attrs = { onClick { counter.value++ } }) {
        Text("点")
    }
}
```

## 运行项目

使用命令行运行: `./gradlew jsBrowserRun`

无需每次想查看所做的更改时手动编译和执行 Kotlin/JS 项目，您可以使用持续编译模式: `./gradlew jsBrowerRun --continuous`

浏览器将自动打开: [localhost:8080](http://localhost:8080)