package dev.ehyeon.surl.application.response;

public record ShortenUrlCreateResponseDto(
        String originalUrl,
        String shortenUrlKey
) {
}
