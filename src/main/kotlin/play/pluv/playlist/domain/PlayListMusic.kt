package play.pluv.playlist.domain

import java.util.*

//TODO: Optional 제거
data class PlayListMusic(
    val title: String,
    val artistNames: List<String>,
    val imageUrl: String,
    @get:JvmName("getNullableIsrcCode")
    val isrcCode: String?,
) {

    fun getIsrcCode(): Optional<String> = Optional.ofNullable(isrcCode)
}