package ch.berawan.springrest.controller;

import ch.berawan.springrest.data.dto.StockLevel;
import ch.berawan.springrest.service.StockLevelService;
import ch.berawan.springrest.service.impl.DefaultStockLevelService;
import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * responsible for request mapping, parameter validation and TODO: error handling
 */
@Controller
@RequestMapping("/stocklevel")
public class StockLevelController {

    @Resource
    private StockLevelService stockLevelService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public StockLevel getById(@PathVariable("id") final long id) {
        Preconditions.checkNotNull(id);

        return stockLevelService.getById(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<StockLevel> getAllStockLevels() {

        return stockLevelService.getAll();
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ResponseBody
    public List<StockLevel> init(@RequestBody final List<StockLevel> stockLevels) {

        return stockLevelService.addStockLevels(stockLevels);
    }

    @Bean
    public StockLevelService getStockLevelService() {
        return new DefaultStockLevelService();
    }
}