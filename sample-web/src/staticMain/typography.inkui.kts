include("base.inkui.part.kts")
page.title = "Ink UI - Typography"

addPageHeader(
    inline(
        BreadcrumbElement {
            link("Ink UI", "index.html")
            text("Typography")
        },
        TextElement("Typography", TextStyle.H1),
        TextElement("Examples of text styles"),
    ),
)

addBody(
    ScrollingListLayout(
        inline(
            TextElement("Heading 1", TextStyle.H1),
            TextElement("Heading 2", TextStyle.H2),
            TextElement("Heading 3", TextStyle.H3),
            TextElement("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", TextStyle.Body),
            TextElement("This is a caption", TextStyle.Caption),
            DividerElement,
            FormattedText {
                link(url="javascript:void(0)") {
                    text("Formatted")
                }
                space()
                strong {
                    text("Text")
                    emphasis {
                        text("!!!")
                    }
                }
                space()
                text("with")
                space()
                code { text("code") }
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
                    strong {
                        text("text list")
                    }
                },
                FormattedText {
                    text("with multiple items")
                }
            ),
            CodeBlock(
                language = Language.Kotlin,
                code = """
                    fun main() {
                        println("Hello, World!")
                    }
                """.trimIndent()
            ),
        ),
        groupingStyle = GroupingStyle.Sections,
    )
)
