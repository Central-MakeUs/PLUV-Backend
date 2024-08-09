package play.pluv.music.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import play.pluv.playlist.domain.PlayListMusic;

class DestinationMusicTest {

  @Nested
  class PlayListMusic과_동일성을_비교한다 {


    @Nested
    class isrc로_동일성을_비교한다 {

      @Test
      void ISRC가_둘다_있는경우() {
        final PlayListMusic sourceMusic = new PlayListMusic("좋은 날", List.of("아이유"), "KRA381001057",
            "imageUrl");
        final DestinationMusic destinationMusic = new DestinationMusic(
            new MusicId(SPOTIFY, "nljewu5"), List.of("IU"), "Good Day", "imageUrl", "KRA381001057"
        );

        final Boolean result = destinationMusic.isSame(sourceMusic);

        assertThat(result).isTrue();
      }

      @Test
      void PlayListMusic의_Isrc가_없는_경우() {
        final PlayListMusic sourceMusic = new PlayListMusic("좋은 날", List.of("아이유"), null,
            "imageUrl");
        final DestinationMusic destinationMusic = new DestinationMusic(
            new MusicId(SPOTIFY, "nljewu5"), List.of("IU"), "Good Day", "imageUrl", "KRA381001057"
        );

        final Boolean result = destinationMusic.isSame(sourceMusic);

        assertThat(result).isFalse();
      }

      @Test
      void DestinationMusic의_Isrc가_없는_경우() {
        final PlayListMusic sourceMusic = new PlayListMusic("좋은 날", List.of("아이유"), "KRA381001057",
            "imageUrl");
        final DestinationMusic destinationMusic = new DestinationMusic(
            new MusicId(SPOTIFY, "nljewu5"), List.of("IU"), "Good Day", "imageUrl", null
        );

        final Boolean result = destinationMusic.isSame(sourceMusic);

        assertThat(result).isFalse();
      }
    }

    @Test
    void Isrc가_없는경우_이름과_가수를_비교한다() {
      final PlayListMusic sourceMusic = new PlayListMusic("SPOT!", List.of("제니", "지코"), null,
          "imageUrl");
      final DestinationMusic destinationMusic = new DestinationMusic(
          new MusicId(SPOTIFY, "dfd124"), List.of("지코", "제니"), "SPOT!", "imageUrl", null
      );

      final Boolean result = destinationMusic.isSame(sourceMusic);

      assertThat(result).isTrue();
    }
  }
}