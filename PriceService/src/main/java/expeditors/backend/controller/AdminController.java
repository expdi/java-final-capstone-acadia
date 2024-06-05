package expeditors.backend.controller;

import expeditors.backend.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/lowerLimit")
    public int getLowerLimit(){
        return priceService.getLowerLimit();
    }

    @GetMapping("/highLimit")
    public int getHighLimit(){
        return priceService.getHigherLimit();
    }

    @GetMapping("/bothLimits")
    public String getBothLimits(){
        return priceService.getLowerLimit() + ":" + priceService.getHigherLimit();
    }

    @PutMapping("/lowerLimit/{ll}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setLowerLimit(@PathVariable("ll") int lowerLimit){
        priceService.setLowerLimit(lowerLimit);
    }

    @PutMapping("/highLimit/{ul}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setHighLimit(@PathVariable("ul") int highLimit){
        priceService.setHigherLimit(highLimit);
    }
}
