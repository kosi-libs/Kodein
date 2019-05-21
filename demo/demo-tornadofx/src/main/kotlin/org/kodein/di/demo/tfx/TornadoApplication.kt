package org.kodein.di.demo.tfx

import org.kodein.di.*
import org.kodein.di.demo.tfx.person.model.*
import org.kodein.di.demo.tfx.person.view.*
import org.kodein.di.generic.*
import org.kodein.di.tornadofx.*
import tornadofx.*

class TornadoApplication : App(MainView::class), KodeinAware {
    override val kodein: Kodein
        get() = Kodein {
            installTornadoSource()

            constant("test") with "MyApp"
            bind<PersonScope>() with factory { p: Person -> PersonScope(p) }
            bind<EditingState>() with scoped(ComponentScope).singleton { EditingState() }
        }
}

class MainView : View("Kodein TornadoFX") {
    override val root = splitpane() {
        minWidth = 1280.0
        minHeight = 720.0
        vbox {
            add(PersonListView::class)
        }
        vbox {
            add(PersonEditorView::class)
        }
    }
}