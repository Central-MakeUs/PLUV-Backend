package play.pluv.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static play.pluv.fixture.FeedEntityFixture.피드목록;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import play.pluv.support.ApiTest;

public class FeedApiTest extends ApiTest {

  private static final Long memberId = 10L;
  private static final String token = "access Token";
  private static final ResponseFieldsSnippet FEED_LIST_SNIPPET = responseFields(
      fieldWithPath("code").type(NUMBER).description("상태 코드"),
      fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
      fieldWithPath("data[]").type(ARRAY).description("피드 목록"),
      fieldWithPath("data[].id").type(NUMBER).description("피드 id"),
      fieldWithPath("data[].title").type(STRING).description("피드 이름"),
      fieldWithPath("data[].thumbNailUrl").type(STRING).description("피드 이미지 url"),
      fieldWithPath("data[].creatorName").type(STRING).description("피드 생성한 멤버 이름"),
      fieldWithPath("data[].artistNames").type(STRING).description("가수들")
  );

  @Test
  void 피드_목록을_조회한다() throws Exception {
    when(feedService.findAll()).thenReturn(피드목록());

    mockMvc.perform(get("/feed"))
        .andExpect(status().isOk())
        .andDo(document("feed-list",
            FEED_LIST_SNIPPET
        ));
  }

  @Test
  void 피드를_북마크한다() throws Exception {
    final Long feedId = 3L;

    setAccessToken(token, memberId);

    mockMvc.perform(post("/feed/{id}/save", feedId)
            .header(AUTHORIZATION, "Bearer " + token))
        .andExpect(status().isOk())
        .andDo(document("bookmark-feed",
            pathParameters(
                parameterWithName("id").description("피드의 식별자")
            ),
            requestHeaders(
                headerWithName(AUTHORIZATION).description("pluv에서 발급한 accessToken")
            )
        ));

    verify(feedService).bookmarkFeed(memberId, feedId);
  }
}
