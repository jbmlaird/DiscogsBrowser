package bj.discogsbrowser.master

import bj.discogsbrowser.model.version.MasterVersion

/**
 * Created by Josh Laird on 19/05/2017.
 */
object MasterVersionsFactory {
    @JvmStatic fun buildMasterVersions(number: Int): List<MasterVersion> {
        return (1..number).map {
            buildMasterVersion("versionId" + it,
                    "thumb" + it, "versionTitle" + it, "versionFormat" + it)
        }
    }

    private fun buildMasterVersion(id: String, thumb: String, title: String, format: String): MasterVersion {
        return MasterVersion(id = id, thumb = thumb, title = title, format = format)
    }
}