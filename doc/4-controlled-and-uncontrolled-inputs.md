# 受控和不受控 input组件 Controlled and Uncontrolled inputs

Compose for Web 中的输入组件有两种模式：受控 controlled 和不受控 uncontrolled。

## 受控与不受控的行为

让我们使用不同的模式创建两个输入并比较它们：

```kotlin
// 不受控 Uncontrolled
Input(type = InputType.Text) {
    defaultValue("Initial Value") // 可选
    onInput { event -> println(event.value) }
}

// 受控 Controlled
Input(type = InputType.Text) {
    value("Some value") // 调用 value(...) 是使输入“受控”所必需的
    onInput { event -> println(event.value) }
}
```

如果您尝试运行这些代码段，您将看到以下行为：

- 不受控制的文本输入将显示“Initial Value”。键入将对输入的状态进行相应的更改。
- 受控文本输入将显示“Some value”。但是打字不会引起任何改变。
- 两个输入都将在 onInput { } 处理程序中接收事件

在上面的示例中，我们设置了硬编码值 - value("Some value")。因此，打字什么也不做。在引擎盖下，受控输入根据最后一个已知值“恢复”其状态。

## 将 `MutableState` 与受控输入 Controlled Input 一起使用

为了使受控输入更有用，我们可以使用 `MutableState<*>` 来保持输入的值：

```kotlin
val inputState = remember { mutableStateOf("Some Text") }

Input(type = InputType.Text) {
    value(inputState.value)
    onInput { event -> println(event.value) }
}
```

可以看到，如果我们按上述示例的方式运行，将看到与硬编码 `value(...)` 时相同的行为, `inputState` 永远不会发生变化。 但是，如果我们更新 `inputState` 的代码，那么 Input 将重组并显示新值。

在大多数情况下，需要在 onInput 事件处理程序中更改 inputState：

```kotlin
val inputState = remember { mutableStateOf("Some Text") }

Input(type = InputType.Text) {
    value(inputState.value)
    onInput { event -> inputState.value = event.value } // 区别在这里
}
```

## 结论

非受控输入独立更改其内容，而受控输入的内容只能通过外部状态（例如 MutableState）进行更改。

**在大多数情况下，受控输入是默认选择。**

## 方便使用的受控输入 inputs 组件

以下是表示不同类型的受控输入的可组合函数列表：

- CheckboxInput 复选框输入
- DateInput 日期输入
- DateTimeLocalInput 日期时间本地输入
- EmailInput 电子邮件输入
- FileInput 文件输入
- MonthInput 月输入
- NumberInput 数字输入
- PasswordInput 密码输入
- RadioInput 选择输入
- RangeInput 范围输入
- SearchInput 搜索输入
- TelInput 电话输入法
- TextInput 文本输入
- TimeInput 时间输入
- UrlInput 网址输入
- WeekInput 周输入

例子

```kotlin
val inputState = remember { mutableStateOf("Some Text") }

TextInput(value = inputState.value) {
    onInput { event -> inputState.value = event.value }
}
```