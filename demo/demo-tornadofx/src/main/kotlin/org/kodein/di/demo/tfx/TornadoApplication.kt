package org.kodein.di.demo.tfx

import tornadofx.*

class TornadoApplication : App(MainView::class)

class MainView : View("Kodein TornadoFX") {
    override val root = splitpane() {
        minWidth = 1280.0
        minHeight = 720.0
        vbox { }
        vbox { }
    }
}