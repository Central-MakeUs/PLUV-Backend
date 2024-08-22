package play.pluv.api;


import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static play.pluv.fixture.HistoryEntityFixture.히스토리_1;
import static play.pluv.fixture.HistoryEntityFixture.히스토리들;

import org.junit.jupiter.api.Test;
import play.pluv.support.ApiTest;

public class HistoryApiTest extends ApiTest {

  @Test
  void 히스토리_목록을_조회한다() throws Exception {
    final Long memberId = 10L;
    final String token = "access Token";
    final var histories = 히스토리들(memberId);

    setAccessToken(token, memberId);
    when(historyService.findHistories(memberId)).thenReturn(histories);

    mockMvc.perform(get("/history/me")
            .header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andDo(document("history-list",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestHeaders(
                headerWithName(AUTHORIZATION).description("Bearer Token")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data[]").type(ARRAY).description("히스토리 목록"),
                fieldWithPath("data[].id").type(NUMBER).description("히스토리의 Id"),
                fieldWithPath("data[].transferredSongCount").type(NUMBER).description("이전한 곡수"),
                fieldWithPath("data[].title").type(STRING).description("이전한 음악 타이틀"),
                fieldWithPath("data[].imageUrl").type(STRING).description("이미지 url"),
                fieldWithPath("data[].transferredDate").type(STRING).description("이전된 날짜")
            )
        ));
  }

  @Test
  void 히스토리_상세조회한다() throws Exception {
    final Long memberId = 10L;
    final Long historyId = 3L;
    final String token = "access Token";
    final var history = 히스토리_1(memberId);

    setAccessToken(token, memberId);
    when(historyService.findHistory(historyId, memberId)).thenReturn(history);

    mockMvc.perform(get("/history/{id}", historyId)
            .header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andDo(document("history-detail",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                parameterWithName("id").description("히스토리의 식별자")
            ),
            requestHeaders(
                headerWithName(AUTHORIZATION).description("Bearer Token")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data.id").type(NUMBER).description("히스토리의 Id"),
                fieldWithPath("data.totalSongCount").type(NUMBER).description("이전하려 했던 곡수"),
                fieldWithPath("data.title").type(STRING).description("이전한 음악 타이틀"),
                fieldWithPath("data.imageUrl").type(STRING).description("이미지 url"),
                fieldWithPath("data.source").type(STRING).description("이전하려했던 플레이리스트 출처"),
                fieldWithPath("data.destination").type(STRING).description("이전한 플레이리스트 목적지")
            )
        ));
  }
}
