package fhict.kwetter.trendservice.persistence.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Component
@Slf4j
public class AddClient {
    private static final String ADD_URL = "https://europe-west1-kwetter-313614.cloudfunctions.net/kwetter-adder";

    @Autowired
    private final RestTemplate restTemplate;

    public SumDto add(int n1, int n2) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity request = new HttpEntity(headers);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(ADD_URL)
        .queryParam("number1", 3)
        .queryParam("number2", 2);


        ResponseEntity<SumDto> response = restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.GET,
                request,
                SumDto.class
        );

        if(response.getStatusCode() == HttpStatus.OK) {
            log.info("sum received." + System.lineSeparator() + "Sum: " + response.getBody().getSum());
            return response.getBody();
        } else {
            log.error("sum request failed");
            return null;
        }

//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//
//        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(ADD_URL)
//                .queryParam("number1", 3)
//                .queryParam("number2", 2);
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        HttpEntity<SumDto> response = restTemplate.exchange(
//                uriComponentsBuilder.toUriString(),
//                HttpMethod.GET,
//                entity,
//                SumDto.class
//        );
//
//        return response.getBody();
//        SumDto sumDto = restTemplate.getForObject(ADD_URL, SumDto.class);

    }
}
