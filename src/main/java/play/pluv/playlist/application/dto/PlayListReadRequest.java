package play.pluv.playlist.application.dto;

public record PlayListReadRequest(
) {

  public record OAuthAccessToken(
      String accessToken
  ) {
  }

  public record OAuthAuthCode(
      String authCode
  ) {
  }
}
