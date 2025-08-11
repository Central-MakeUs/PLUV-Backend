package play.pluv.login.application.dto

import play.pluv.playlist.domain.MusicStreaming

data class LoginTypeResponse(
    val type: String
) {
    companion object {
        fun from(type: MusicStreaming): LoginTypeResponse {
            if (type == MusicStreaming.YOUTUBE) {
                return LoginTypeResponse("google")
            }
            return LoginTypeResponse(type.name)
        }

        fun createList(types: List<MusicStreaming>): List<LoginTypeResponse> {
            return types
                .map { type: MusicStreaming -> from(type) }
                .toList()
        }
    }
}
