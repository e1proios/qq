package quark.quark;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

import quark.quark.rest.ReviewsInterface;
import quark.quark.types.PlayedGame;

@RegisterRestClient
@Path("/api/reviews")
public class ReviewsController implements ReviewsInterface<PlayedGame> {

  private Set<PlayedGame> loggedGames = Collections.synchronizedSet(new HashSet<>());

  @Inject
  ReviewsService src;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<List<PlayedGame>> gimmeAll() {
    var reviews = this.src.all();

    reviews.sort(
      Comparator.comparing(PlayedGame::rating)
        .reversed()
        .thenComparing(PlayedGame::name)
    );

    return RestResponse.ok(reviews);
  }

  @GET
  @Path("/search/")
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<List<PlayedGame>> gimmeFound(
    @QueryParam("field") String fieldName,
    @QueryParam("search") String search
  ) {
    List<PlayedGame> reviews;

    if (fieldName == null || search == null) {
      reviews = this.src.all();
    } else {
      reviews = this.src.search(fieldName, search);
    }

    if (reviews.size() > 0) {
      return RestResponse.ok(reviews);
    }
    return RestResponse.noContent();
  }

  // POST and DELETE don't connect to the database
  // they still operate with dummy data
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<List<PlayedGame>> take(PlayedGame game) {
    var added = loggedGames.add(game);
    return added ? RestResponse.ok(this.getSortedGameList()) : RestResponse.notModified();
  }

  @DELETE
  @Path("/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public RestResponse<List<PlayedGame>> dontWant(@PathParam("name") String name) {
    var removed = loggedGames.removeIf(g -> g.name().equals(name));
    return removed ? RestResponse.ok(this.getSortedGameList()) : RestResponse.noContent();
  }

  private List<PlayedGame> getSortedGameList() {
    List<PlayedGame> list = new ArrayList<>(loggedGames);

    list.sort(
      Comparator.comparing(PlayedGame::rating)
        .reversed()
        .thenComparing(PlayedGame::name)
    );

    return list;
  }
}
