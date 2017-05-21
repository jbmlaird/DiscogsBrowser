package bj.discogsbrowser.model.common

/**
 * Created by Josh Laird on 19/05/2017.
 */
interface RecyclerViewModel {
    fun getTitle(): String
    fun getSubtitle(): String
    fun getThumb(): String
    fun getType(): String
    fun getId(): String
}