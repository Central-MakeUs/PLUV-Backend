package play.pluv.oauth.apple.dto;

public record AppleCreatePlayListRequest(
    PlayListAttributes attributes
) {

  public static AppleCreatePlayListRequest from(final String name) {
    return new AppleCreatePlayListRequest(new PlayListAttributes(name));
  }

  private record PlayListAttributes(
      String name
  ) {

  }
}
