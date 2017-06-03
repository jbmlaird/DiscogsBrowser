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
    var isError = false
    var videoLoaded = false

    fun addVideo(video: Video) {
        val youtubeId = video.uri.split("=".toRegex())[1]
        if (!playerInitialised) {
            playerInitialised = true
            youTubePlayerHolder.initializeYouTubeFragment(this, youtubeId)
        } else {
            if (!youTubePlayerHolder.isPlaying() && controller.videos.size == 0 && !isError && !videoLoaded) {
                youTubePlayerHolder.youtubePlayer?.loadVideo(youtubeId)
            } else {
                controller.addVideo(video)
                Toast.makeText(context, "Added to up next", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAdStarted() {}

    override fun onLoading() {
        videoLoaded = true
    }

    override fun onVideoStarted() {
        isError = false
        videoLoaded = true
    }

    override fun onLoaded(p0: String?) {
        videoLoaded = true
    }

    override fun onVideoEnded() {
        videoLoaded = false
        if (controller.videos.size > 0) {
            isError = false
            youTubePlayerHolder.youtubePlayer?.loadVideo(controller.removeNextVideo().uri.split("=".toRegex())[1])
        }
    }

    override fun onError(p0: YouTubePlayer.ErrorReason?) {
        if (p0!!.name != ("UNAUTHORIZED_OVERLAY"))
            Toast.makeText(context, "There was an error playing the video", Toast.LENGTH_SHORT).show()
        isError = true
        videoLoaded = false
    }
}