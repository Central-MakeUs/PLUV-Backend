package play.pluv.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static play.pluv.music.domain.MusicStreaming.SPOTIFY;
import static play.pluv.music.domain.MusicStreaming.YOUTUBE;

import org.junit.jupiter.api.Test;
import play.pluv.login.application.dto.GoogleLoginRequest;
import play.pluv.login.application.dto.SpotifyLoginRequest;
import play.pluv.support.ApiTest;

public class LoginApiTest extends ApiTest {

  @Test
  void 스포티파이_소셜_로그인을_한다() throws Exception {
    final SpotifyLoginRequest loginRequest = new SpotifyLoginRequest("accessToken");

    final String requestBody = objectMapper.writeValueAsString(loginRequest);

    when(loginService.createToken(SPOTIFY, "accessToken")).thenReturn(2L);

    mockMvc.perform(post("/login/spotify")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("spotify-login",
            requestFields(
                fieldWithPath("accessToken").type(STRING).description("스포티파이의 accessToken")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data.token").type(STRING).description("로그인 할 떄 쓸 accessToken")
            )
        ));
  }

  @Test
  void 구글_소셜_로그인을_한다() throws Exception {
    final GoogleLoginRequest loginRequest = new GoogleLoginRequest("idToken");

    final String requestBody = objectMapper.writeValueAsString(loginRequest);

    when(loginService.createToken(YOUTUBE, "idToken")).thenReturn(2L);

    mockMvc.perform(post("/login/google")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("google-login",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("idToken").type(STRING).description("구글의 accessToken")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data.token").type(STRING).description("로그인 할 떄 쓸 accessToken")
            )
        ));
  }
}
