package play.pluv.playlist.domain

@JvmRecord
data class PlayListId(
  @JvmField val id: String,
  @JvmField val musicStreaming: MusicStreaming
)
