package quark.quark.rest;

import java.util.*;
import org.jboss.resteasy.reactive.RestResponse;

public interface ReviewsInterface<PlayedGame> {

  public RestResponse<List<PlayedGame>> gimmeAll();
  public RestResponse<List<PlayedGame>> gimmeFound(String fieldName, String search);
}
