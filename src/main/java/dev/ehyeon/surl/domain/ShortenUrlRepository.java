package dev.ehyeon.surl.domain;

import java.util.Optional;

public interface ShortenUrlRepository {

    void saveShortenUrl(ShortenUrl shortenUrl);

    Optional<ShortenUrl> findShortenUrlByShortenUrlKey(String shortenUrlKey);
}
