include("base.inkui.part.kts")
page.title = "Ink UI - Elements"

addPageHeader(
    ElementList(
        TextElement("Elements", TextStyle.H1),
        TextElement("Built-in UI elements"),
        BreadcrumbElement {
            link("Ink UI", "index.html")
            text("Elements")
        },
        groupingStyle = GroupingStyle.Inline,
    ),
)
addBody(
    ScrollingListLayout(
        ElementList(
            TextElement("Status Indicators", TextStyle.H1),
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
            groupingStyle = GroupingStyle.Inline,
        ),
        ElementList(
            TextElement("Symbols", TextStyle.H1),
            IconElement(
                symbol = Symbol.Add,
                sentiment = Sentiment.Primary,
            ),
            IconElement(
                symbol = Symbol.Alarm,
                sentiment = Sentiment.Positive,
            ),
            IconElement(
                symbol = Symbol.AlarmOff,
                sentiment = Sentiment.Negative,
            ),
            IconElement(
                symbol = Symbol.ArrowBackward,
                sentiment = Sentiment.Caution,
            ),
            IconElement(
                symbol = Symbol.ArrowForward,
                sentiment = Sentiment.Idle,
            ),
            IconElement(Symbol.Bed),
            IconElement(Symbol.Caution),
            IconElement(Symbol.Close),
            IconElement(Symbol.Cloud),
            IconElement(Symbol.Done),
            IconElement(Symbol.Edit),
            IconElement(Symbol.Error),
            IconElement(Symbol.Group),
            IconElement(Symbol.House),
            IconElement(Symbol.Lock),
            IconElement(Symbol.LockOpen),
            IconElement(Symbol.Moon),
            IconElement(Symbol.Movie),
            IconElement(Symbol.Person),
            IconElement(Symbol.Rain),
            IconElement(Symbol.Remove),
            IconElement(Symbol.Sunshine),
            IconElement(Symbol.Temperature),
            IconElement(Symbol.Water),

            groupingStyle = GroupingStyle.Inline,
        ),
        ElementList(
            TextElement("Buttons", TextStyle.H1),
            LinkButtonElement(
                text = "Button",
                url = "javascript:void(0)",
            ),
            LinkButtonElement(
                text = "Primary Button",
                url = "javascript:void(0)",
                sentiment = Sentiment.Primary,
            ),
            LinkButtonElement(
                text = "Icon Button",
                url = "javascript:void(0)",
                sentiment = Sentiment.Positive,
                leadingSymbol = Symbol.Cloud,
            ),
        ),
        ElementList(
            TextElement("Link Navigation", TextStyle.H1),
            LinkNavigation {
                link(text = "Link 1", url = "#")
                link(text = "Link 2", url = "#")
            },
            groupingStyle = GroupingStyle.Inline,
        ),
        groupingStyle = GroupingStyle.Sections,
    )
)
