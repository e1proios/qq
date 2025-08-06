package quark.quark.codecs;

import com.mongodb.MongoClientSettings;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.*;
import org.bson.Document;

import quark.quark.types.PlayedGameDto;

public class PlayedGameDtoCodec implements Codec<PlayedGameDto> {

  private final Codec<Document> documentCodec;

  public PlayedGameDtoCodec() {
    this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
  }

  @Override
  public void encode(final BsonWriter writer, PlayedGameDto dto, EncoderContext ctx) {
    Document doc = new Document();

    doc.put("name", dto.name());
    doc.put("platform", dto.platform());
    doc.put("restricted", dto.restricted());
    doc.put("released", dto.released() + "");
    doc.put("finished", dto.finished());
    doc.put("mastered", dto.mastered());
    doc.put("completion", dto.completion());
    doc.put("rating", dto.rating());
    doc.put("notes", dto.notes());

    documentCodec.encode(writer, doc, ctx);
  }

  @Override
  public PlayedGameDto decode(final BsonReader reader, final DecoderContext ctx) {
    Document doc = new DocumentCodec().decode(reader, ctx);

    // mixed field - either double or int
    var completion = doc.get("completion");
    double completionDouble;

    if (completion instanceof Double) {
      completionDouble = (Double) completion;
    } else {
      completionDouble = Double.valueOf((Integer) completion);
    }

    return new PlayedGameDto(
      doc.getObjectId("_id"),
      doc.getString("name"),
      doc.getString("platform"),
      doc.getBoolean("restricted", false),
      Integer.parseInt(doc.getString("released")),
      doc.getString("finished"),
      doc.getString("mastered"),
      completionDouble,
      doc.getInteger("rating"),
      doc.getString("notes")
    );
  }

  @Override
  public Class<PlayedGameDto> getEncoderClass() {
    return PlayedGameDto.class;
  }
}
