package play.pluv.api;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static play.pluv.music.domain.MusicStreaming.SPOTIFY;

import org.junit.jupiter.api.Test;
import play.pluv.login.application.dto.LoginRequest;
import play.pluv.support.ApiTest;

public class LoginApiTest extends ApiTest {

  @Test
  void 소셜_로그인을_한다() throws Exception {
    final LoginRequest loginRequest = new LoginRequest("accessToken");

    final String requestBody = objectMapper.writeValueAsString(loginRequest);

    when(loginService.createToken(SPOTIFY, "accessToken")).thenReturn(2L);

    mockMvc.perform(post("/{oauth}/login", "spotify")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("social-login",
            pathParameters(
                parameterWithName("oauth").description("소셜 로그인 제공자(spotify, apple, youtube)")
            ),
            requestFields(
                fieldWithPath("accessToken").type(STRING).description("소셜 로그인 제공자의 accessToken")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data.token").type(STRING).description("로그인 할 떄 쓸 accessToken")
            )
        ));
  }
}
