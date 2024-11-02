package ink.ui.sample.web

import ink.ui.render.web.elements.LinkButtonElement
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.ScrollingListLayout

val SampleScreen = ScrollingListLayout(
    inline(
        TextElement(
            "Heading 1",
            TextStyle.H1,
        ),
        TextElement(
            "Heading 2",
            TextStyle.H2,
        ),
        TextElement(
            "Heading 3",
            TextStyle.H3,
        ),
        TextElement(
            "Body Text",
            TextStyle.Body,
        ),
        TextElement(
            "Caption",
            TextStyle.Caption,
        ),
    ),
    inline(
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
    ),
    items(
        TextElement(
            "Progress Indicators",
            TextStyle.H1,
        ),
        ProgressElement.Determinate(
            progress = 0.5f,
            caption = "Progress",
            sentiment = Sentiment.Positive
        ),
        ProgressElement.Indeterminate(
            caption = "Indeterminate Progress",
        ),
        ThrobberElement(
            caption = "Throbber",
        ),
    ),
    items(
        TextElement(
            "UI Elements",
            TextStyle.H1,
        ),
        IconElement(
            symbol = Symbol.House,
            sentiment = Sentiment.Caution,
        ),
        ButtonElement(
            text = "Button",
            onClick = {},
            leadingSymbol = Symbol.LockOpen,
            sentiment = Sentiment.Primary,
            latchOnPress = true,
        ),
        ButtonElement(
            text = "Button",
            onClick = {},
            leadingSymbol = Symbol("test"),
            sentiment = Sentiment.Caution,
            latchOnPress = true,
        ),
        LinkButtonElement(
            text = "Button",
            url = "https://www.example.com",
            leadingSymbol = Symbol.Cloud,
            sentiment = Sentiment.Caution,
        ),
        MenuRowElement(
            text = "Menu Item",
            rightElement = CheckBoxElement(
                checked = true,
                onClick = {},
            ),
            onClick = {},
        ),
        SpinnerElement(
            value = "5",
            onNextValue = {},
            onPreviousValue = {},
            hasPreviousValue = false,
        ),
    ),
    groupingStyle = GroupingStyle.Sections
)
