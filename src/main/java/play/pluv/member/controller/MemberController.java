package play.pluv.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import play.pluv.base.BaseResponse;
import play.pluv.member.application.MemberService;
import play.pluv.member.application.dto.UpdateNickNameRequest;
import play.pluv.security.JwtMemberId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/nickname")
  public ResponseEntity<BaseResponse<String>> updateNickName(
      @Valid @RequestBody final UpdateNickNameRequest updateNickNameRequest,
      final JwtMemberId jwtMemberId
  ) {
    memberService.updateNickname(jwtMemberId.memberId(), updateNickNameRequest.nickName());
    return ResponseEntity.ok(BaseResponse.ok(""));
  }

  @GetMapping("/nickname")
  public ResponseEntity<BaseResponse<String>> getNickName(final JwtMemberId jwtMemberId) {
    final String nickName = memberService.getNickName(jwtMemberId.memberId());
    return ResponseEntity.ok(BaseResponse.ok(nickName));
  }

  @PostMapping("/unregister")
  public ResponseEntity<BaseResponse<String>> unregister(final JwtMemberId jwtMemberId) {
    memberService.unregister(jwtMemberId.memberId());
    return ResponseEntity.ok(BaseResponse.ok(""));
  }
}
