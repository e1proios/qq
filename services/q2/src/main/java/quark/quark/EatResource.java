package quark.quark;

import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.QuarkusApplication;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

import quark.quark.client.ReviewsClient;
import quark.quark.types.PlayedGame;

import java.util.List;

@QuarkusMain
public class EatResource implements QuarkusApplication {

    @Inject
    @RestClient
    ReviewsClient reviewsClient;

    @Override
    public int run(String... args) {
        try (RestResponse<List<PlayedGame>> response = this.reviewsClient.gimmeAll()) {
            var data = response.readEntity(List.class);
            data.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
