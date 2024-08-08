package play.pluv.playlist.domain;

import static play.pluv.playlist.exception.PlayListExceptionType.PLAYLIST_PROVIDER_NOT_FOUND;

import java.util.Arrays;
import lombok.Getter;
import play.pluv.playlist.exception.PlayListException;

@Getter
public enum MusicStreaming {

  SPOTIFY("spotify"),
  YOUTUBE("youtube"),
  APPLE("apple");

  private final String name;

  MusicStreaming(final String name) {
    this.name = name;
  }

  public static MusicStreaming from(final String name) {
    return Arrays.stream(values())
        .filter(streaming -> streaming.getName().equals(name))
        .findAny()
        .orElseThrow(() -> new PlayListException(PLAYLIST_PROVIDER_NOT_FOUND));
  }
}
