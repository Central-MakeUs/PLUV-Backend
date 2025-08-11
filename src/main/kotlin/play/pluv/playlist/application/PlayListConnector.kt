package play.pluv.playlist.application

import play.pluv.playlist.domain.MusicStreaming
import play.pluv.playlist.domain.PlayList
import play.pluv.playlist.domain.PlayListId
import play.pluv.playlist.domain.PlayListMusic

interface PlayListConnector {
    fun getPlayList(accessToken: String): List<PlayList>

    fun getMusics(playListId: String, accessToken: String): List<PlayListMusic>

    fun createPlayList(accessToken: String, name: String): PlayListId

    fun supportedType(): MusicStreaming
}
