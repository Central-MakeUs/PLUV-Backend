package play.pluv.music.domain;

import static play.pluv.music.exception.MusicExceptionType.MUSIC_STREAMING_NOT_FOUND;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import lombok.Getter;
import play.pluv.music.exception.MusicException;

@Getter
public enum MusicStreaming {

  SPOTIFY("Spotify"),
  YOUTUBE("Youtube"),
  APPLE("Apple");

  private final String name;

  MusicStreaming(final String name) {
    this.name = name;
  }

  @JsonCreator
  public static MusicStreaming from(final String name) {
    return Arrays.stream(values())
        .filter(streaming -> streaming.getName().equals(name))
        .findAny()
        .orElseThrow(() -> new MusicException(MUSIC_STREAMING_NOT_FOUND));
  }
}
