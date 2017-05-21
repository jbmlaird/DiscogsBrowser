package bj.discogsbrowser.artistreleases

import bj.discogsbrowser.model.artist.Member

/**
 * Created by Josh Laird on 19/05/2017.
 */
object MemberFactory {
    @JvmStatic fun buildMember(number: Int): Member {
        return Member(id = "memberId" + number,
                url = "memberUrl" + number,
                name = "memberName" + number)
    }
}