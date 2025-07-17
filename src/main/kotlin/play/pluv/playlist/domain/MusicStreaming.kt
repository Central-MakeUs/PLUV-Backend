package play.pluv.playlist.domain

import play.pluv.playlist.exception.PlayListException
import play.pluv.playlist.exception.PlayListExceptionType

enum class MusicStreaming(name: String) {
    SPOTIFY("spotify"),
    YOUTUBE("youtube"),
    APPLE("apple");

    companion object {
        fun from(name: String): MusicStreaming {
            return entries.find { it.name == name }
                ?: throw PlayListException(PlayListExceptionType.PLAYLIST_PROVIDER_NOT_FOUND)
        }
    }
}