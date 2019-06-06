package ch.berawan.springrest.controller;

import ch.berawan.springrest.data.dto.StockLevel;
import ch.berawan.springrest.service.StockLevelService;
import ch.berawan.springrest.service.impl.DefaultStockLevelService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.Set;

@Controller
@RequestMapping("/stocklevel")
public class StockLevelController {

    @Resource
    private StockLevelService stockLevelService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Set<StockLevel> getAllStockLevels() {
        //Preconditions.checkNotNull();

        final Set<StockLevel> stockLevel = stockLevelService.getAll();

        //if (stockLevel == null)
        //throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase());

        return stockLevel;
    }


    @Bean
    public StockLevelService getStockLevelService() {
        return new DefaultStockLevelService();
    }
}
