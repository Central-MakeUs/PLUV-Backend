package play.pluv.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.login.application.dto.JwtMemberId;
import play.pluv.member.application.MemberService;
import play.pluv.member.application.dto.UpdateNickNameRequest;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/member/nickname")
  public ResponseEntity<BaseResponse<String>> updateNickName(
      @Valid @RequestBody final UpdateNickNameRequest updateNickNameRequest,
      final JwtMemberId jwtMemberId
  ) {
    memberService.updateNickname(jwtMemberId.memberId(), updateNickNameRequest.nickName());
    return ResponseEntity.ok(BaseResponse.ok(""));
  }
}
