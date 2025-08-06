package quark.quark.codecs;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import quark.quark.types.PlayedGameDto;

public class PlayedGameDtoCodecProvider implements CodecProvider {
  @SuppressWarnings("unchecked")
  @Override
  public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
    if (clazz.equals(PlayedGameDto.class)) {
      return (Codec<T>) new PlayedGameDtoCodec();
    }
    return null;
  }
}
