package quark.quark;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.*;
import org.jboss.resteasy.reactive.RestResponse;

import quark.quark.types.PlayedGame;

@Path("/api/reviews")
public final class ReviewsController {

    private final Set<PlayedGame> loggedGames = Collections.synchronizedSet(new HashSet<>());

    @Inject
    ReviewsService src;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<PlayedGame>> all() {
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
    public RestResponse<List<PlayedGame>> search(
        @QueryParam("field") String field,
        @QueryParam("term") String term
    ) {
        List<PlayedGame> reviews;
        System.out.println("searching for: " + field + " " + term);

        if (field == null || term == null) {
            reviews = this.src.all();
        } else {
            reviews = this.src.search(field, term);
        }
        if (!reviews.isEmpty()) {
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
