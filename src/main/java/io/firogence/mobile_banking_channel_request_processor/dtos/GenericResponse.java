package io.firogence.mobile_banking_channel_request_processor.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Alex Kiburu
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {
    private String transactionId;
    private String reference;
    private Object data;
    private String status;
    private String message;
    private String error;
}
