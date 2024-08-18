useHostedStyles()
title = "Test"
sectioned = true

addPageHeader(TextElement("Page Header", TextStyle.H1))
addPageHeader(TextElement("Subtitle"))
addBody(
    PageLayout(
        ElementList(
            items = listOf(
                TextElement("Heading 1", TextStyle.H1),
                TextElement("Heading 2", TextStyle.H2),
                TextElement("Heading 3", TextStyle.H3),
                TextElement("Body Text", TextStyle.Body),
                TextElement("Caption", TextStyle.Caption),
                StatusIndicatorElement("Nominal Status", Sentiment.Nominal),
            )
        )
    )
)
