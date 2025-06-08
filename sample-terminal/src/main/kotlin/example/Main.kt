package example

import ink.ui.render.terminal.TerminalPresenter
import ink.ui.render.terminal.bindAndPresent
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking

fun main()
{
    val presenter = TerminalPresenter()

    runBlocking {
        val job = presenter.bind()
        val examples = Examples(listOf(
            ElementCategory.Navigation to Breadcrumbs,
        ))
        presenter.bindAndPresent(examples.withDefaults().layout)
        job.cancelAndJoin()
    }
}
