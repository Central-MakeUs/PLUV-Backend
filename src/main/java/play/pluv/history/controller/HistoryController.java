package play.pluv.history.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.history.application.HistoryService;
import play.pluv.history.application.dto.HistoryDetailResponse;
import play.pluv.history.application.dto.HistoryListResponse;
import play.pluv.history.application.dto.HistoryMusicResponse;
import play.pluv.security.JwtMemberId;

@RestController
@RequiredArgsConstructor
public class HistoryController {

  private final HistoryService historyService;

  @GetMapping("/history/me")
  public BaseResponse<List<HistoryListResponse>> getHistories(final JwtMemberId jwtMemberId) {
    final var histories = historyService.findHistories(jwtMemberId.memberId());
    final List<HistoryListResponse> historyListResponses = HistoryListResponse
        .createList(histories);
    return BaseResponse.ok(historyListResponses);
  }

  @GetMapping("/history/{id}")
  public BaseResponse<HistoryDetailResponse> getHistory(
      final JwtMemberId jwtMemberId, @PathVariable final Long id
  ) {
    final var history = historyService.findHistory(id, jwtMemberId.memberId());
    final HistoryDetailResponse response = HistoryDetailResponse.from(history);
    return BaseResponse.ok(response);
  }

  @GetMapping("/history/{id}/music/fail")
  public BaseResponse<List<HistoryMusicResponse>> getTransferFailMusics(
      final JwtMemberId jwtMemberId, @PathVariable final Long id
  ) {
    final var transferFailMusics = historyService.findTransferFailMusics(
        id, jwtMemberId.memberId()
    );
    final List<HistoryMusicResponse> responses = HistoryMusicResponse
        .createListFromTransferFail(transferFailMusics);
    return BaseResponse.ok(responses);
  }

  @GetMapping("/history/{id}/music/success")
  public BaseResponse<List<HistoryMusicResponse>> getTransferredMusics(
      final JwtMemberId jwtMemberId, @PathVariable final Long id
  ) {
    final var transferredMusics = historyService.findTransferredMusics(id, jwtMemberId.memberId());
    final List<HistoryMusicResponse> responses = HistoryMusicResponse
        .createListFromTransferred(transferredMusics);
    return BaseResponse.ok(responses);
  }
}
