package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.StatusIndicatorElement

val StatusRenderer = renderer<StatusIndicatorElement> {
    print("${element.sentiment.formatAnsi(" ■ ")} ${element.text}")
}
