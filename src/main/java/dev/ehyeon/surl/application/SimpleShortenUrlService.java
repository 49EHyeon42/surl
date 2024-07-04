package dev.ehyeon.surl.application;

import dev.ehyeon.surl.domain.LackOfShortenUrlKeyException;
import dev.ehyeon.surl.domain.NotFoundShortenUrlException;
import dev.ehyeon.surl.domain.ShortenUrl;
import dev.ehyeon.surl.domain.ShortenUrlRepository;
import dev.ehyeon.surl.application.request.ShortenUrlCreateRequest;
import dev.ehyeon.surl.application.response.ShortenUrlCreateResponse;
import dev.ehyeon.surl.application.response.ShortenUrlInformationResponse;
import org.springframework.stereotype.Service;

@Service
public class SimpleShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;

    SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public ShortenUrlCreateResponse generateShortenUrl(ShortenUrlCreateRequest shortenUrlCreateRequest) {
        String originalUrl = shortenUrlCreateRequest.originalUrl();
        String shortenUrlKey = getUniqueShortenUrlKey();

        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);

        shortenUrlRepository.saveShortenUrl(shortenUrl);

        return new ShortenUrlCreateResponse(shortenUrl.getOriginalUrl(), shortenUrl.getShortenUrlKey());
    }

    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if (null == shortenUrl) {
            throw new NotFoundShortenUrlException();
        }

        shortenUrl.increaseRedirectCount();

        shortenUrlRepository.saveShortenUrl(shortenUrl);

        return shortenUrl.getOriginalUrl();
    }

    public ShortenUrlInformationResponse getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if (null == shortenUrl) {
            throw new NotFoundShortenUrlException();
        }

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
            ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

            if (null == shortenUrl) {
                return shortenUrlKey;
            }
        }

        throw new LackOfShortenUrlKeyException();
    }
}
