package quark.quark;

import com.mongodb.client.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

import quark.quark.types.PlayedGame;
import quark.quark.types.PlayedGameDto;

@ApplicationScoped
public class ReviewsService {

    @Inject
    MongoClient mongoClient;

    public List<PlayedGame> all() {
        List<PlayedGame> reviews = new ArrayList<>();

        try (var cursor = this.getAllReviews().find().iterator()) {
            this.processResults(cursor, reviews);
        }
        return reviews;
    }

    public List<PlayedGame> search(String field, String term) {
        Document query = new Document(
            field,
            new Document("$regex", term)
                .append("$options", "i")
        );

        List<PlayedGame> reviews = new ArrayList<>();

        try (var cursor = this.getAllReviews().find(query).iterator()) {
            this.processResults(cursor, reviews);
        }
        return reviews;
    }

    private MongoCollection<PlayedGameDto> getAllReviews() {
        return mongoClient
            .getDatabase("game-reviews")
            .getCollection("reviews", PlayedGameDto.class);
    }

    private void processResults(
        MongoCursor<PlayedGameDto> cursor,
        List<PlayedGame> list
    ) {
        while (cursor.hasNext()) {
            var dto = cursor.next();
            list.add(PlayedGame.create(dto));
        }
    }
}
