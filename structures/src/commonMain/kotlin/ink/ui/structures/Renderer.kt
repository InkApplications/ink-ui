package ink.ui.structures

import ink.ui.structures.layouts.UiLayout

interface Renderer {
    fun render(uiLayout: UiLayout)
}
