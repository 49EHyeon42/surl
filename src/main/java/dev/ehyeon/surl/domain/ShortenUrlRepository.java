package dev.ehyeon.surl.domain;

public interface ShortenUrlRepository {

    void saveShortenUrl(ShortenUrl shortenUrl);

    ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey);
}
