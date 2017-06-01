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

    fun initializeYouTubeFragment(youtubeId: String) {
        youTubePlayerSupportFragment.initialize("AIzaSyAQ75jaUUbURNpuA9bdyY-pgb72awgw68I",
                object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, wasRestored: Boolean) {
                        if (!wasRestored) {
                            youtubePlayer = youTubePlayer
                            youTubePlayer.setShowFullscreenButton(false)
                            youTubePlayer.loadVideo(youtubeId)

//                            youtubePlayer.setPlayerStateChangeListener(object : YouTubePlayer.PlayerStateChangeListener {
//                                override fun onAdStarted() {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                                override fun onLoading() {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                                override fun onVideoStarted() {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                                override fun onLoaded(p0: String?) {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                                override fun onVideoEnded() {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                                override fun onError(p0: YouTubePlayer.ErrorReason?) {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                            })
//                            youtubePlayer.setPlaylistEventListener(object : YouTubePlayer.PlaylistEventListener {
//                                override fun onPlaylistEnded() {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                                override fun onPrevious() {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                                override fun onNext() {
//                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                                }
//
//                            })
                        }
                    }

                    override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {}
                })
    }

    fun isPlaying(): Boolean {
        return youtubePlayer.isPlaying
    }
}