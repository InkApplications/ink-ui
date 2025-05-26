package example

import ink.ui.structures.*
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.ScrollingListLayout

val SampleScreen = ScrollingListLayout(
    items = listOf(
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
        ProgressElement.Determinate(
            progress = 0.5f,
            caption = "Progress",
        ),
        ProgressElement.Indeterminate(
            caption = "Indeterminate Progress",
        ),
        ThrobberElement(
            caption = "Throbber",
        ),
        IconElement(
            symbol = Symbol.House,
            sentiment = Sentiment.Caution,
        ),
        ButtonElement(
            text = "Button",
            onClick = {},
            leadingSymbol = Symbol.Sunshine,
            sentiment = Sentiment.Primary,
            latchOnPress = true,
        ),
        MenuRowElement(
            text = "Menu Item",
            rightElement = CheckBoxElement(
                checked = true,
                onClick = {},
            ),
            onClick = {},
        ),
        ElementList(
            items = listOf(
                WeatherElement(
                    temperature = "72",
                    condition = WeatherElement.Condition.Clear,
                ),
                WeatherElement(
                    title = "Sun",
                    temperature = "72",
                    secondaryText = "60",
                    condition = WeatherElement.Condition.Snowy,
                ),
                WeatherElement(
                    temperature = "72",
                    condition = WeatherElement.Condition.Cloudy,
                ),
                WeatherElement(
                    title = "Sun",
                    temperature = "72",
                    secondaryText = "60",
                    condition = WeatherElement.Condition.Rainy,
                ),
            ),
            positioning = Positioning.Center,
            orientation = Orientation.Horizontal,
        ),
        ElementList(
            items = listOf(
                WeatherElement(
                    temperature = "72",
                    condition = WeatherElement.Condition.Clear,
                    compact = true,
                ),
                WeatherElement(
                    title = "Sun",
                    temperature = "72",
                    secondaryText = "60",
                    condition = WeatherElement.Condition.Snowy,
                    compact = true,
                ),
                WeatherElement(
                    temperature = "72",
                    condition = WeatherElement.Condition.Cloudy,
                    compact = true,
                ),
                WeatherElement(
                    title = "Sun",
                    temperature = "72",
                    secondaryText = "60",
                    condition = WeatherElement.Condition.Rainy,
                    compact = true,
                ),
            ),
            positioning = Positioning.Start,
            orientation = Orientation.Horizontal,
        ),
        SpinnerElement(
            value = "5",
            onNextValue = {},
            onPreviousValue = {},
        ),
    )
)
