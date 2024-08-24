package play.pluv.history.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.history.application.HistoryService;
import play.pluv.history.application.dto.MusicResponse;
import play.pluv.history.domain.TransferredMusic;

@RequestMapping("/feed")
@RequiredArgsConstructor
@RestController
public class FeedHistoryController {

  private final HistoryService historyService;

  @GetMapping("/{id}/music")
  public BaseResponse<List<MusicResponse>> getFeedMusics(@PathVariable final Long id) {
    final List<TransferredMusic> transferredMusics = historyService.findFeedMusics(id);
    final List<MusicResponse> responses = MusicResponse
        .createListFromTransferred(transferredMusics);
    return BaseResponse.ok(responses);
  }
}
