package quark.quark.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

import quark.quark.rest.ReviewsInterface;
import quark.quark.types.PlayedGame;

import java.util.List;

@RegisterRestClient(configKey = "review-service")
@Path("/api/reviews")
public interface ReviewsClient extends ReviewsInterface {

    @GET
    RestResponse<List<PlayedGame>> all();

    @GET
    RestResponse<List<PlayedGame>> search(
        @QueryParam("field") String field,
        @QueryParam("term") String term
    );
}
