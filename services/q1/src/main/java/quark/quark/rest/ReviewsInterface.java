package quark.quark.rest;

import java.util.*;
import org.jboss.resteasy.reactive.RestResponse;

import quark.quark.types.PlayedGame;

public interface ReviewsInterface {

  public RestResponse<List<PlayedGame>> gimmeAll();
  public RestResponse<List<PlayedGame>> gimmeFound(String fieldName, String search);
}
