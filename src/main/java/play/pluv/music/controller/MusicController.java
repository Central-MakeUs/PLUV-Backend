package play.pluv.music.controller;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.music.application.MusicService;
import play.pluv.music.application.dto.MusicAddRequest;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {

  private final MusicService musicService;

  @PostMapping("/spotify/search")
  public BaseResponse<List<MusicSearchResponse>> searchSpotifyMusics(
      @Valid @RequestBody final MusicSearchRequest musicSearchRequest
  ) {
    final var responses = musicService.searchMusics(SPOTIFY, musicSearchRequest);
    return BaseResponse.ok(responses);
  }

  @PostMapping("/youtube/search")
  public BaseResponse<List<MusicSearchResponse>> searchYoutubeMusics(
      @Valid @RequestBody final MusicSearchRequest musicSearchRequest
  ) {
    final var responses = musicService.searchMusics(YOUTUBE, musicSearchRequest);
    return BaseResponse.ok(responses);
  }

  @PostMapping("/apple/search")
  public BaseResponse<List<MusicSearchResponse>> searchAppleMusics(
      @Valid @RequestBody final MusicSearchRequest musicSearchRequest
  ) {
    final var responses = musicService.searchMusics(APPLE, musicSearchRequest);
    return BaseResponse.ok(responses);
  }

  @PostMapping("/spotify/add")
  public BaseResponse<String> transferSpotifyMusics(
      @Valid @RequestBody final MusicAddRequest request
  ) {
    musicService.transferMusics(request, SPOTIFY);
    return BaseResponse.created();
  }

  @PostMapping("/youtube/add")
  public BaseResponse<String> transferYoutubeMusics(
      @Valid @RequestBody final MusicAddRequest request
  ) {
    musicService.transferMusics(request, YOUTUBE);
    return BaseResponse.created();
  }
}
