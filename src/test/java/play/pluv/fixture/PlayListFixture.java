package play.pluv.fixture;

import static play.pluv.playlist.domain.MusicStreaming.SPOTIFY;

import play.pluv.playlist.domain.PlayList;
import play.pluv.playlist.domain.PlayListId;

public class PlayListFixture {

  public static PlayList 스포티파이_플레이리스트_1() {
    return new PlayList(new PlayListId("uniqueId1", SPOTIFY), "플레이리스트 1", "url1", 30);
  }

  public static PlayList 스포티파이_플레이리스트_2() {
    return new PlayList(new PlayListId("uniqueId2", SPOTIFY), "플레이리스트 2", "url2", 20);
  }
}
