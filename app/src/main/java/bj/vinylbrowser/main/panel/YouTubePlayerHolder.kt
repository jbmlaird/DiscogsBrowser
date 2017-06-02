package bj.vinylbrowser.main.panel

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

/**
 * Created by Josh Laird on 01/06/2017.
 */
open class YouTubePlayerHolder {
    lateinit var youtubePlayer: YouTubePlayer
    lateinit var youTubePlayerSupportFragment: YouTubePlayerSupportFragment

    fun buildYouTubeFragment(): YouTubePlayerSupportFragment {
        youTubePlayerSupportFragment = YouTubePlayerSupportFragment()
        return youTubePlayerSupportFragment
    }

    fun initializeYouTubeFragment(playerListener: YouTubePlayer.PlayerStateChangeListener, youtubeId: String) {
        youTubePlayerSupportFragment.initialize("AIzaSyAQ75jaUUbURNpuA9bdyY-pgb72awgw68I",
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
                        if (!wasRestored) {
                            youtubePlayer = youTubePlayer
                            youTubePlayer.setShowFullscreenButton(false)
                            youTubePlayer.loadVideo(youtubeId)

                            youtubePlayer.setPlayerStateChangeListener(playerListener)
                        }
                    }

                    override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {}
                })
    }

    fun isPlaying(): Boolean {
        return youtubePlayer.isPlaying
    }
}