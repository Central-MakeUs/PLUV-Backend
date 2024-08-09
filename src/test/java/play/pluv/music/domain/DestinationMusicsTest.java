package play.pluv.music.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.api.fixture.MusicFixture.한로로_집_아이유_좋은날;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import play.pluv.playlist.domain.PlayListMusic;

class DestinationMusicsTest {

  @Nested
  class 원소에_동일한음악이_포함되어있는지 {

    @Test
    void 포함되어_있는_경우() {
      final DestinationMusics destinationMusics = 한로로_집_아이유_좋은날();
      final PlayListMusic playListMusic
          = new PlayListMusic("good day", List.of("iu"), "KRDDAFA3", "image");

      final Boolean result = destinationMusics.containEqual(playListMusic);

      assertThat(result)
          .isTrue();
    }

    @Test
    void 포함되어_있지_않은_경우() {
      final DestinationMusics destinationMusics = 한로로_집_아이유_좋은날();
      final PlayListMusic playListMusic
          = new PlayListMusic("always awake", List.of("재지팩트"), null, "image");

      final Boolean result = destinationMusics.containEqual(playListMusic);

      assertThat(result)
          .isFalse();
    }
  }
}