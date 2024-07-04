package dev.ehyeon.surl.domain.exception;

import dev.ehyeon.surl.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ShortenUrlNotFoundException extends CustomException {

    public ShortenUrlNotFoundException() {
        super("단축 URL을 찾지 못했습니다.", HttpStatus.NOT_FOUND);
    }
}
