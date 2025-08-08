package quark.quark.rest;

import java.util.*;
import org.jboss.resteasy.reactive.RestResponse;

import quark.quark.types.PlayedGame;

public interface ReviewsInterface {

  RestResponse<List<PlayedGame>> all();
  RestResponse<List<PlayedGame>> search(String field, String term);
}
