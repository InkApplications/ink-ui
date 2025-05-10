package example

import ink.ui.render.terminal.TerminalRenderer
import ink.ui.structures.Sentiment
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.StackElement
import ink.ui.structures.elements.StatusIndicatorElement
import ink.ui.structures.elements.TextElement
import ink.ui.structures.elements.inline
import ink.ui.structures.elements.println
import ink.ui.structures.layouts.ScrollingListLayout
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking

fun main()
{
    val renderer = TerminalRenderer()
    val stack = StackElement()

    val job = renderer.renderAsync(
        ScrollingListLayout(
            TextElement("Terminal Renderer", style = TextStyle.H1),
            stack
        )
    )

    runBlocking {
        stack.print(TextElement("Heading 2", TextStyle.H2))
        stack.print(TextElement("Heading 3", TextStyle.H3))
        stack.println("Body text")
        stack.print(
            inline(
                StatusIndicatorElement(
                    text = "Primary Status",
                    sentiment = Sentiment.Primary
                ),
                StatusIndicatorElement(
                    text = "Nominal Status",
                    sentiment = Sentiment.Nominal
                ),
                StatusIndicatorElement(
                    text = "Idle Status",
                    sentiment = Sentiment.Idle
                ),
                StatusIndicatorElement(
                    text = "Positive Status",
                    sentiment = Sentiment.Positive
                ),
                StatusIndicatorElement(
                    text = "Caution Status",
                    sentiment = Sentiment.Caution
                ),
                StatusIndicatorElement(
                    text = "Negative Status",
                    sentiment = Sentiment.Negative
                ),
            )
        )
        job.cancelAndJoin()
    }
}
