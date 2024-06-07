package expeditors.backend.price;

import expeditors.backend.domain.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("localprice")
public class InMemoryPriceProvider implements PriceProvider {
    @Override
    public void addPriceToTrack(Track track){
        String price = "$4.19";
        track.setPrice(price);
    }
}
