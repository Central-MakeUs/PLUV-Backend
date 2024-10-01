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
import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;
import play.pluv.playlist.application.dto.ApplePlayListMusicReadRequest;
import play.pluv.playlist.application.dto.ApplePlayListReadRequest;
import play.pluv.playlist.application.dto.PlayListMusicReadRequest;
import play.pluv.playlist.application.dto.PlayListOcrRequest;
import play.pluv.playlist.application.dto.PlayListReadRequest;
import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;
import play.pluv.playlist.domain.PlayListMusic;
import play.pluv.support.ApiTest;

public class PlayListApiTest extends ApiTest {

  private static final FieldDescriptor[] PLAY_LIST_RESPONSE = {
      fieldWithPath("code").type(NUMBER).description("상태 코드"),
      fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
      fieldWithPath("data[]").type(ARRAY).description("플레이리스트 전체"),
      fieldWithPath("data[].thumbNailUrl").type(STRING).description("플레이리스트 섬네일 url"),
      fieldWithPath("data[].songCount").type(NUMBER).description("플레이리스트 안에 있는 곡 수").optional(),
      fieldWithPath("data[].name").type(STRING).description("플레이리스트 이름"),
      fieldWithPath("data[].source").type(STRING).description("플레이리스트 출처"),
      fieldWithPath("data[].id").type(STRING).description("플레이리스트 식별자")};

  private static final FieldDescriptor[] MUSIC_RESPONSE = {
      fieldWithPath("code").type(NUMBER).description("상태 코드"),
      fieldWithPath("msg").type(STRING).description("상태 코드에 해당하는 메시지"),
      fieldWithPath("data[].title").type(STRING).description("음악의 이름"),
      fieldWithPath("data[].artistNames").type(STRING).description("음악의 가수 이름"),
      fieldWithPath("data[].isrcCode").type(STRING).description("조회된 음악의 isrcCode")
          .optional(),
      fieldWithPath("data[].imageUrl").type(STRING).description("음악의 이미지 url")};

