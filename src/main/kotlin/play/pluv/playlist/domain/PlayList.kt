package play.pluv.playlist.domain

data class PlayList(
    val playListId: PlayListId,
    val name: String,
    val thumbNailUrl: String,
    val songCount: Int? = null
) {

    //TODO: 호출부가 모두 kotlin으로 바꾸면 없얘기
    constructor(playListId: PlayListId, name:String, thumbNailUrl: String) : this(
        playListId = playListId,
        name = name,
        thumbNailUrl = thumbNailUrl,
        songCount = null
    )
}