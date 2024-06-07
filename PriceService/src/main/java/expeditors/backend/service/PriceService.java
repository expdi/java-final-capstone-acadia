package expeditors.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    @Value("${expeditors.backend.trackpricing.lowerLimit}")
    private int lowerLimit;
    @Value("${expeditors.backend.trackpricing.highLimit}")
    private int higherLimit;

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getHigherLimit() {
        return higherLimit;
    }

    public void setHigherLimit(int higherLimit) {
        this.higherLimit = higherLimit;
    }
}
