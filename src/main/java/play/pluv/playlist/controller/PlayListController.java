package play.pluv.playlist.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.playlist.application.PlayListService;
import play.pluv.playlist.application.dto.PlayListOverViewResponse;

@RestController
public class PlayListController {

  private final PlayListService playListService;

  public PlayListController(final PlayListService playListService) {
    this.playListService = playListService;
  }

  @PostMapping("/{source}/playLists/read")
  public ResponseEntity<List<PlayListOverViewResponse>> readPlayLists(
      @PathVariable final String source, @RequestBody final String authCode
  ) {
    final var playLists = playListService.getPlayLists(authCode, source);
    final List<PlayListOverViewResponse> response = PlayListOverViewResponse.createList(playLists);
    return ResponseEntity.ok(response);
  }
}
