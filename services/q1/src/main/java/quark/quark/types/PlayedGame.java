package quark.quark.types;

public record PlayedGame(
  String name,
  String platform,
  int released,
  double completion,
  int rating
) {
  public static PlayedGame create (PlayedGameDto dto) {
    return new PlayedGame(
      dto.name(),
      dto.platform(),
      dto.released(),
      dto.completion(),
      dto.rating()
    );
  }
}
