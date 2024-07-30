package play.pluv.api;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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
import play.pluv.playlist.application.dto.PlayListMusicReadRequest;
import play.pluv.playlist.application.dto.PlayListReadRequest;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;
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

    final PlayListReadRequest request = new PlayListReadRequest("accessToken");
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
                fieldWithPath("accessToken").type(STRING)
                    .description("플레이리스트 제공자의 oauth accessToken")
            ),
            responseFields(
                fieldWithPath("[]").type(ARRAY).description("플레이리스트 전체"),
                fieldWithPath("[].thumbNailUrl").type(STRING).description("플레이리스트 섬네일 url"),
                fieldWithPath("[].songCount").type(NUMBER).description("플레이리스트 안에 있는 곡 수"),
                fieldWithPath("[].name").type(STRING).description("플레이리스트 이름"),
                fieldWithPath("[].source").type(STRING).description("플레이리스트 출처"),
                fieldWithPath("[].id").type(STRING).description("플레이리스트 식별자")
            )
        ));
  }

  @Test
  void 플레이리스트의_음악을_읽어서_반환해준다() throws Exception {
    final List<PlayListMusic> playListMusics =
        List.of(
            new PlayListMusic(
                "좋은 날", List.of("아이유"), "KRA381001057",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1"
            ),
            new PlayListMusic(
                "ㅈㅣㅂ", List.of("hanroro"), "KRA381001234",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a22314"
            )
        );

    final PlayListMusicReadRequest request = new PlayListMusicReadRequest("accessToken");
    final String requestBody = objectMapper.writeValueAsString(request);

    when(playListService.getPlayListMusics(any(), any())).thenReturn(playListMusics);

    mockMvc.perform(post("/{source}/playLists/{id}/read", "spotify", "playListId")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList-musics",
            pathParameters(
                parameterWithName("source").description("플레이리스트 제공자(spotify, apple, youtube)"),
                parameterWithName("id").description("플레이리스트의 식별자")
            ),
            requestFields(
                fieldWithPath("accessToken").type(STRING)
                    .description("플레이리스트 제공자의 oauth accessToken")
            ),
            responseFields(
                fieldWithPath("code").type(NUMBER).description("상태 코드"),
                fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
                fieldWithPath("data[].title").type(STRING).description("음악의 이름"),
                fieldWithPath("data[].artistNames").type(STRING).description("음악의 가수 이름"),
                fieldWithPath("data[].isrcCode").type(STRING).description("조회된 음악의 isrcCode")
                    .optional(),
                fieldWithPath("data[].imageUrl").type(STRING).description("음악의 이미지 url")
            )
        ));
  }
}
