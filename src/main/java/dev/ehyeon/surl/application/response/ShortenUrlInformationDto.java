package dev.ehyeon.surl.application.response;

public record ShortenUrlInformationDto(
        String originalUrl,
        String shortenUrlKey,
        Long redirectCount
) {
}
