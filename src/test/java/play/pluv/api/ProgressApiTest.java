package play.pluv.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import play.pluv.support.ApiTest;
import play.pluv.transfer_context.domain.TransferProgress;

public class ProgressApiTest extends ApiTest {

  private final String token = "access Token";
  private final Long memberId = 10L;

  @Test
  void 현재_진행상황을_반환해준다() throws Exception {
    final var progress = new TransferProgress(10, 3);

    setAccessToken(token, memberId);
    when(progressService.getTransferProgress(memberId)).thenReturn(progress);

    mockMvc.perform(get("/progress")
            .header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andDo(document("transfer-progress",
            requestHeaders(
                headerWithName(AUTHORIZATION).description("Bearer Token")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data.willTransferMusicCount").type(NUMBER).description("이전하는 전체 음악"),
                fieldWithPath("data.transferredMusicCount").type(NUMBER).description("이전 완료한 음악")
            )
        ));
  }
}
