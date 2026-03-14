import example.*

include("base.inkui.part.kts")
useCodeBlocks = true

header {
    h1("Ink UI")
    text("A multiplatform UI library for websites and apps.")
    appendElement(Breadcrumbs)
}

body {
    appendElement(Examples.Defaults.groupedElements)
}
