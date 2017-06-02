package bj.vinylbrowser.main.panel

import android.content.Context
import android.widget.Toast
import bj.vinylbrowser.model.release.Video
import com.google.android.youtube.player.YouTubePlayer

/**
 * Created by Josh Laird on 01/06/2017.
 */
class YouTubePlayerPresenter(val context: Context, val controller: YouTubePlayerEpxController, val youTubePlayerHolder: YouTubePlayerHolder) : YouTubePlayer.PlayerStateChangeListener {
    var playerInitialised = false

    fun addVideo(video: Video) {
        val youtubeId = video.uri.split("=".toRegex())[1]
        if (!playerInitialised) {
            playerInitialised = true
            youTubePlayerHolder.initializeYouTubeFragment(this, youtubeId)
        } else {
            if (!youTubePlayerHolder.isPlaying() && controller.videos.size == 0) {
                youTubePlayerHolder.youtubePlayer.loadVideo(youtubeId)
            } else {
                controller.addVideo(video)
                Toast.makeText(context, "Added to up next", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAdStarted() {}

    override fun onLoading() {}

    override fun onVideoStarted() {}

    override fun onLoaded(p0: String?) {}

    override fun onVideoEnded() {
        if (controller.videos.size > 0)
            youTubePlayerHolder.youtubePlayer.loadVideo(controller.removeNextVideo().uri.split("=".toRegex())[1])
    }

    override fun onError(p0: YouTubePlayer.ErrorReason?) {
        Toast.makeText(context, "There was an error playing the video", Toast.LENGTH_SHORT).show()
    }
}