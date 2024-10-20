useCodeBlocks = true
resourceBaseUrl = "res"
page.title = "Test"
page.sectioned = true
page.inkFooter = true

include("header.inkui.kts")
addBody(
    PageLayout(
        ElementList(
            items = listOf(
                TextElement("Heading 1", TextStyle.H1),
                TextElement("Heading 2", TextStyle.H2),
                TextElement("Heading 3", TextStyle.H3),
                TextElement("Body Text", TextStyle.Body),
                TextElement("Caption", TextStyle.Caption),
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
                        text("hello")
                        space()
                        strong {
                            text("world")
                        }
                    },
                    FormattedText {
                        text("this is an item list")
                    }
                ),
                DividerElement,
                StatusIndicatorElement(
                    text = "Nominal Status",
                    sentiment = Sentiment.Nominal,
                ),
                StatusIndicatorElement(
                    text = "Primary Status",
                    sentiment = Sentiment.Primary,
                ),
                StatusIndicatorElement(
                    text = "Positive Status",
                    sentiment = Sentiment.Positive,
                ),
                StatusIndicatorElement(
                    text = "Negative Status",
                    sentiment = Sentiment.Negative,
                ),
                StatusIndicatorElement(
                    text = "Caution Status",
                    sentiment = Sentiment.Caution,
                ),
                StatusIndicatorElement(
                    text = "Idle Status",
                    sentiment = Sentiment.Idle,
                ),
                IconElement(
                    symbol = Symbol.House,
                    sentiment = Sentiment.Caution,
                ),
                LinkButtonElement(
                    url = "https://www.example.com",
                    text = "Test",
                    sentiment = Sentiment.Positive,
                    leadingSymbol = Symbol.Cloud,
                ),
                CodeBlock(
                    language = Language.Kotlin,
                    code = """
  fun main() {
    println("Hello, World!")
  }
                    """.trimIndent()
                ),
                LinkNavigation {
                    link(text = "Link 1", url = "#")
                    link(text = "Link 2", url = "#")
                },
            )
        )
    )
)
