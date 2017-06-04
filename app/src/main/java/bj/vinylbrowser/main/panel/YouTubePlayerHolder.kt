package bj.vinylbrowser.main.panel

import android.content.Context
import android.widget.Toast
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

/**
 * Created by Josh Laird on 01/06/2017.
 */
open class YouTubePlayerHolder(val context: Context) {
    var youtubePlayer: YouTubePlayer? = null
    lateinit var youTubePlayerSupportFragment: YouTubePlayerSupportFragment
    lateinit var stateChangeListener: YouTubePlayer.PlayerStateChangeListener

    fun buildYouTubeFragment(): YouTubePlayerSupportFragment {
        youTubePlayerSupportFragment = YouTubePlayerSupportFragment()
        return youTubePlayerSupportFragment
    }

    fun initializeYouTubeFragment(playerListener: YouTubePlayer.PlayerStateChangeListener, youtubeId: String, cue: Boolean = false) {
        youTubePlayerSupportFragment.initialize("AIzaSyAQ75jaUUbURNpuA9bdyY-pgb72awgw68I",
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
                        if (!wasRestored) {
                            youtubePlayer = youTubePlayer
                            stateChangeListener = playerListener
                            youtubePlayer!!.setPlayerStateChangeListener(playerListener)
                            youTubePlayer.setShowFullscreenButton(false)
                            if (cue)
                                youTubePlayer.cueVideo(youtubeId)
                            else
                                youTubePlayer.loadVideo(youtubeId)
                        }
                    }

                    override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
                        Toast.makeText(context, "Unable to initialise YouTube", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    fun isPlaying(): Boolean {
        if (youtubePlayer?.isPlaying == true)
            return true
        return false
    }
}