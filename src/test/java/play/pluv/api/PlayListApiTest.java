package play.pluv.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static play.pluv.music.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import org.junit.jupiter.api.Test;
import play.pluv.playlist.application.dto.PlayListReadRequest;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.support.ApiTest;

public class PlayListApiTest extends ApiTest {

  @Test
  void 플레이리스트를_읽어서_반환해준다() throws Exception {
    final List<PlayList> playLists =
        List.of(
            new PlayList(
                new PlayListId("id1", SPOTIFY),
                "테스트용 플레이리스트 #1",
                "https://testImage.com/testImage.png", 10
            ),
            new PlayList(
                new PlayListId("id2", SPOTIFY),
                "테스트용 플레이리스트 #2",
                "https://testImage.com/testImage.png", 6
            )
        );

    final PlayListReadRequest request = new PlayListReadRequest("autCode");
    final String requestBody = objectMapper.writeValueAsString(request);

    when(playListService.getPlayLists(any(), any())).thenReturn(playLists);

    mockMvc.perform(post("/{source}/playLists/read", "spotify")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList",
            pathParameters(
                parameterWithName("source").description("플레이리스트 제공자(spotify, apple, youtube)")
            ),
            requestFields(
                fieldWithPath("authCode").type(STRING).description("플레이리스트 제공자의 oauth authCode")
            ),
            responseFields(
                fieldWithPath("[]").type(ARRAY).description("플레이리스트 전체"),
                fieldWithPath("[].thumbNailUrl").type(STRING).description("플레이리스트 섬네일 url"),
                fieldWithPath("[].songCount").type(NUMBER).description("플레이리스트 안에 있는 곡 수"),
                fieldWithPath("[].name").type(STRING).description("플레이리스트 이름"),
                fieldWithPath("[].source").type(STRING).description("플레이리스트 출처")
            )
        ));
  }
}
