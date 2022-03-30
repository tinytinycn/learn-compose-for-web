import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun BuildingUIDemo() {

    Div(
        attrs = {
            // specify attributes here
            style {
                // specify inline style here
            }
        }
    ) {
        Text("A text in <div>")
    }

    Input(
        type = InputType.Text, // All InputTypes supported
        attrs = {}
    )

    Text("Arbitrary text")

    Span({
        style { color(Color.red) } // inline style
    }) {
        Text("Red text")
    }

    Div(
        attrs = {
            id("elementId")
            classes("cl1", "cl2")
            hidden()
            title("title")
            draggable(Draggable.Auto)
            dir(DirType.Auto)
            lang("en")
            contentEditable(true)

            // custom attr
            attr(attr = "custom_attr", value = "its_value")
        }
    ) { /* content */ }

    A(
        attrs = {
            href("https://localhost:8080/page2")
            target(ATarget.Blank)
            hreflang("en")
            download("https://...")
        }
    ) { Text("Link") }

    Button(
        attrs = {
            onClick { println("Button clicked") }
        }
    ) { Text("Button") }

    Div({
        style {
            display(DisplayStyle.Flex)
            padding(20.px)

            // custom property
            property("font-family", "Arial, Helvetica, sans-serif")
        }
    }) { Text("Text in Div with inline style") }

}