package dev.ehyeon.surl.domain;

import java.util.concurrent.ThreadLocalRandom;

public class ShortenUrl {

    private static final String BASE_56_CHARACTERS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
    private static final int KEY_LENGTH = 8;

    private String originalUrl;
    private String shortenUrlKey;
    private Long redirectCount;

    public ShortenUrl(String originalUrl, String shortenUrlKey) {
        this.originalUrl = originalUrl;
        this.shortenUrlKey = shortenUrlKey;
        this.redirectCount = 0L;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenUrlKey() {
        return shortenUrlKey;
    }

    public Long getRedirectCount() {
        return redirectCount;
    }

    public void increaseRedirectCount() {
        this.redirectCount++;
    }

    public static String generateShortenUrlKey() {
        return ThreadLocalRandom.current()
                .ints(KEY_LENGTH, 0, BASE_56_CHARACTERS.length())
                .mapToObj(BASE_56_CHARACTERS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
