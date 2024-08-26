package play.pluv.progress.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.TransferContextFixture.이전한_음악_목록;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;
import static play.pluv.playlist.domain.MusicStreaming.YOUTUBE;

import java.util.List;
import org.junit.jupiter.api.Test;
import play.pluv.history.domain.HistoryMusicId;

class MusicTransferContextTest {

  @Test
  void 피드를_생성할수_있다() {
    final List<TransferredMusicInContext> 이전한_음악_목록 = 이전한_음악_목록();
    final MusicTransferContext context = new MusicTransferContext(
        10L, List.of(), 10, "imageUrl", "이전 중", APPLE, SPOTIFY
    );
    context.addTransferredMusics(이전한_음악_목록);

    final String actual = context.createFeed("creator").getArtistNames();
    final String expected = "한로로,아이유,창모";

    assertThat(actual)
        .isEqualTo(expected);
  }

  @Test
  void 피드를_생성할떄_가수이름이없는경우() {
    final List<TransferredMusicInContext> 이전한_음악_목록 = List.of(
        new TransferredMusicInContext(new HistoryMusicId(YOUTUBE, "c"), "ㅈㅣㅂ", "", "imageUrl",
            null),
        new TransferredMusicInContext(new HistoryMusicId(YOUTUBE, "d"), "금붕어", "", "imageUrl",
            null)
    );

    final MusicTransferContext context = new MusicTransferContext(
        10L, List.of(), 10, "imageUrl", "이전 중", APPLE, SPOTIFY
    );
    context.addTransferredMusics(이전한_음악_목록);

    final String actual = context.createFeed("creator").getArtistNames();

    assertThat(actual)
        .isBlank();
  }
}