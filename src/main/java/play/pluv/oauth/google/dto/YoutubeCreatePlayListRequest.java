package play.pluv.oauth.google.dto;

public record YoutubeCreatePlayListRequest(
    YoutubePlayListDetail snippet
) {

  public static YoutubeCreatePlayListRequest from(final String title) {
    return new YoutubeCreatePlayListRequest(YoutubePlayListDetail.from(title));
  }

  private record YoutubePlayListDetail(
      String title, String defaultLanguage
  ) {

    private static YoutubePlayListDetail from(final String title) {
      return new YoutubePlayListDetail(title, "ko");
    }
  }
}
