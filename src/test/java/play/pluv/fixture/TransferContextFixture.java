package play.pluv.fixture;

import static play.pluv.playlist.domain.MusicStreaming.APPLE;

import java.util.List;
import play.pluv.transfer_context.domain.TransferFailMusicInContext;
import play.pluv.transfer_context.domain.TransferredMusicInContext;
import play.pluv.music.domain.MusicId;

public class TransferContextFixture {

  public static List<TransferFailMusicInContext> 이전실패_음악_목록() {
    return List.of(
        new TransferFailMusicInContext("조회되지 못한 음악", "imageUrl", "조회되지 못한 아티스트"),
        new TransferFailMusicInContext("유사하지만 선택하지 않은 음악", "imageUrl", "아티스트")
    );
  }

  public static List<TransferredMusicInContext> 이전한_음악_목록() {
    return List.of(
        new TransferredMusicInContext(new MusicId(APPLE, "a"), "사랑하게 될거야", "한로로", "imageUrl",
            "isrc"),
        new TransferredMusicInContext(new MusicId(APPLE, "b"), "자처", "한로로", "imageUrl",
            "abcd"),
        new TransferredMusicInContext(new MusicId(APPLE, "c"), "ㅈㅣㅂ", "한로로", "imageUrl",
            null),
        new TransferredMusicInContext(new MusicId(APPLE, "d"), "금붕어", "한로로", "imageUrl",
            null)
    );
  }
}
