package play.pluv.progress.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.security.JwtMemberId;
import play.pluv.progress.application.ProgressService;
import play.pluv.progress.application.dto.ProgressResponse;

@RestController
@RequiredArgsConstructor
public class ProgressController {

  private final ProgressService progressService;

  @GetMapping("/progress")
  public BaseResponse<ProgressResponse> getTransferProgress(final JwtMemberId jwtMemberId) {
    final var progress = progressService.getTransferProgress(jwtMemberId.memberId());
    return BaseResponse.ok(ProgressResponse.from(progress));
  }
}
