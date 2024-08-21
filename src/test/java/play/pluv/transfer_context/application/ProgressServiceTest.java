package play.pluv.transfer_context.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.fixture.MemberEntityFixture.멤버_홍혁준;
import static play.pluv.fixture.TransferContextFixture.musicTransferContext;
import static play.pluv.fixture.TransferContextFixture.이전한_음악_목록;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.repository.MemberRepository;
import play.pluv.playlist.domain.MusicStreaming;
import play.pluv.support.ApplicationTest;
import play.pluv.transfer_context.domain.MusicTransferContext;
import play.pluv.transfer_context.domain.TransferProgress;

class ProgressServiceTest extends ApplicationTest {

  @Autowired
  private ProgressService progressService;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private MusicTransferContextManager musicTransferContextManager;

  @Test
  void 현재진행상황을_반환한다() {
    //given
    final Member member = 멤버_홍혁준(memberRepository);
    final var transferredMusicInContexts = 이전한_음악_목록();
    final var musicIds = List.of(
        new HistoryMusicId(MusicStreaming.APPLE, "a"),
        new HistoryMusicId(MusicStreaming.APPLE, "b")
    );

    final MusicTransferContext context = musicTransferContext(10, member.getId(), List.of());
    musicTransferContextManager.initContext(context);
    musicTransferContextManager.putDestMusic(transferredMusicInContexts);
    musicTransferContextManager.addTransferredMusics(member.getId(), musicIds);

    //when
    final TransferProgress actual = progressService.getTransferProgress(member.getId());
    final TransferProgress expected = new TransferProgress(10, 2);

    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}