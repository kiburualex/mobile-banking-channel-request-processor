package io.firogence.mobile_banking_channel_request_processor.exceptions.handlers;

import io.firogence.mobile_banking_channel_request_processor.exceptions.InvalidAuthenticationException;
import io.firogence.mobile_banking_channel_request_processor.exceptions.MissingReferenceException;
import io.firogence.mobile_banking_channel_request_processor.exceptions.OperationNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author Alex Kiburu
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final String TIMESTAMP_FORMAT = "dd-MM-YYYY hh:mm:ss a";
    private final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);

    // used to catch errors from model @Valid annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp){
        log.error("MethodArgumentNotValidException:: ",exp);
        // set will remove any duplicate message
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .status("01")
                                .message(String.join(", ", errors))
                                .validationErrors(errors)
                                .timestamp(LocalDateTime.now().format(DATETIME_FORMATTER))
                                .build()
                );
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp){
        log.error("OperationNotPermittedException:: ",exp);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .status("01")
                                .message(exp.getMessage())
                                .error(exp.getMessage())
                                .timestamp(LocalDateTime.now().format(DATETIME_FORMATTER))
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp){
        log.error("Exception:: ",exp);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .status("01")
                                .message(exp.getMessage())
                                .errorDescription("Internal error, contact admin")
                                .error(exp.getMessage())
                                .timestamp(LocalDateTime.now().format(DATETIME_FORMATTER))
                                .build()
                );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> handleException(NoSuchElementException exp){
        log.error("NoSuchElementException:: ",exp);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .status("01")
                                .message(exp.getMessage())
                                .error(exp.getMessage())
                                .timestamp(LocalDateTime.now().format(DATETIME_FORMATTER))
                                .build()
                );
    }

    @ExceptionHandler(MissingReferenceException.class)
    public ResponseEntity<ExceptionResponse> handleException(MissingReferenceException exp){
        log.error("MissingReferenceException:: ",exp);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .status("01")
                                .message(exp.getMessage())
                                .error(exp.getMessage())
                                .timestamp(LocalDateTime.now().format(DATETIME_FORMATTER))
                                .build()
                );
    }

    @ExceptionHandler(InvalidAuthenticationException .class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidAuthenticationException exp){
        log.error("InvalidAuthenticationException:: ",exp);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .status("01")
                                .message(exp.getMessage())
                                .error(exp.getMessage())
                                .timestamp(LocalDateTime.now().format(DATETIME_FORMATTER))
                                .build()
                );
    }
}
