package io.firogence.mobile_banking_channel_request_processor.utils;

import io.firogence.mobile_banking_channel_request_processor.configurations.wrappers.CustomHttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Service
public class UtilService {
    // extract details of the device used to send the request
    public String getDeviceAgentDetails(String userAgent) {
        String deviceDetails = "UNKNOWN";

        Parser parser = new Parser();
        Client client = parser.parse(userAgent);
        if (Objects.nonNull(client)) {
            deviceDetails = client.userAgent.family
                    + " " + client.userAgent.major + "."
                    + client.userAgent.minor + " - "
                    + client.os.family + " " + client.os.major
                    + "." + client.os.minor;
        }
        return deviceDetails;
    }

    public String getRequestData(HttpServletRequest request) throws IOException {
        return switch (request.getMethod().toUpperCase()) {
            case "POST", "PUT" -> extractPostDetails(request);
            case "GET" -> extractRequestDetails(request);
            default -> extractBody(request);
        };
    }

    private String extractPostDetails(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            /* using CustomHttpServletRequestWrapper will allow to call
             * getReader() multiple times and avoid error:
             * "java.lang.IllegalStateException: getReader() has already been called for this request"
             * */
            // get the request's input stream
            HttpServletRequest customRequest = new CustomHttpServletRequestWrapper(request);
            bufferedReader = customRequest.getReader();
            String line;

            // read each line of the input stream
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            log.error("Error extracting post details ", e);
        } finally {
            // close the BufferedReader
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("Error extracting post details >>", e);
                }
            }
        }

        return stringBuilder.toString();
    }

    public String extractRequestDetails(HttpServletRequest request) {
        StringBuilder requestDetails = new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();

        // form the combined parameters get request details
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();
            for (String paramValue : paramValues) {
                String param = paramName + "=" + paramValue;
                String appender = (requestDetails.isEmpty()) ? "" : "&";
                requestDetails.append(appender).append(param);
            }
        }

        return requestDetails.toString();
    }

    private String extractBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            int i;
            while ((i = reader.read()) != -1) {
                stringBuilder.append((char) i);
            }
        }
        return stringBuilder.toString();
    }
}
