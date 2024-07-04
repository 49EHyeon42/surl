package dev.ehyeon.surl.application;

import dev.ehyeon.surl.domain.exception.LackOfShortenUrlKeyException;
import dev.ehyeon.surl.domain.exception.NotFoundShortenUrlException;
import dev.ehyeon.surl.domain.ShortenUrl;
import dev.ehyeon.surl.domain.ShortenUrlRepository;
import dev.ehyeon.surl.application.request.ShortenUrlCreateRequest;
import dev.ehyeon.surl.application.response.ShortenUrlCreateResponse;
import dev.ehyeon.surl.application.response.ShortenUrlInformationResponse;
import org.springframework.stereotype.Service;

@Service
public class SimpleShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;

    public SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public ShortenUrlCreateResponse generateShortenUrl(ShortenUrlCreateRequest shortenUrlCreateRequest) {
        String originalUrl = shortenUrlCreateRequest.originalUrl();
        String shortenUrlKey = getUniqueShortenUrlKey();

        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);

        shortenUrlRepository.save(shortenUrl);

        return new ShortenUrlCreateResponse(shortenUrl.getOriginalUrl(), shortenUrl.getShortenUrlKey());
    }

    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findByShortenUrlKey(shortenUrlKey)
                .orElseThrow(NotFoundShortenUrlException::new);

        shortenUrl.increaseRedirectCount();

        shortenUrlRepository.save(shortenUrl);

        return shortenUrl.getOriginalUrl();
    }

    public ShortenUrlInformationResponse getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findByShortenUrlKey(shortenUrlKey)
                .orElseThrow(NotFoundShortenUrlException::new);

        return new ShortenUrlInformationResponse(
                shortenUrl.getOriginalUrl(),
                shortenUrl.getShortenUrlKey(),
                shortenUrl.getRedirectCount()
        );
    }

    private String getUniqueShortenUrlKey() {
        final int MAX_RETRY_COUNT = 5;
        int count = 0;

        while (count++ < MAX_RETRY_COUNT) {
            String shortenUrlKey = ShortenUrl.generateShortenUrlKey();

            if (!shortenUrlRepository.existsByShortenUrlKey(shortenUrlKey)) {
                return shortenUrlKey;
            }
        }

        throw new LackOfShortenUrlKeyException();
    }
}
