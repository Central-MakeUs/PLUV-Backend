package play.pluv.playlist.infra.dto

import play.pluv.playlist.domain.PlayListMusic
import java.util.List

data class OcrMusicResponse(
    val artistNames: String, val songTitle: String
) {
    fun toPlayListMusic(): PlayListMusic {
        return PlayListMusic(
            title = songTitle,
            artistNames = List.of(artistNames),
            "",
            null,
        )
    }
}
