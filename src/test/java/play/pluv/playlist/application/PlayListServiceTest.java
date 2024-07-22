package play.pluv.playlist.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.PlayListFixture.스포티파이_플레이리스트_1;
import static play.pluv.fixture.PlayListFixture.스포티파이_플레이리스트_2;
import static play.pluv.music.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.playlist.domain.PlayList;
import play.pluv.support.ApplicationTest;

class PlayListServiceTest extends ApplicationTest {

  @Autowired
  private PlayListService playListService;

  @Test
  void 플레이리스트를_읽는다() {
    final List<PlayList> expected = List.of(스포티파이_플레이리스트_1(), 스포티파이_플레이리스트_2());

    final List<PlayList> actual = playListService.getPlayLists("accessToken", SPOTIFY.getName());

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);
  }
}