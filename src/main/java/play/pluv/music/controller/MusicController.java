package play.pluv.music.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/music")
public class MusicController {

  private final MusicService musicService;

  public MusicController(final MusicService musicService) {
    this.musicService = musicService;
  }

  @PostMapping("/{destination}/search")
  public BaseResponse<List<MusicSearchResponse>> searchMusics(
      @RequestBody final MusicSearchRequest musicSearchRequest,
      @PathVariable final String destination
  ) {
    final var responses = musicService.searchMusics(musicSearchRequest);
    return BaseResponse.ok(responses);
  }

  @PostMapping("/{destination}/add")
  public BaseResponse<String> addMusics(
      @RequestBody final MusicAddRequest request, @PathVariable final String destination
  ) {
    musicService.addMusics(request, destination);
    return BaseResponse.created();
  }
}
