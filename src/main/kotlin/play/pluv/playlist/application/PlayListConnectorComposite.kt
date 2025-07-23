package play.pluv.playlist.application

import org.springframework.stereotype.Component
import play.pluv.playlist.domain.MusicStreaming
import play.pluv.playlist.domain.PlayList
import play.pluv.playlist.domain.PlayListMusic
import play.pluv.playlist.exception.PlayListException
import play.pluv.playlist.exception.PlayListExceptionType.PLAYLIST_PROVIDER_NOT_FOUND

@Component
class PlayListConnectorComposite(playListConnectors: Set<PlayListConnector>) {
    private val playListConnectorMap: Map<MusicStreaming, PlayListConnector> =
        playListConnectors.associateBy { it.supportedType() }

    fun getPlayList(serverType: MusicStreaming, accessToken: String): List<PlayList> {
        return getClient(serverType).getPlayList(accessToken)
    }

    fun getMusics(
        serverType: MusicStreaming, accessToken: String, playListId: String
    ): List<PlayListMusic> {
        return getClient(serverType).getMusics(playListId, accessToken)
    }

    private fun getClient(serverType: MusicStreaming): PlayListConnector {
        return playListConnectorMap[serverType] ?: throw PlayListException(
            playListExceptionType = PLAYLIST_PROVIDER_NOT_FOUND
        )
    }
}
