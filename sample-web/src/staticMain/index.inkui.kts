import example.*

include("base.inkui.part.kts")
useCodeBlocks = true

addPageHeader(inline(
    TextElement("Ink UI", TextStyle.H1),
    TextElement("A multiplatform UI library for websites and apps."),
    Breadcrumbs,
))

addBody(Examples.Defaults.layout)
