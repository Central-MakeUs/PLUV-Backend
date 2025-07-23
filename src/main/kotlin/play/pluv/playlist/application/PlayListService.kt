package play.pluv.playlist.application

import org.springframework.stereotype.Service
import play.pluv.playlist.domain.MusicStreaming
import play.pluv.playlist.domain.PlayList
import play.pluv.playlist.domain.PlayListMusic
import play.pluv.playlist.infra.OcrReader

@Service
class PlayListService(
    private val playListConnectorComposite: PlayListConnectorComposite,
    private val ocrReader: OcrReader
) {
    fun getPlayLists(accessToken: String, source: MusicStreaming): List<PlayList> {
        return playListConnectorComposite.getPlayList(
            serverType = source,
            accessToken = accessToken
        )
    }

    fun getPlayListMusics(
        playListId: String, accessToken: String, source: MusicStreaming
    ): List<PlayListMusic> {
        return playListConnectorComposite.getMusics(
            serverType = source,
            accessToken = accessToken,
            playListId = playListId
        )
    }

    fun getOcrPlayListMusics(base64EncodedImages: List<String>): List<PlayListMusic> {
        return ocrReader.ocrImages(base64EncodedImages)
    }
}
