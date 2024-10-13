package ink.ui.render.statichtml.renderer

import ink.ui.render.web.elements.HorizontalRule
import kotlinx.html.hr

val HorizontalRuleRenderer = renderer<HorizontalRule> {
    hr()
}
