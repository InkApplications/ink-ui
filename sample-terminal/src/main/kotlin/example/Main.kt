package example

import ink.ui.render.terminal.TerminalPresenter
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking

fun main()
{
    val presenter = TerminalPresenter()

    runBlocking {
        val job = presenter.bind()
        val examples = Examples.Defaults
        presenter.presentLayout(examples.layout)
        job.cancelAndJoin()
    }
}
