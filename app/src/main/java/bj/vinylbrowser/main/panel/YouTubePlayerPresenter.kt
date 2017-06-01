package bj.vinylbrowser.main.panel

import bj.vinylbrowser.model.release.Video

/**
 * Created by Josh Laird on 01/06/2017.
 */
class YouTubePlayerPresenter(val controller: YouTubePlayerEpxController, val youTubePlayerHolder: YouTubePlayerHolder) {
    var playerInitialised = false

    fun addVideo(video: Video) {
        val youtubeId = video.uri.split("=".toRegex())[1]
        if (!playerInitialised) {
            playerInitialised = true
            youTubePlayerHolder.initializeYouTubeFragment(youtubeId)
        } else {
            if (!youTubePlayerHolder.isPlaying() && controller.videos.size == 0) {
                youTubePlayerHolder.youtubePlayer.loadVideo(youtubeId)
            } else {
                controller.addVideo(video)
            }
        }
    }
}