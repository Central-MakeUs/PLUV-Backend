package play.pluv.oauth.google.dto;

import play.pluv.music.domain.MusicId;

public record YoutubeAddMusicRequest(
    YoutubeAddMusicSnippet snippet
) {

  public static YoutubeAddMusicRequest of(final String playlistId, final MusicId musicId) {
    return new YoutubeAddMusicRequest(YoutubeAddMusicSnippet.of(playlistId, musicId));
  }

  private record YoutubeAddMusicSnippet(
      String playlistId, ResourceId resourceId
  ) {

    private static YoutubeAddMusicSnippet of(final String playlistId, final MusicId musicId) {
      return new YoutubeAddMusicSnippet(playlistId, ResourceId.of(musicId.id()));
    }

    private record ResourceId(
        String videoId, String kind
    ) {

      private static ResourceId of(final String videoId) {
        return new ResourceId(videoId, "youtube#video");
      }
    }
  }
}
