package dev.ehyeon.surl.application;

import dev.ehyeon.surl.domain.exception.LackOfShortenUrlKeyException;
import dev.ehyeon.surl.domain.exception.NotFoundShortenUrlException;
import dev.ehyeon.surl.domain.ShortenUrl;
import dev.ehyeon.surl.domain.ShortenUrlRepository;
import dev.ehyeon.surl.application.request.ShortenUrlCreateRequest;
import dev.ehyeon.surl.application.response.ShortenUrlCreateResponse;
import dev.ehyeon.surl.application.response.ShortenUrlInformationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SimpleShortenUrlService {

    @Value("${config.shorten-url.maximum-number-of-retries}")
    private int MAXIMUM_NUMBER_OF_RETRIES;

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
        for (int i = 0; i < MAXIMUM_NUMBER_OF_RETRIES; i++) {
            String shortenUrlKey = ShortenUrl.generateShortenUrlKey();

            if (!shortenUrlRepository.existsByShortenUrlKey(shortenUrlKey)) {
                return shortenUrlKey;
            }
        }

        throw new LackOfShortenUrlKeyException();
    }
}
