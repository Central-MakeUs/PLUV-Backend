package play.pluv.playlist.infra.dto

data class OcrMusicRequest(
    val images: List<Base64EncodedImage>
) {

    data class Base64EncodedImage(val base64EncodedImage: String)

    companion object {
        fun from(base64EncodedImages: List<String>): OcrMusicRequest {
            return OcrMusicRequest(
                images = base64EncodedImages.map { Base64EncodedImage(it) }
            )
        }
    }
}
