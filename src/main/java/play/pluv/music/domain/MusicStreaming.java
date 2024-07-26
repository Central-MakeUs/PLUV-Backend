package play.pluv.music.domain;

import static play.pluv.music.exception.MusicExceptionType.MUSIC_STREAMING_NOT_FOUND;

import java.util.Arrays;
import lombok.Getter;
import play.pluv.music.exception.MusicException;

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
        .orElseThrow(() -> new MusicException(MUSIC_STREAMING_NOT_FOUND));
  }
}
