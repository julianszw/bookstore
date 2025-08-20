package com.jszw.bookstore.external.isbndb;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IsbnDbClient {

    private final @Qualifier("isbndbWebClient") WebClient isbndbWebClient;

    /** Devuelve el mejor par ISBN encontrado para (title, author). */
    public Optional<IsbnPair> pickBestIsbn(String title, String author) {
        return isbndbWebClient.get()
                .uri(uri -> uri.path("/books/{q}")
                        .queryParam("author", author)
                        .queryParam("pageSize", 5)
                        .build(title))
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp -> resp.createException().flatMap(Mono::error))
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    var books = json.path("books");
                    if (!books.isArray() || books.isEmpty()) return null;
                    // Heurística mínima: primer resultado
                    var first = books.get(0);
                    String isbn13 = textOrNull(first, "isbn13");
                    String isbn10 = textOrNull(first, "isbn");
                    if (isbn13 == null && isbn10 == null) return null;
                    return new IsbnPair(isbn13, isbn10);
                })
                .blockOptional();
    }

    private static String textOrNull(JsonNode node, String field) {
        var v = node.path(field);
        return v.isMissingNode() || v.isNull() ? null : v.asText(null);
    }

    /** Par de ISBNs posible (uno o ambos). */
    public record IsbnPair(String isbn13, String isbn10) {}
}
