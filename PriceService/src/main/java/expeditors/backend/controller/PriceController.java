package expeditors.backend.controller;

import expeditors.backend.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/price")
public class PriceController {
    @Autowired
    private PriceService priceService;
    @GetMapping("{id}")
    public String getTrackPrice(@PathVariable("id") int id){
        double price = ThreadLocalRandom.current().nextDouble(priceService.getLowerLimit(),priceService.getHigherLimit());
        DecimalFormat decimalFormat = new DecimalFormat("$#0.00");
        String formattedPrice = decimalFormat.format(price);
        return formattedPrice;
    }
}
