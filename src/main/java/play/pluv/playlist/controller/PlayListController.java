package play.pluv.playlist.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.playlist.application.PlayListService;
import play.pluv.playlist.application.dto.PlayListMusicResponse;
import play.pluv.playlist.application.dto.PlayListOverViewResponse;
import play.pluv.playlist.application.dto.PlayListReadRequest;

@RestController
public class PlayListController {

  private final PlayListService playListService;

  public PlayListController(final PlayListService playListService) {
    this.playListService = playListService;
  }

  @PostMapping("/{source}/playLists/read")
  public ResponseEntity<List<PlayListOverViewResponse>> readPlayLists(
      @PathVariable final String source, @RequestBody final PlayListReadRequest request
  ) {
    final var playLists = playListService.getPlayLists(request.accessToken(), source);
    final List<PlayListOverViewResponse> response = PlayListOverViewResponse.createList(playLists);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{source}/playLists/{id}/read")
  public BaseResponse<List<PlayListMusicResponse>> readPlayLists(
      @PathVariable final String source, @RequestBody final PlayListReadRequest request,
      @PathVariable final String id
  ) {
    final var musics = playListService.getPlayListMusics(request.accessToken(), id);
    final List<PlayListMusicResponse> response = PlayListMusicResponse.createList(musics);
    return BaseResponse.ok(response);
  }
}
