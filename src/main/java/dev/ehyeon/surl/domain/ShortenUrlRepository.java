package dev.ehyeon.surl.domain;

import java.util.Optional;

public interface ShortenUrlRepository {

    void save(ShortenUrl shortenUrl);

    Optional<ShortenUrl> findByShortenUrlKey(String shortenUrlKey);
}