  @Test
  void 스포티파이_플레이리스트를_읽어서_반환해준다() throws Exception {
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

    mockMvc.perform(post("/playlist/spotify/read")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList-spotify",
            requestFields(
                fieldWithPath("accessToken").type(STRING).description("스포티파이의 oauth accessToken")
            ),
            responseFields(PLAY_LIST_RESPONSE)
        ));
  }

  @Test
  void 유튜브_플레이리스트를_읽어서_반환해준다() throws Exception {
    final List<PlayList> playLists =
        List.of(
            new PlayList(
                new PlayListId("id1", YOUTUBE),
                "테스트용 플레이리스트 #1",
                "https://testImage.com/testImage.png", null
            ),
            new PlayList(
                new PlayListId("id2", YOUTUBE),
                "테스트용 플레이리스트 #2",
                "https://testImage.com/testImage.png", null
            )
        );

    final PlayListReadRequest request = new PlayListReadRequest("accessToken");
    final String requestBody = objectMapper.writeValueAsString(request);

    when(playListService.getPlayLists(any(), any())).thenReturn(playLists);

    mockMvc.perform(post("/playlist/youtube/read")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList-youtube",
            requestFields(
                fieldWithPath("accessToken").type(STRING).description("google의 accessToken")
            ),
            responseFields(PLAY_LIST_RESPONSE)
        ));
  }

  @Test
  void 애플뮤직_플레이리스트를_읽어서_반환해준다() throws Exception {
    final List<PlayList> playLists =
        List.of(
            new PlayList(
                new PlayListId("id1", APPLE),
                "테스트용 플레이리스트",
                "https://testImage.com/testImage.png", null
            ),
            new PlayList(
                new PlayListId("id2", APPLE),
                "테스트용 플레이리스트",
                "https://testImage.com/testImage.png", null
            )
        );

    final ApplePlayListReadRequest request = new ApplePlayListReadRequest("music-user-token");
    final String requestBody = objectMapper.writeValueAsString(request);

    when(playListService.getPlayLists(any(), any())).thenReturn(playLists);

    mockMvc.perform(post("/playlist/apple/read")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList-apple",
            requestFields(
                fieldWithPath("musicUserToken").type(STRING)
                    .description("apple Music의 music user token")
            ),
            responseFields(PLAY_LIST_RESPONSE)
        ));
  }

  @Test
  void 스포티파이_플레이리스트의_음악을_읽어서_반환해준다() throws Exception {
    final List<PlayListMusic> playListMusics =
        List.of(
            new PlayListMusic(
                "좋은 날", List.of(), "KR1215932",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1"
            ),
            new PlayListMusic(
                "ㅈㅣㅂ", List.of(), "KR12163",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a22314"
            )
        );

    final PlayListMusicReadRequest request = new PlayListMusicReadRequest("accessToken");
    final String requestBody = objectMapper.writeValueAsString(request);

    when(playListService.getPlayListMusics(any(), any(), any())).thenReturn(playListMusics);

    mockMvc.perform(post("/playlist/spotify/{id}/read", "playListId")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList-spotify-musics",
            pathParameters(
                parameterWithName("id").description("플레이리스트의 식별자")
            ),
            requestFields(
                fieldWithPath("accessToken").type(STRING)
                    .description("플레이리스트 제공자의 oauth accessToken")
            ),
            responseFields(
                MUSIC_RESPONSE
            )
        ));
  }

  @Test
  void 유튜브_플레이리스트의_음악을_읽어서_반환해준다() throws Exception {
    final List<PlayListMusic> playListMusics =
        List.of(
            new PlayListMusic(
                "좋은 날", List.of("아이유"), null,
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1"
            ),
            new PlayListMusic(
                "ㅈㅣㅂ", List.of("hanroro"), null,
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a22314"
            )
        );

    final PlayListMusicReadRequest request = new PlayListMusicReadRequest("accessToken");
    final String requestBody = objectMapper.writeValueAsString(request);

    when(playListService.getPlayListMusics(any(), any(), any())).thenReturn(playListMusics);

    mockMvc.perform(post("/playlist/youtube/{id}/read", "playListId")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList-youtube-musics",
            pathParameters(
                parameterWithName("id").description("플레이리스트의 식별자")
            ),
            requestFields(
                fieldWithPath("accessToken").type(STRING)
                    .description("플레이리스트 제공자의 oauth accessToken")
            ),
            responseFields(
                MUSIC_RESPONSE
            )
        ));
  }

  @Test
  void 애플_플레이리스트의_음악을_읽어서_반환해준다() throws Exception {
    final List<PlayListMusic> playListMusics =
        List.of(
            new PlayListMusic(
                "Supernatural", List.of("뉴진스"), null,
                "https://is1-ssl.mzstatic.com/image/thumb/Music211/v4/ab/ce/d6/abced6f6-2b90-c230-eb4b-e146734a3a22/196922907821_Cover.jpg/{w}x{h}bb.jpg"
            ),
            new PlayListMusic(
                "Flex", List.of("기리보이, 키드밀리, NO:EL & 스윙스"), null,
                "https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/96/7a/8a/967a8a1e-8630-5b3e-13be-aca62500d91b/cover-_Kid_Milli_NOEL_DS.jpg/{w}x{h}bb.jpg"
            )
        );

    final ApplePlayListMusicReadRequest request = new ApplePlayListMusicReadRequest("accessToken");
    final String requestBody = objectMapper.writeValueAsString(request);

    when(playListService.getPlayListMusics(any(), any(), any())).thenReturn(playListMusics);

    mockMvc.perform(post("/playlist/apple/{id}/read", "playListId")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList-apple-musics",
            pathParameters(
                parameterWithName("id").description("플레이리스트의 식별자")
            ),
            requestFields(
                fieldWithPath("musicUserToken").type(STRING)
                    .description("apple Music의 music user token")
            ),
            responseFields(
                MUSIC_RESPONSE
            )
        ));
  }

  @Test
  void ocr로_음악들을_읽는다() throws Exception {
    final List<PlayListMusic> playListMusics = List.of(
        new PlayListMusic(
            "좋은 날", List.of("아이유"), null,
            "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1"
        ),
        new PlayListMusic(
            "ㅈㅣㅂ", List.of("한로로"), null,
            "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a22314"
        )
    );

    final PlayListOcrRequest request = new PlayListOcrRequest(List.of(
        "base64EncodedImages1", "base64EncodedImages2"
    ));
    final String requestBody = objectMapper.writeValueAsString(request);

    when(playListService.getOcrPlayListMusics(any())).thenReturn(playListMusics);

    mockMvc.perform(post("/playlist/ocr/read")
            .contentType(APPLICATION_JSON_VALUE)
            .content(requestBody))
        .andExpect(status().isOk())
        .andDo(document("read-playList-ocr-musics",
            requestFields(
                fieldWithPath("base64EncodedImages.[]").type(ARRAY).description("base64 인코딩된 이미지")
            ),
            responseFields(
                MUSIC_RESPONSE
            )
        ));
  }
}
