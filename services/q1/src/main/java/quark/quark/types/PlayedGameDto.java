package quark.quark.types;

import org.bson.types.ObjectId;

public record PlayedGameDto(
  ObjectId id,
  String name,
  String platform,
  boolean restricted,
  int released,
  String finished,
  String mastered,
  double completion,
  int rating,
  String notes
) {
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof PlayedGameDto)) {
      return false;
    }
    PlayedGameDto other = (PlayedGameDto) obj;
      return this.id.equals(other.id);
    }
}
