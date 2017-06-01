package bj.vinylbrowser.main

/**
 * Created by Josh Laird on 29/05/2017.
 */
interface MainContract {
    interface View {
        fun initialiseDraggablePanel()
        fun displayDraggablePanel()
        fun minimiseDraggablePanel()
    }

    interface Presenter {
        fun minimiseDraggablePanel()
    }
}