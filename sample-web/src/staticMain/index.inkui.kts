include("base.inkui.part.kts")
useCodeBlocks = true

addPageHeader(inline(
    TextElement("Ink UI", TextStyle.H1),
    TextElement("A multiplatform UI library for websites and apps."),
))
addBody(
    ScrollingListLayout(
        inline(
            TextElement("UI Reference", TextStyle.H1),
            TextElement("Visual references for various types of UI elements"),
            LinkNavigation {
                link(text = "Typography", url = "typography.html")
                link(text = "Elements", url = "elements.html")
            },
        ),
        groupingStyle = GroupingStyle.Sections,
    )
)
