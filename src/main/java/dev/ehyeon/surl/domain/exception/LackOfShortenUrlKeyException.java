package dev.ehyeon.surl.domain.exception;

import dev.ehyeon.surl.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class LackOfShortenUrlKeyException extends CustomException {

    public LackOfShortenUrlKeyException() {
        super("단축 URL 자원이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
