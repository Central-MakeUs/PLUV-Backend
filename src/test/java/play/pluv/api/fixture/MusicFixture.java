package play.pluv.api.fixture;

import java.util.List;
import play.pluv.music.application.dto.MusicAddRequest;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchRequest.MusicQuery;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.application.dto.MusicSearchResponse.DestinationMusicResponse;
import play.pluv.music.application.dto.MusicSearchResponse.SourceMusicResponse;

public class MusicFixture {

  public static List<MusicSearchResponse> 스포티파이_음악_검색_결과() {
    return List.of(
        new MusicSearchResponse(
            true, true, new SourceMusicResponse("좋은 날", "아이유"),
            new DestinationMusicResponse("124nkd3fh", "Good Day", "IU",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1")
        ),
        new MusicSearchResponse(true, true, new SourceMusicResponse("ㅈㅣㅂ", "hanro"),
            new DestinationMusicResponse("uo890df1", "SPOT!", "제니,지코",
                "https://i.scdn.co/image/ab67616d00001e024930dc9d8cdc7f5f33282538")),
        new MusicSearchResponse(false, false,
            new SourceMusicResponse("세상에 존재하지 않는 음악", "세상에 존재하지 않는 가수"), null
        )
    );
  }

  public static List<MusicSearchResponse> 유튜브_음악_검색_결과() {
    return List.of(
        new MusicSearchResponse(
            true, true, new SourceMusicResponse("좋은 날", "아이유"),
            new DestinationMusicResponse("124nkd3fh", "Good Day - MV", "",
                "https://i.scdn.co/image/ab67616d00001e0215cf3110f19687b1a24943d1")
        ),
        new MusicSearchResponse(true, true, new SourceMusicResponse("ㅈㅣㅂ", "hanro"),
            new DestinationMusicResponse("uo890df1", "SPOT! - MV (제니,지코)", "",
                "https://i.scdn.co/image/ab67616d00001e024930dc9d8cdc7f5f33282538")),
        new MusicSearchResponse(false, false,
            new SourceMusicResponse("세상에 존재하지 않는 음악", "세상에 존재하지 않는 가수"), null
        )
    );
  }

  public static MusicSearchRequest 음악_검색_요청() {
    final List<MusicQuery> musicQueries = List.of(
        new MusicQuery("좋은 날", "아이유", "KRA381001057"),
        new MusicQuery("SPOT!", "지코,제니", null),
        new MusicQuery("세상에 존재하지 않는 음악", "세상에 존재하지 않는 가수", null)
    );

    return new MusicSearchRequest("dfj51lk5", musicQueries);
  }

  public static MusicAddRequest 음악_추가_요청() {
    return new MusicAddRequest("추가할 playlistName", "spotify accessToken",
        List.of("musicId1", "musicId2", "musicId3", "musicId4")
    );
  }
}
