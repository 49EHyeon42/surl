package dev.ehyeon.surl.application.response;

public record ShortenUrlInformationResponse(
        String originalUrl,
        String shortenUrlKey,
        Long redirectCount
) {
}
