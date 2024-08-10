package play.pluv.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import play.pluv.member.application.dto.UpdateNickNameRequest;
import play.pluv.support.ApiTest;

public class MemberApiTest extends ApiTest {

  @Test
  void 멤버의_닉네임을_변경한다() throws Exception {
    final String token = "access Token";
    final Long memberId = 10L;
    final String nickName = "변경할 닉네임";

    final UpdateNickNameRequest request = new UpdateNickNameRequest(nickName);
    final String requestBody = objectMapper.writeValueAsString(request);

    setAccessToken(token, memberId);

    mockMvc.perform(post("/member/nickname")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody)
            .header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andDo(document("update-nickname",
            requestFields(
                fieldWithPath("nickName").type(STRING).description("변경할 새로운 닉네임")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data").type(STRING).description("빈 값")
            )
        ));
    verify(memberService).updateNickname(memberId, nickName);
  }

  @Test
  void 멤버가_회원탈퇴한다() throws Exception {
    final String token = "access Token";
    final Long memberId = 10L;

    setAccessToken(token, memberId);

    mockMvc.perform(post("/member/unregister")
            .header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andDo(document("unregister",
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data").type(STRING).description("빈 값")
            )
        ));
//    verify(memberService).unregister(memberId);
  }
}
