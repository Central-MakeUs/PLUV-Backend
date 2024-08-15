package play.pluv.oauth.apple.dto;

import java.util.List;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.DestinationMusics;
import play.pluv.music.domain.MusicId;
import play.pluv.playlist.domain.MusicStreaming;

public record AppleMusicSongs(
    List<AppleMusicSong> data
) {

  public DestinationMusics toDestinationMusics() {
    return new DestinationMusics(data.stream()
        .map(AppleMusicSong::toDestinationMusic)
        .toList());
  }

  private record AppleMusicSong(
      AppleMusicSong.AppleMusicAttributes attributes, String id
  ) {

    private DestinationMusic toDestinationMusic() {
      return DestinationMusic.builder()
          .musicId(new MusicId(MusicStreaming.APPLE, id))
          .title(attributes.name)
          .artistNames(List.of(attributes.artistName))
          .isrcCode(attributes.isrc)
          .imageUrl(attributes().artwork.url())
          .build();
    }

    private record AppleMusicAttributes(
        String isrc, Artwork artwork, String name, String artistName
    ) {

    }
  }
}
