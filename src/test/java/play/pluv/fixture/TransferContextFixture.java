package play.pluv.fixture;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;
import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import java.util.List;
import play.pluv.history.domain.HistoryMusicId;
import play.pluv.transfer_context.domain.MusicTransferContext;
import play.pluv.transfer_context.domain.TransferFailMusicInContext;
import play.pluv.transfer_context.domain.TransferredMusicInContext;

public class TransferContextFixture {

  public static List<TransferFailMusicInContext> 이전실패_음악_목록() {
    return List.of(
        new TransferFailMusicInContext("조회되지 못한 음악", "imageUrl", "조회되지 못한 아티스트"),
        new TransferFailMusicInContext("유사하지만 선택하지 않은 음악", "imageUrl", "아티스트")
    );
  }

  public static MusicTransferContext musicTransferContext(
      final Integer willTransferMusicCount, final Long memberId,
      final List<TransferFailMusicInContext> transferFailMusics
  ) {
    return MusicTransferContext.builder()
        .transferFailMusics(transferFailMusics)
        .title("이전한 플레이리스트")
        .willTransferMusicCount(willTransferMusicCount)
        .destination(SPOTIFY)
        .source(SPOTIFY)
        .memberId(memberId)
        .thumbNailUrl("thumbNailUrl")
        .build();
  }

  public static List<TransferredMusicInContext> 이전한_음악_목록() {
    return List.of(
        new TransferredMusicInContext(new HistoryMusicId(APPLE, "a"), "사랑하게 될거야", "한로로", "imageUrl",
            "isrc"),
        new TransferredMusicInContext(new HistoryMusicId(APPLE, "b"), "자처", "한로로", "imageUrl",
            "abcd"),
        new TransferredMusicInContext(new HistoryMusicId(APPLE, "c"), "ㅈㅣㅂ", "한로로", "imageUrl",
            null),
        new TransferredMusicInContext(new HistoryMusicId(APPLE, "d"), "금붕어", "한로로", "imageUrl",
            null)
    );
  }
}
