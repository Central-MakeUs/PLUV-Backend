package play.pluv.oauth.apple.dto;

import java.util.List;
import play.pluv.music.domain.MusicId;

public record AppleAddMusicRequest(
    List<AppleMusicSongData> data
) {

  public static AppleAddMusicRequest of(final List<MusicId> musicIds) {
    final List<AppleMusicSongData> data = musicIds.stream()
        .map(MusicId::id)
        .map(AppleMusicSongData::fromId)
        .toList();
    return new AppleAddMusicRequest(data);
  }

  private record AppleMusicSongData(
      String id, String type
  ) {

    private static final String TYPE = "songs";

    private static AppleMusicSongData fromId(final String id) {
      return new AppleMusicSongData(id, TYPE);
    }
  }
}
