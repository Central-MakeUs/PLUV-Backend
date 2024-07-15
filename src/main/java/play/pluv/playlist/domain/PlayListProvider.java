package play.pluv.playlist.domain;

import lombok.Getter;

@Getter
public enum PlayListProvider {

  SPOTIFY("Spotify"),
  YOUTUBE("Youtube"),
  APPLE("Apple");

  private final String name;

  PlayListProvider(final String name) {
    this.name = name;
  }
}
