package play.pluv.music.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import play.pluv.transfer_context.domain.TransferredMusicInContext;
import play.pluv.playlist.domain.PlayListMusic;

@ToString
@Getter
@Builder
public class DestinationMusic {

  private final MusicId musicId;
  private final List<String> artistNames;
  private final String title;
  private final String imageUrl;
  //nullable한 값
  private final String isrcCode;

  public Boolean isSame(final PlayListMusic playListMusic) {
    if (isrcCode != null) {
      return playListMusic.getIsrcCode()
          .map(isrc -> isrc.equals(isrcCode))
          .orElseGet(() -> compareName(playListMusic));
    }
    return compareName(playListMusic);
  }

  private Boolean compareName(final PlayListMusic playListMusic) {
    final Set<String> set1 = new HashSet<>(artistNames);
    final Set<String> set2 = new HashSet<>(playListMusic.getArtistNames());

    return Objects.equals(playListMusic.getTitle(), title) && set1.equals(set2);
  }

  public TransferredMusicInContext toTransferredMusic() {
    return new TransferredMusicInContext(musicId, title, String.join(",", artistNames), imageUrl,
        isrcCode);
  }
}
