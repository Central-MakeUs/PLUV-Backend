package play.pluv.oauth.spotify;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.PlayListFixture.스포티파이_플레이리스트_1;
import static play.pluv.fixture.PlayListFixture.스포티파이_플레이리스트_2;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.playlist.domain.PlayList;
import play.pluv.support.ApplicationTest;

class SpotifyConnectorTest extends ApplicationTest {

  @Autowired
  private SpotifyConnector spotifyConnector;

  @Test
  void 정상적으로_PlayList_객체를_반환한다() {
    final List<PlayList> expected = List.of(스포티파이_플레이리스트_1(), 스포티파이_플레이리스트_2());

    final List<PlayList> actual = spotifyConnector.getPlayList("authCode");

    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);
  }
}