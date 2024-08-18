package play.pluv.transfer_context.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.TransferContextFixture.musicTransferContext;
import static play.pluv.fixture.TransferContextFixture.이전실패_음악_목록;
import static play.pluv.fixture.TransferContextFixture.이전한_음악_목록;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.history.domain.History;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.music.domain.MusicId;
import play.pluv.support.ApplicationTest;
import play.pluv.transfer_context.domain.MusicTransferContext;
import play.pluv.transfer_context.domain.TransferProgress;

class MusicTransferContextManagerTest extends ApplicationTest {

  @Autowired
  private MusicTransferContextManager manager;
  @Autowired
  private HistoryRepository historyRepository;

  private final Long memberId = 10L;

  @Test
  void TransferContext를_저장한다() {
    final var transferFailMusics = 이전실패_음악_목록();
    final var transferContext = musicTransferContext(10, memberId, transferFailMusics);

    manager.initContext(transferContext);

    final MusicTransferContext actual = manager.getContext(memberId);

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(transferContext);
  }

  @Test
  void 이전_진척도를_반환한다() {
    final var transferFailMusics = 이전실패_음악_목록();
    final var transferredMusics = 이전한_음악_목록();
    final var transferContext = musicTransferContext(10, memberId, transferFailMusics);

    manager.putDestMusic(transferredMusics);
    manager.initContext(transferContext);
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
