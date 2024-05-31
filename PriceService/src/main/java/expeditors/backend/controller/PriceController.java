package expeditors.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/price")
public class PriceController {
    private int lowerLimit = 0;
    private int higherLimit = 5;

    @GetMapping("{id}")
    public String getTrackPrice(@PathVariable("id") int id){
        double price = ThreadLocalRandom.current().nextDouble(lowerLimit, higherLimit);
        DecimalFormat decimalFormat = new DecimalFormat("$#0.00");
        String formattedPrice = decimalFormat.format(price);
        return formattedPrice;
    }
}
