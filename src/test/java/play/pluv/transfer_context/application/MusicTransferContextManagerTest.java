package play.pluv.transfer_context.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.TransferContextFixture.musicTransferContext;
import static play.pluv.fixture.TransferContextFixture.이전실패_음악_목록;
import static play.pluv.fixture.TransferContextFixture.이전한_음악_목록;
import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.history.domain.History;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.history.domain.TransferFailMusic;
import play.pluv.history.domain.TransferredMusic;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.history.domain.repository.TransferFailMusicRepository;
import play.pluv.history.domain.repository.TransferredMusicRepository;
import play.pluv.support.ApplicationTest;
import play.pluv.transfer_context.domain.MusicTransferContext;
import play.pluv.transfer_context.domain.TransferFailMusicInContext;
import play.pluv.transfer_context.domain.TransferProgress;
import play.pluv.transfer_context.domain.TransferredMusicInContext;

class MusicTransferContextManagerTest extends ApplicationTest {

  @Autowired
  private MusicTransferContextManager manager;
  @Autowired
  private HistoryRepository historyRepository;
  @Autowired
  private TransferFailMusicRepository transferFailMusicRepository;
  @Autowired
  private TransferredMusicRepository transferredMusicRepository;

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
        List.of(new HistoryMusicId(APPLE, "a"), new HistoryMusicId(APPLE, "b"))
    );

    final TransferProgress progress = manager.getCurrentProgress(memberId);
    final TransferProgress expected = new TransferProgress(10, 2);

    assertThat(progress)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  @Nested
  class context를_히스토리로_저장한다 {

    private final List<TransferFailMusicInContext> transferFailMusics = 이전실패_음악_목록();
    private final List<TransferredMusicInContext> transferredMusics = 이전한_음악_목록();

    @BeforeEach
    void setUp() {
      final var transferContext = musicTransferContext(10, memberId, transferFailMusics);

      manager.putDestMusic(transferredMusics);
      manager.initContext(transferContext);
      manager.addTransferredMusics(memberId,
          List.of(
              new HistoryMusicId(APPLE, "a"), new HistoryMusicId(APPLE, "b"),
              new HistoryMusicId(APPLE, "c"), new HistoryMusicId(APPLE, "d")
          )
      );
    }

    @Test
    void 히스토리를_생성한다() {
      final Long historyId = manager.saveTransferHistory(memberId);

      final History history = historyRepository.readById(historyId);
      assertThat(history)
          .isNotNull();
    }

    @Test
    void 이전하지_못한_음악들을_저장한다() {
      final Long historyId = manager.saveTransferHistory(memberId);

      final List<TransferFailMusic> actual = transferFailMusicRepository.findByHistoryId(historyId);
      final List<TransferFailMusic> expected
          = expectedTransferFailMusics(transferFailMusics, historyId);

      assertThat(actual)
          .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt", "updatedAt")
          .containsExactlyElementsOf(expected);
    }

    @Test
    void 이전한_음악들을_저장한다() {
      final Long historyId = manager.saveTransferHistory(memberId);

      final List<TransferredMusic> actual = transferredMusicRepository.findByHistoryId(historyId);
      final List<TransferredMusic> expected
          = expectedTransferredMusics(transferredMusics, historyId);

      assertThat(actual)
          .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt", "updatedAt")
          .containsExactlyElementsOf(expected);
    }

    private List<TransferredMusic> expectedTransferredMusics(
        final List<TransferredMusicInContext> transferredMusics, final Long historyId
    ) {
      return transferredMusics.stream()
          .map(tfm -> tfm.toTransferredMusic(historyId))
          .toList();
    }

    public static List<TransferFailMusic> expectedTransferFailMusics(
        final List<TransferFailMusicInContext> transferFailMusicInContexts, final Long historyId
    ) {
      return transferFailMusicInContexts.stream()
          .map(tfm -> tfm.toTransferFailMusic(historyId))
          .toList();
    }
  }
}
