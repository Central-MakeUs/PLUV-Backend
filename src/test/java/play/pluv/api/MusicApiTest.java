package play.pluv.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static play.pluv.api.fixture.MusicFixture.음악_검색_결과;
import static play.pluv.api.fixture.MusicFixture.음악_검색_요청;
import static play.pluv.api.fixture.MusicFixture.음악_추가_요청;

import java.util.List;
import org.junit.jupiter.api.Test;
import play.pluv.music.application.dto.MusicAddRequest;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.support.ApiTest;

public class MusicApiTest extends ApiTest {

  @Test
  void 음악을_읽어서_반환해준다() throws Exception {
    final MusicSearchRequest 검색_요청 = 음악_검색_요청();
    final List<MusicSearchResponse> 검색_결과 = 음악_검색_결과();

    final String requestBody = objectMapper.writeValueAsString(검색_요청);

    when(musicService.searchMusics(any())).thenReturn(검색_결과);

    mockMvc.perform(post("/music/{destination}/search", "spotify")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("search-music",
            pathParameters(
                parameterWithName("destination").description("플레이리스트 제공자(spotify, apple, youtube)")
            ),
            requestFields(
                fieldWithPath("destinationAccessToken").type(STRING)
                    .description("플레이리스트 제공자의 accessToken"),
                fieldWithPath("musics[].title").type(STRING).description("음악 이름"),
                fieldWithPath("musics[].artistName").type(STRING).description("가수 이름들"),
                fieldWithPath("musics[].isrcCode").type(STRING).description("음악의 isrc코드").optional()
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data[].isEqual").type(BOOLEAN).description("동일한 음악인지"),
                fieldWithPath("data[].isFound").type(BOOLEAN).description("찾았는지"),
                fieldWithPath("data[].sourceMusic.title").type(STRING).description("검색하려는 음악의 이름"),
                fieldWithPath("data[].sourceMusic.artistName").type(STRING)
                    .description("검색하려는 음악의 가수 이름"),
                fieldWithPath("data[].destinationMusic").type(OBJECT).description("조회된 음악 정보")
                    .optional(),
                fieldWithPath("data[].destinationMusic.id").type(STRING).description("조회된 음악 id"),
                fieldWithPath("data[].destinationMusic.title").type(STRING)
                    .description("조회된 음악의 이름"),
                fieldWithPath("data[].destinationMusic.artistName").type(STRING)
                    .description("조회된 음악의 가수 이름"),
                fieldWithPath("data[].destinationMusic.imageUrl").type(STRING)
                    .description("조회된 음악의 커버 이미지 url")
            )
        ));
  }

  @Test
  void 음악들을_새로운_플리에_추가한다() throws Exception {
    final MusicAddRequest 음악_추가_요청 = 음악_추가_요청();

    final String requestBody = objectMapper.writeValueAsString(음악_추가_요청);

    mockMvc.perform(post("/music/{destination}/add", "spotify")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("add-music",
            pathParameters(
                parameterWithName("destination").description("플레이리스트 제공자(spotify, apple, youtube)")
            ),
            requestFields(
                fieldWithPath("destinationAccessToken").type(STRING)
                    .description("플레이리스트 제공자의 accessToken"),
                fieldWithPath("musicIds[]").type(ARRAY).description("음악 이름"),
                fieldWithPath("playListId").type(STRING).description("가수 이름들")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data").type(STRING).description("빈 값")
            )
        ));
  }
}
