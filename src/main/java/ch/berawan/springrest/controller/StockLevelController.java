package ch.berawan.springrest.controller;

import ch.berawan.springrest.data.dto.ModifyStockLevel;
import ch.berawan.springrest.data.entity.StockLevel;
import ch.berawan.springrest.service.StockLevelService;
import ch.berawan.springrest.service.impl.DefaultStockLevelService;

import static com.google.common.base.Preconditions.*;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import static org.springframework.util.StringUtils.*;

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
    public StockLevel getById(@PathVariable final long id) {
        checkArgument(!isEmpty(id));

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
    @RequestMapping(value = "/{product}/{warehouse}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public StockLevel addStockLevel(@PathVariable final String product, @PathVariable final String warehouse, @RequestBody final StockLevel stockLevel) {

        checkNotNull(stockLevel);
        checkArgument(!isEmpty(product));
        checkArgument(!isEmpty(warehouse));

        //to ensure stock level of requested resource/url will be created
        stockLevel.setProduct(product);
        stockLevel.setWarehouse(warehouse);

        return stockLevelService.addStockLevel(stockLevel);
    }

    /**
     * @param product
     * @param warehouse
     * @param stockLevel StockLevel object which will be used to override the StockLevel.level value
     * @return
     */
    @RequestMapping(value = "/{product}/{warehouse}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateStockLevel(@PathVariable final String product, @PathVariable final String warehouse, @RequestBody final StockLevel stockLevel) {

        checkNotNull(stockLevel);
        checkArgument(!isEmpty(product));
        checkArgument(!isEmpty(warehouse));

        //to ensure stock level of requested resource/url will be modified
        stockLevel.setProduct(product);
        stockLevel.setWarehouse(warehouse);

        stockLevelService.updateStockLevel(stockLevel);
    }

    /**
     * @param stockLevels list of stocklevels, which we like to have in the database
     * @return list of stocklevels, which was written to the database
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ResponseBody
    public List<StockLevel> init(@RequestBody final List<StockLevel> stockLevels) {

        checkNotNull(stockLevels);

        return stockLevelService.addStockLevels(stockLevels);
    }

    @RequestMapping(value = "/{product}/{warehouse}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void modifyStockLevel(@PathVariable final String product, @PathVariable final String warehouse, @RequestBody final ModifyStockLevel modifyStockLevel) {

        checkArgument(!isEmpty(product));
        checkArgument(!isEmpty(warehouse));
        checkNotNull(modifyStockLevel);

        //to ensure stock level of requested resource/url will be modified
        modifyStockLevel.setProduct(product);
        modifyStockLevel.setWarehouse(warehouse);

        stockLevelService.modifyStockLevel(modifyStockLevel);
    }

    @Bean
    public StockLevelService getStockLevelService() {
        return new DefaultStockLevelService();
    }
}