package play.pluv.history.application;

import static org.assertj.core.api.Assertions.assertThat;
import static play.pluv.api.fixture.MusicFixture.이전되지_못한_음악_목록;
import static play.pluv.fixture.MemberEntityFixture.멤버_홍혁준;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import play.pluv.history.domain.History;
import play.pluv.history.domain.repository.HistoryRepository;
import play.pluv.member.domain.Member;
import play.pluv.member.domain.MemberRepository;
import play.pluv.playlist.domain.PlayListMusic;
import play.pluv.support.ApplicationTest;

public class HistoryUpdaterTest extends ApplicationTest {

  @Autowired
  private HistoryUpdater historyUpdater;
  @Autowired
  private HistoryRepository historyRepository;
  @Autowired
  private MemberRepository memberRepository;

  @Test
  void DestinationMusic으로_히스토리를_생성한다() {
    final List<PlayListMusic> cantTransferredMusics = 이전되지_못한_음악_목록();
    final int totalTransferSongCount = 10;
    final Member member = 멤버_홍혁준(memberRepository);

    final Long historyId = historyUpdater.createHistory(
        "title", "imageUrl", member, cantTransferredMusics, totalTransferSongCount
    );

    final History history = historyRepository.findById(historyId).orElseThrow();
    final History expected = new History("title", "imageUrl", 0, 10, member.getId());

    assertThat(history)
        .usingRecursiveComparison()
        .ignoringFields("id", "createdAt", "updatedAt")
        .isEqualTo(expected);
  }

//  @Test
//  void 히스토리를_생성하는_경우_이전_못한_음악들이_저장된다() {
//    final List<PlayListMusic> cantTransferredMusics = 이전되지_못한_음악_목록();
//    final int canTransferSongCount = 3;
//    final Member member = 멤버_홍혁준(memberRepository);
//
//    final Long historyId = historyUpdater.createHistory(
//        "title", "imageUrl", member, cantTransferredMusics, canTransferSongCount
//    );
//
//    final var actual = failTransferMusicRepository.findByHistoryId(historyId);
//    final var expected = List.of(
//        new FailTransferMusic()
//    );
//
//    assertThat(actual)
//        .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt", "updatedAt")
//        .isEqualTo(expected);
//  }
}
