package play.pluv.music.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.music.application.dto.MusicSearchRequest;
import play.pluv.music.application.dto.MusicSearchRequest.MusicQuery;
import play.pluv.music.application.dto.MusicSearchResponse;
import play.pluv.music.application.dto.MusicSearchResponse.DestinationMusicResponse;
import play.pluv.music.application.dto.MusicSearchResponse.SourceMusicResponse;
import play.pluv.support.ApplicationTest;

class MusicServiceTest extends ApplicationTest {

  @Autowired
  private MusicService musicService;

  @Test
  void 음악을_조회한다() {
    final MusicSearchRequest request = new MusicSearchRequest("accessToken",
        List.of(new MusicQuery("좋은 날", "아이유", "KRA381001057"))
    );

    final List<MusicSearchResponse> actual = musicService.searchMusics(SPOTIFY, request);
    final List<MusicSearchResponse> expected = List.of(
        new MusicSearchResponse(true, true,
            new SourceMusicResponse("좋은 날", "아이유"),
            new DestinationMusicResponse("goodDayId", "Good Day", "IU", "href")
        )
    );

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);
  }
}
