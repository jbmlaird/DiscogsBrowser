package bj.vinylbrowser.userdetails

import bj.vinylbrowser.model.user.UserDetails

/**
 * Created by Josh Laird on 19/05/2017.
 */
object UserDetailsFactory {
    @JvmStatic fun buildUserDetails(): UserDetails {
        return UserDetails(sellerRating = 100.0)
    }
}