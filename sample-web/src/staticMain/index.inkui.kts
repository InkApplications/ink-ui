import example.*

include("base.inkui.part.kts")
useCodeBlocks = true

header {
    h1("Ink UI")
    text("A multiplatform UI library for websites and apps.")
    navigation {
        link(text = "Google", url = "https://www.google.com")
        link(text = "GitHub", url = "https://www.github.com")
    }
    appendElement(Breadcrumbs)
}

body {
    appendElement(Examples.Defaults.groupedElements)
}
