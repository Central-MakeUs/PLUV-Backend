package play.pluv.oauth.spotify.dto;

import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import play.pluv.music.domain.DestinationMusic;
import play.pluv.music.domain.MusicId;

@JsonNaming(SnakeCaseStrategy.class)
public record SpotifyMusic(
    String name,
    String id,
    Album album,
    ExternalId externalIds,
    List<Artist> artists
) {

  public DestinationMusic toDestinationMusic() {
    return DestinationMusic.builder()
        .musicId(new MusicId(SPOTIFY, id()))
        .imageUrl(getImageUrl())
        .artistNames(getArtistNames())
        .title(name())
        .isrcCode(getIsrcCode())
        .build();
  }

  public String getImageUrl() {
    //TODO: 추후 로직 수정하기 images가 nullable함
    return album.images().get(0).url();
  }

  public List<String> getArtistNames() {
    return artists.stream()
        .map(Artist::name)
        .toList();
  }

  public String getIsrcCode() {
    return externalIds().isrc();
  }

  public record Album(
      List<ThumbNailResponse> images
  ) {

  }

  public record ExternalId(
      String isrc
  ) {

  }

  public record Artist(
      String name
  ) {

  }
}
