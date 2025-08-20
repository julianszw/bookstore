package com.jszw.bookstore.external.googlebooks;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoogleBooksClient {

    private final WebClient.Builder builder;

    @Value("${external.google.base-url:https://www.googleapis.com}")
    private String baseUrl;

    @Value("${external.google.api-key:}")
    private String apiKey;

    public Optional<IsbnPair> pickBestIsbn(String title, String author) {
        WebClient client = builder.baseUrl(baseUrl).build();

        JsonNode json = client.get()
                .uri(uri -> {
                    var b = uri.path("/books/v1/volumes")
                            .queryParam("q", String.format("intitle:\"%s\"+inauthor:\"%s\"", title, author))
                            .queryParam("printType", "books")
                            .queryParam("maxResults", 5)
                            .queryParam("fields", "items(id,volumeInfo/industryIdentifiers)");
                    if (apiKey != null && !apiKey.isBlank()) {
                        b.queryParam("key", apiKey);
                    }
                    return b.build();
                })
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp -> resp.createException().flatMap(Mono::error))
                .bodyToMono(JsonNode.class)
                .block();

        if (json == null) return Optional.empty();
        var items = json.path("items");
        if (!items.isArray() || items.isEmpty()) return Optional.empty();

        for (var item : items) {
            var ids = item.path("volumeInfo").path("industryIdentifiers");
            if (!ids.isArray()) continue;
            String isbn13 = null, isbn10 = null;
            for (var id : ids) {
                var type = id.path("type").asText("");
                var val  = id.path("identifier").asText("");
                if ("ISBN_13".equalsIgnoreCase(type) && !val.isBlank()) isbn13 = val;
                if ("ISBN_10".equalsIgnoreCase(type) && !val.isBlank()) isbn10 = val;
            }
            if (isbn13 != null || isbn10 != null) {
                return Optional.of(new IsbnPair(isbn13, isbn10));
            }
        }
        return Optional.empty();
    }

    public record IsbnPair(String isbn13, String isbn10) {}
}
