package play.pluv.playlist.controller;

import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import play.pluv.playlist.application.dto.PlayListReadRequest;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlayListController {

  private final PlayListService playListService;

  @PostMapping("/spotify/read")
  public ResponseEntity<List<PlayListOverViewResponse>> readSpotifyPlayLists(
      @Valid @RequestBody final PlayListReadRequest request
  ) {
    final var playLists = playListService.getPlayLists(request.accessToken(), SPOTIFY);
    final List<PlayListOverViewResponse> response = PlayListOverViewResponse.createList(playLists);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/youtube/read")
  public ResponseEntity<List<PlayListOverViewResponse>> readYoutubePlayLists(
      @Valid @RequestBody final PlayListReadRequest request
  ) {
    final var playLists = playListService.getPlayLists(request.accessToken(), YOUTUBE);
    final List<PlayListOverViewResponse> response = PlayListOverViewResponse.createList(playLists);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/spotify/{id}/read")
  public BaseResponse<List<PlayListMusicResponse>> readSpotifyMusics(
      @Valid @RequestBody final PlayListReadRequest request, @PathVariable final String id
  ) {
    final var musics = playListService.getPlayListMusics(id, request.accessToken(), SPOTIFY);
    final List<PlayListMusicResponse> response = PlayListMusicResponse.createList(musics);
    return BaseResponse.ok(response);
  }

  @PostMapping("/youtube/{id}/read")
  public BaseResponse<List<PlayListMusicResponse>> readYoutubeMusics(
      @Valid @RequestBody final PlayListReadRequest request, @PathVariable final String id
  ) {
    final var musics = playListService.getPlayListMusics(id, request.accessToken(), YOUTUBE);
    final List<PlayListMusicResponse> response = PlayListMusicResponse.createList(musics);
    return BaseResponse.ok(response);
  }
}
