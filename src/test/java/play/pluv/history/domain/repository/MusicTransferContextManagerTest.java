package play.pluv.history.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.HistoryFixture.이전실패_음악_목록;
import static play.pluv.fixture.HistoryFixture.이전한_음악_목록;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import java.util.List;
import org.junit.jupiter.api.Test;
import play.pluv.history.application.MusicTransferContextManager;
import play.pluv.history.domain.MusicTransferContext;
import play.pluv.history.domain.TransferProgress;
import play.pluv.music.domain.MusicId;

class MusicTransferContextManagerTest {

  private final MusicTransferContextManager manager = new MusicTransferContextManager();
  private final Long memberId = 10L;

  @Test
  void TransferContext를_초기화한다() {
    final var transferFailMusics = 이전실패_음악_목록();

    manager.initContext(memberId, transferFailMusics, 10);

    final MusicTransferContext actual = manager.getContext(memberId);
    final MusicTransferContext expected = new MusicTransferContext(memberId, transferFailMusics,
        10);

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Test
  void 이전_진척도를_반환한다() {
    final var transferFailMusics = 이전실패_음악_목록();
    final var transferredMusics = 이전한_음악_목록();

    manager.putDestMusic(transferredMusics);
    manager.initContext(memberId, transferFailMusics, 10);
    manager.addTransferredMusics(memberId,
        List.of(new MusicId(APPLE, "a"), new MusicId(APPLE, "b"))
    );

    final TransferProgress progress = manager.getCurrentProgress(memberId);
    final TransferProgress expected = new TransferProgress(10, 2);

    assertThat(progress)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
