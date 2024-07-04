package dev.ehyeon.surl.infrastructure;

import dev.ehyeon.surl.domain.ShortenUrl;
import dev.ehyeon.surl.domain.ShortenUrlRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ShortenUrlMemoryRepository implements ShortenUrlRepository {

    private final Map<String, ShortenUrl> shortenUrls = new ConcurrentHashMap<>();

    @Override
    public void save(ShortenUrl shortenUrl) {
        shortenUrls.put(shortenUrl.getShortenUrlKey(), shortenUrl);
    }

    @Override
    public Optional<ShortenUrl> findByShortenUrlKey(String shortenUrlKey) {
        return Optional.ofNullable(shortenUrls.get(shortenUrlKey));
    }
}
