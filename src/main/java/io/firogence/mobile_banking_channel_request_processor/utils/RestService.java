package io.firogence.mobile_banking_channel_request_processor.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RestService {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    // todo:: make it dynamic (headers, method and payload)
    public Map<String, Object> post(String url, String secret, JsonObject payloadMap){
        Map<String, Object> responseData = new HashMap<>();
        try {
            // Convert the Map to a JSON string
            String jsonPayload = payloadMap.toString();
            log.info("URL:: {} Request:: {}", url, jsonPayload);
            ResponseEntity<?> responseEntity = restClient.post()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .body(jsonPayload)
                    .retrieve()
                    .toEntity(String.class);

            // Add status code and response body to the map
            responseData.put("responseCode", responseEntity.getStatusCode().value());
            responseData.put("responseBody", new HashMap<>());
            if (null != responseEntity.getBody()) {
                String responseBodyStr = responseEntity.getBody().toString();
                Map<String, Object> responseBodyMap = objectMapper.readValue(responseBodyStr, new TypeReference<>() {});
                responseData.put("responseBody", responseBodyMap);
            }
        } catch (RestClientResponseException e) {
            log.error("Http Client Error:: ", e);
            // Handle 4xx client errors, e.g., 400 Bad Request, 401 Unauthorized
            responseData.put("responseCode", e.getStatusCode().value());
            responseData.put("responseBody", e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Http Server Error:: ", e);
            // Handle other exceptions like 5xx server errors or network issues
            responseData.put("responseCode", "500");
            responseData.put("responseBody", e.getMessage());
        }

        return responseData;
    }
}
