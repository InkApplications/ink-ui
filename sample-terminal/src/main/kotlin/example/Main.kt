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
        presenter.bindAndPresent(Examples.Defaults.layout)
        job.cancelAndJoin()
    }
}
