package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.elements.*

val Typography = ElementList.build(groupingStyle = Items) {
    h1("Heading 1")
    h2("Heading 2")
    h3("Heading 3")
    text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
    caption("This is a caption")

    divider()

    formattedText {
        link(url = "https://inkapplications.com") {
            text("Formatted")
        }
        space()
        strong {
            text("Text")
            emphasis("!!!")
        }
        space()
        text("with")
        space()
        code("code")
        space()
        text("and")
        space()
        code(group = true) {
            text("grouped")
            text("references")
        }
    }
    textList {
        formattedText {
            text("This is a ")
            strong("text list")
        }
        formattedText {
            text("with multiple items")
        }
    }
}
