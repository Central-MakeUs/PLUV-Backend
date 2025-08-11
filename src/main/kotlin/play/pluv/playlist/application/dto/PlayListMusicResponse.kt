package play.pluv.playlist.application.dto

import play.pluv.playlist.domain.PlayListMusic

data class PlayListMusicResponse(
    val title: String, val artistNames: String, val isrcCode: String?, val imageUrl: String
) {
    companion object {
        fun createList(musics: List<PlayListMusic>): List<PlayListMusicResponse> {
            return musics.map { from(it) }
        }

        private fun from(playListMusic: PlayListMusic): PlayListMusicResponse {
            return PlayListMusicResponse(
                title = playListMusic.title,
                artistNames = playListMusic.artistNames.joinToString(","),
                isrcCode = playListMusic.isrcCode,
                imageUrl = playListMusic.imageUrl
            )
        }
    }
}
