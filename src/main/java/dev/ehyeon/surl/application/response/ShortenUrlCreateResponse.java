package dev.ehyeon.surl.application.response;

public record ShortenUrlCreateResponse(
        String originalUrl,
        String shortenUrlKey
) {
}
