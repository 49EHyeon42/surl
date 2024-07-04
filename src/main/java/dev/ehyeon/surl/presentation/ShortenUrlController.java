package dev.ehyeon.surl.presentation;

import dev.ehyeon.surl.application.SimpleShortenUrlService;
import dev.ehyeon.surl.application.request.ShortenUrlCreateRequest;
import dev.ehyeon.surl.application.response.ShortenUrlCreateResponse;
import dev.ehyeon.surl.application.response.ShortenUrlInformationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ShortenUrlController {

    private final SimpleShortenUrlService simpleShortenUrlService;

    ShortenUrlController(SimpleShortenUrlService simpleShortenUrlService) {
        this.simpleShortenUrlService = simpleShortenUrlService;
    }

    @PostMapping("/shortenUrl")
    public ResponseEntity<ShortenUrlCreateResponse> createShortenUrl(
            @Valid @RequestBody ShortenUrlCreateRequest shortenUrlCreateRequest
    ) {
        return ResponseEntity.ok(simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequest));
    }

    @GetMapping("/{shortenUrlKey}")
    public ResponseEntity<?> redirectShortenUrl(
            @PathVariable String shortenUrlKey
    ) throws URISyntaxException {
        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(originalUrl));

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/shortenUrl/{shortenUrlKey}")
    public ResponseEntity<ShortenUrlInformationResponse> getShortenUrlInformation(
            @PathVariable String shortenUrlKey
    ) {
        return ResponseEntity.ok(simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(shortenUrlKey));
    }
}
