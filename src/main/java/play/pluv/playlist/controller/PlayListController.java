package play.pluv.playlist.controller;

import static play.pluv.music.domain.MusicStreaming.SPOTIFY;
import static play.pluv.music.domain.MusicStreaming.YOUTUBE;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.playlist.application.PlayListService;
import play.pluv.playlist.application.dto.PlayListMusicResponse;
import play.pluv.playlist.application.dto.PlayListOverViewResponse;
import play.pluv.playlist.application.dto.PlayListReadRequest.OAuthAccessToken;
import play.pluv.playlist.application.dto.PlayListReadRequest.OAuthAuthCode;

@RestController
@RequestMapping("/playlist")
public class PlayListController {

  private final PlayListService playListService;

  public PlayListController(final PlayListService playListService) {
    this.playListService = playListService;
  }

  @PostMapping("/spotify/read")
  public ResponseEntity<List<PlayListOverViewResponse>> readSpotifyPlayLists(
      @RequestBody final OAuthAccessToken request
  ) {
    final var playLists = playListService.getPlayLists(request.accessToken(), SPOTIFY);
    final List<PlayListOverViewResponse> response = PlayListOverViewResponse.createList(playLists);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/youtube/read")
  public ResponseEntity<List<PlayListOverViewResponse>> readYoutubePlayLists(
      @RequestBody final OAuthAuthCode request
  ) {
    final var playLists = playListService.getPlayLists(request.authCode(), YOUTUBE);
    final List<PlayListOverViewResponse> response = PlayListOverViewResponse.createList(playLists);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/spotify/{id}/read")
  public BaseResponse<List<PlayListMusicResponse>> readSpotifyMusics(
      @RequestBody final OAuthAccessToken accessToken, @PathVariable final String id
  ) {
    final var musics = playListService.getPlayListMusics(id, accessToken.accessToken(), SPOTIFY);
    final List<PlayListMusicResponse> response = PlayListMusicResponse.createList(musics);
    return BaseResponse.ok(response);
  }
}
