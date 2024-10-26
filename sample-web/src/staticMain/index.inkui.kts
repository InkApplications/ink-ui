include("base.inkui.part.kts")
useCodeBlocks = true

addPageHeader(ElementList(
    TextElement("Ink UI", TextStyle.H1),
    TextElement("A multiplatform UI library for websites and apps."),
    groupingStyle = GroupingStyle.Inline,
))
addBody(
    ScrollingListLayout(
        ElementList(
            TextElement("UI Reference", TextStyle.H1),
            TextElement("Visual references for various types of UI elements"),
            LinkNavigation {
                link(text = "Typography", url = "typography.html")
                link(text = "Elements", url = "elements.html")
            },
            groupingStyle = GroupingStyle.Inline,
        ),
        groupingStyle = GroupingStyle.Sections,
    )
)
