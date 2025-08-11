package play.pluv.playlist.infra

import org.springframework.stereotype.Component
import play.pluv.playlist.domain.PlayListMusic
import play.pluv.playlist.infra.dto.OcrMusicRequest

@Component
class OcrReader(
    private val ocrApiClient: OcrApiClient
) {

    fun ocrImages(base64EncodedImages: List<String>): List<PlayListMusic> {
        val request = OcrMusicRequest.from(base64EncodedImages = base64EncodedImages)

        return ocrApiClient.ocrPlayListImage(request)
            .map { it.toPlayListMusic() }
    }
}
