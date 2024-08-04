package play.pluv.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import play.pluv.oauth.application.dto.GoogleAccessToken;
import play.pluv.support.ApiTest;

public class OAuthApiTest extends ApiTest {

  @Test
  void google_AccessToken을_반환해준다() throws Exception {
    final String authCode = "authCode";
    final GoogleAccessToken accessToken = new GoogleAccessToken("accessToken");

    when(oAuthService.getAccessToken(authCode)).thenReturn(accessToken);

    mockMvc.perform(get("/youtube/oauth/token?code=" + authCode))
        .andExpect(status().isOk())
        .andDo(document("get-google-accessToken",
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data.accessToken").type(STRING).description("googleAccessToken")
            )
        ));
  }
}
