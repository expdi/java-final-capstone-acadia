package expeditors.backend.price;

import expeditors.backend.domain.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Component
@Profile("networkprice")
public class NetworkPriceProvider implements PriceProvider {
    private String priceUrl;
    private RestClient restClient;

    public NetworkPriceProvider() {
        var baseUrl = "http://localhost:10005";
        var rootUrl = "/price";
        priceUrl = rootUrl + "/{id}";

        String notEncoded = "super:password";
        String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Basic " + encodedAuth)
                .build();
    }

    @Override
    public void addPriceToTrack(Track track) {
        ResponseEntity<String> response = restClient.get()
                .uri(priceUrl, 1)
                .retrieve()
                .toEntity(String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String price = response.getBody();
            if (price != null) {
                track.setPrice(price);
            }
        }
    }
}
