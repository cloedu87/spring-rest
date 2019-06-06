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


    /**
     * @param id
     * @return specific StockLevel according to uniq id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public StockLevel getById(@PathVariable("id") final long id) {
        Preconditions.checkNotNull(id);

        return stockLevelService.getById(id);
    }

    /**
     * @return all StockLevels in the database
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<StockLevel> getAllStockLevels() {

        return stockLevelService.getAll();
    }

    /**
     * @param product
     * @param warehouse
     * @param stockLevel StockLevel object who will be added to the database
     * @return
     */
    @RequestMapping(value = "/{product}/{warehouse}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public StockLevel addStockLevel(@PathVariable final String product, @PathVariable final String warehouse, @RequestBody final StockLevel stockLevel) {

        //to ensure stock level of requested resource/url will be created
        stockLevel.setProduct(product);
        stockLevel.setWarehouse(warehouse);

        return stockLevelService.addStockLevel(stockLevel);
    }

    /**
     * @param stockLevels list of stocklevels, which we like to have in the database
     * @return list of stocklevels, which was written to the database
     */
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