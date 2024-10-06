package ink.ui.render.statichtml

import kotlin.script.experimental.annotations.KotlinScript

@KotlinScript(
    fileExtension = "inkui.part.kts",
    compilationConfiguration = InkUiConfig::class
)
abstract class PartialScript(
    private val parent: InkUiScript,
): InkUiBuilder by parent
