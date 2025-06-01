package example

import ink.ui.structures.TextStyle
import ink.ui.structures.elements.DividerElement
import ink.ui.structures.elements.FormattedText
import ink.ui.structures.elements.TextElement
import ink.ui.structures.elements.TextListElement
import ink.ui.structures.elements.items

val Typography = items(
    TextElement("Heading 1", TextStyle.H1),
    TextElement("Heading 2", TextStyle.H2),
    TextElement("Heading 3", TextStyle.H3),
    TextElement("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", TextStyle.Body),
    TextElement("This is a caption", TextStyle.Caption),
    DividerElement,
    FormattedText {
        link(url = "javascript:void(0)") {
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
    },
    TextListElement(
        FormattedText {
            text("This is a ")
            strong("text list")
        },
        FormattedText {
            text("with multiple items")
        }
    ),
)
