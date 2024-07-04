package dev.ehyeon.surl.application.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record ShortenUrlCreateRequest(
        @NotNull
        @URL(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
        String originalUrl
) {
}
