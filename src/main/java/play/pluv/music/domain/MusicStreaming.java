package play.pluv.music.domain;

import lombok.Getter;

@Getter
public enum MusicStreaming {

  SPOTIFY("Spotify"),
  YOUTUBE("Youtube"),
  APPLE("Apple");

  private final String name;

  MusicStreaming(final String name) {
    this.name = name;
  }
}
