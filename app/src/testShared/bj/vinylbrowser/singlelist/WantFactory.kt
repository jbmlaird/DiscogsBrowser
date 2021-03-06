package bj.vinylbrowser.singlelist

import bj.vinylbrowser.model.common.BasicInformation
import bj.vinylbrowser.model.wantlist.Want

/**
 * Created by Josh Laird on 19/05/2017.
 */
object WantFactory {
    @JvmStatic fun getThreeWants(): List<Want> {
        return (1..3).map { buildWant("wantTitle" + it, "wantSubtitle" + it) }
    }

    private fun buildWant(title: String, subtitle: String): Want {
        return Want(basicInformation = BasicInformation(title = title),
                subtitle = subtitle)
    }
}