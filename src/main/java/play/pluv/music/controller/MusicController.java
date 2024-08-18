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
import play.pluv.security.JwtMemberId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class MusicController {

  private final MusicService musicService;

  @PostMapping("/spotify/search")
  public BaseResponse<List<MusicSearchResponse>> searchSpotifyMusics(
      final JwtMemberId jwtMemberId, @Valid @RequestBody final MusicSearchRequest musicSearchRequest
  ) {
    final var responses = musicService
        .searchMusics(jwtMemberId.memberId(), SPOTIFY, musicSearchRequest);
    return BaseResponse.ok(responses);
  }

  @PostMapping("/youtube/search")
  public BaseResponse<List<MusicSearchResponse>> searchYoutubeMusics(
      final JwtMemberId jwtMemberId, @Valid @RequestBody final MusicSearchRequest musicSearchRequest
  ) {
    final var responses = musicService
        .searchMusics(jwtMemberId.memberId(), YOUTUBE, musicSearchRequest);
    return BaseResponse.ok(responses);
  }

  @PostMapping("/apple/search")
  public BaseResponse<List<MusicSearchResponse>> searchAppleMusics(
      final JwtMemberId jwtMemberId, @Valid @RequestBody final MusicSearchRequest musicSearchRequest
  ) {
    final var responses = musicService
        .searchMusics(jwtMemberId.memberId(), APPLE, musicSearchRequest);
    return BaseResponse.ok(responses);
  }

  @PostMapping("/spotify/add")
  public BaseResponse<String> transferSpotifyMusics(
      final JwtMemberId jwtMemberId, @Valid @RequestBody final MusicAddRequest request
  ) {
    musicService.transferMusics(jwtMemberId.memberId(), request, SPOTIFY);
    return BaseResponse.created();
  }

  @PostMapping("/youtube/add")
  public BaseResponse<String> transferYoutubeMusics(
      final JwtMemberId jwtMemberId, @Valid @RequestBody final MusicAddRequest request
  ) {
    musicService.transferMusics(jwtMemberId.memberId(), request, YOUTUBE);
    return BaseResponse.created();
  }

  @PostMapping("/apple/add")
  public BaseResponse<String> transferAppleMusics(
      final JwtMemberId jwtMemberId, @Valid @RequestBody final MusicAddRequest request
  ) {
    musicService.transferMusics(jwtMemberId.memberId(), request, APPLE);
    return BaseResponse.created();
  }
}
