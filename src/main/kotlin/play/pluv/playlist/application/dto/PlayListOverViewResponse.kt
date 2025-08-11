package play.pluv.playlist.application.dto

import play.pluv.playlist.domain.PlayList


data class PlayListOverViewResponse(
    val id: String,
    val thumbNailUrl: String,
    val songCount: Int?,
    val name: String,
    val source: String
) {
    companion object {
        fun createList(playLists: List<PlayList>): List<PlayListOverViewResponse> {
            return playLists.map(::from)
        }

        fun from(playList: PlayList): PlayListOverViewResponse {
            val playListId = playList.playListId
            return PlayListOverViewResponse(
                id = playListId.id,
                thumbNailUrl = playList.thumbNailUrl,
                songCount = playList.songCount,
                name = playList.name,
                source = playListId.musicStreaming.name,
            )
        }
    }
}
