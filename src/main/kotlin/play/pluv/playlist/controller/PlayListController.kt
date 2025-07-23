package play.pluv.playlist.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import play.pluv.base.BaseResponse
import play.pluv.base.BaseResponse.Companion.ok
import play.pluv.playlist.application.PlayListService
import play.pluv.playlist.application.dto.*
import play.pluv.playlist.domain.MusicStreaming

@RestController
@RequestMapping("/playlist")
class PlayListController(
    private val playListService: PlayListService
) {

    @PostMapping("/spotify/read")
    fun readSpotifyPlayLists(
        @RequestBody request: @Valid PlayListReadRequest
    ): BaseResponse<List<PlayListOverViewResponse>> {
        val playLists = playListService.getPlayLists(
            accessToken = request.accessToken,
            source = MusicStreaming.SPOTIFY
        )
        val response = PlayListOverViewResponse.createList(playLists)
        return ok(response)
    }

    @PostMapping("/youtube/read")
    fun readYoutubePlayLists(
        @RequestBody request: @Valid PlayListReadRequest
    ): BaseResponse<List<PlayListOverViewResponse>> {
        val playLists = playListService.getPlayLists(
            accessToken = request.accessToken,
            source = MusicStreaming.YOUTUBE
        )
        val response = PlayListOverViewResponse.createList(playLists)
        return ok(response)
    }

    @PostMapping("/ocr/read")
    fun readOcrPlayLists(
        @RequestBody request: @Valid PlayListOcrRequest
    ): BaseResponse<List<PlayListMusicResponse>> {
        val musics = playListService.getOcrPlayListMusics(
            base64EncodedImages = request.base64EncodedImages
        )
        val responses = PlayListMusicResponse.createList(musics)
        return ok(responses)
    }

    @PostMapping("/spotify/{id}/read")
    fun readSpotifyMusics(
        @RequestBody request: @Valid PlayListReadRequest, @PathVariable id: String
    ): BaseResponse<List<PlayListMusicResponse>> {
        val musics = playListService.getPlayListMusics(
            playListId = id,
            accessToken = request.accessToken,
            source = MusicStreaming.SPOTIFY
        )
        val response = PlayListMusicResponse.createList(musics)
        return ok(response)
    }

    @PostMapping("/youtube/{id}/read")
    fun readYoutubeMusics(
        @RequestBody request: @Valid PlayListReadRequest, @PathVariable id: String
    ): BaseResponse<List<PlayListMusicResponse?>?> {
        val musics = playListService.getPlayListMusics(
            playListId = id,
            accessToken = request.accessToken,
            source = MusicStreaming.YOUTUBE
        )
        val response = PlayListMusicResponse.createList(musics)
        return ok(response)
    }

    @PostMapping("/apple/read")
    fun readApplePlayLists(
        @RequestBody request: @Valid ApplePlayListReadRequest
    ): BaseResponse<List<PlayListOverViewResponse>> {
        val playLists = playListService.getPlayLists(
            accessToken = request.musicUserToken,
            source = MusicStreaming.APPLE
        )
        val response = PlayListOverViewResponse.createList(playLists)
        return ok(response)
    }

    @PostMapping("/apple/{id}/read")
    fun readAppleMusics(
        @RequestBody request: @Valid ApplePlayListReadRequest, @PathVariable id: String
    ): BaseResponse<List<PlayListMusicResponse?>?> {
        val musics = playListService.getPlayListMusics(
            playListId = id,
            accessToken = request.musicUserToken,
            source = MusicStreaming.APPLE
        )
        val response = PlayListMusicResponse.createList(musics)
        return ok(response)
    }
}
