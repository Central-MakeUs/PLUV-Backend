package play.pluv.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static play.pluv.fixture.FeedEntityFixture.피드목록;

import org.junit.jupiter.api.Test;
import play.pluv.support.ApiTest;

public class FeedApiTest extends ApiTest {

  @Test
  void 피드_목록을_조회한다() throws Exception {
    when(feedService.findAll()).thenReturn(피드목록());

    mockMvc.perform(get("/feed"))
        .andExpect(status().isOk())
        .andDo(document("feed-list",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data[]").type(ARRAY).description("피드 목록"),
                fieldWithPath("data[].id").type(NUMBER).description("피드 id"),
                fieldWithPath("data[].title").type(STRING).description("피드 이름"),
                fieldWithPath("data[].thumbNailUrl").type(STRING).description("피드 이미지 url"),
                fieldWithPath("data[].creatorName").type(STRING).description("피드 생성한 멤버 이름"),
                fieldWithPath("data[].artistNames").type(STRING).description("가수들")
            )
        ));
  }
}
