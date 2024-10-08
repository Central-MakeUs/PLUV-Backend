package play.pluv.api.fixture;

import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import play.pluv.music.application.dto.MusicAddRequest;
import play.pluv.music.application.dto.MusicAddRequest.TransferFailMusicRequest;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchRequest.MusicQuery;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.application.dto.MusicSearchResponse.DestinationMusicResponse;
import play.pluv.music.application.dto.MusicSearchResponse.SourceMusicResponse;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.PlayListMusic;

public class MusicFixture {

  public static List<MusicSearchResponse> 스포티파이_음악_검색_결과() {
    return List.of(
        new MusicSearchResponse(
            true, true, new SourceMusicResponse("좋은 날", "아이유", "imageUrl"),
            List.of(new DestinationMusicResponse("124nkd3fh", "Good Day", "IU",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1")
            )
        ),
        new MusicSearchResponse(false, true, new SourceMusicResponse("ㅈㅣㅂ", "hanro", "imageUrl"),
            List.of(new DestinationMusicResponse("uo890df1", "SPOT!", "제니,지코",
                "https://i.scdn.co/image/ab67616d00001e024930dc9d8cdc7f5f33282538"))),
        new MusicSearchResponse(false, false,
            new SourceMusicResponse("세상에 존재하지 않는 음악", "세상에 존재하지 않는 가수", "imageUrl"), List.of()
        )
    );
  }

  public static List<MusicSearchResponse> 유튜브_음악_검색_결과() {
    return List.of(
        new MusicSearchResponse(
            true, true, new SourceMusicResponse("좋은 날", "아이유", "imageUrl"),
            List.of(new DestinationMusicResponse("124nkd3fh", "Good Day - MV", "",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1"))
        ),
        new MusicSearchResponse(false, true, new SourceMusicResponse("ㅈㅣㅂ", "hanro", "imageUrl"),
            List.of(new DestinationMusicResponse("uo890df1", "SPOT! - MV (제니,지코)", "",
                "https://i.scdn.co/image/ab67616d00001e024930dc9d8cdc7f5f33282538"))),
        new MusicSearchResponse(false, false,
            new SourceMusicResponse("세상에 존재하지 않는 음악", "세상에 존재하지 않는 가수", "imageUrl"), List.of()
        )
    );
  }

  public static List<MusicSearchResponse> 애플_음악_검색_결과() {
    return List.of(
        new MusicSearchResponse(
            true, true, new SourceMusicResponse("좋은 날", "아이유", "imageUrl"),
            List.of(new DestinationMusicResponse("534dfdf.dfe", "좋은 날", "아이유",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1"))
        ),
        new MusicSearchResponse(false, true, new SourceMusicResponse("ㅈㅣㅂ", "hanro", "imageUrl"),
            List.of(new DestinationMusicResponse("uo890df1", "SPOT! - MV (제니,지코)", "",
                "https://i.scdn.co/image/ab67616d00001e024930dc9d8cdc7f5f33282538"))),
        new MusicSearchResponse(false, false,
            new SourceMusicResponse("세상에 존재하지 않는 음악", "세상에 존재하지 않는 가수", "imageUrl"), List.of()
        )
    );
  }

  public static MusicSearchRequest 음악_검색_요청() {
    final List<MusicQuery> musicQueries = List.of(
        new MusicQuery("좋은 날", "아이유", "KRA381001057", "imageUrl"),
        new MusicQuery("SPOT!", "지코,제니", null, "imageUrl"),
        new MusicQuery("세상에 존재하지 않는 음악", "세상에 존재하지 않는 가수", null, "imageUrl")
    );

    return new MusicSearchRequest("dfj51lk5", musicQueries);
  }

  public static MusicAddRequest 음악_추가_요청() {
    return new MusicAddRequest("추가할 playlistName", "accessToken",
        List.of("musicId1", "musicId2", "musicId3", "musicId4"),
        List.of(
            new TransferFailMusicRequest("조회되지 못한 음악", "조회되지 못한 아티스트", "imageUrl"),
            new TransferFailMusicRequest("유사하지만 선택하지 않은 음악", "아티스트", "imageUrl")
        ), "thumbNailUrl", "spotify"
    );
  }

  public static List<DestinationMusic> 이전된_음악_목록() {
    return List.of(
        DestinationMusic.builder()
            .musicId(new MusicId(SPOTIFY, "musicId1")).title("ㅈㅣㅂ").imageUrl("imageUrl")
            .isrcCode(null).artistNames(List.of("한로로")).build(),
        DestinationMusic.builder()
            .musicId(new MusicId(SPOTIFY, "musicId2")).title("좋은 날").imageUrl("imageUrl")
            .isrcCode("KRDDAFA3").artistNames(List.of("아이유")).build(),
        DestinationMusic.builder()
            .musicId(new MusicId(SPOTIFY, "musicId3")).title("Always Awake").imageUrl("imageUrl")
            .isrcCode(null).artistNames(List.of("재지팩트")).build()
    );
  }

  public static DestinationMusics 한로로_집_아이유_좋은날() {
    return new DestinationMusics(List.of(
        DestinationMusic.builder()
            .musicId(new MusicId(SPOTIFY, "musicId1")).title("ㅈㅣㅂ").imageUrl("imageUrl")
            .isrcCode(null).artistNames(List.of("한로로")).build(),
        DestinationMusic.builder()
            .musicId(new MusicId(SPOTIFY, "musicId2")).title("좋은 날").imageUrl("imageUrl")
            .isrcCode("KRDDAFA3").artistNames(List.of("아이유")).build()
    ));
  }

  public static List<PlayListMusic> 이전되지_못한_음악_목록() {
    return List.of(
        PlayListMusic.builder()
            .title("레이디버드").imageUrl("imageUrl").isrcCode(null).artistNames(List.of("잔나비"))
            .build(),
        PlayListMusic.builder()
            .title("하루살이").imageUrl("imageUrl").isrcCode(null).artistNames(List.of("한로로"))
            .build()
    );
  }
}
