package ch.berawan.springrest.service.impl;

import ch.berawan.springrest.CONSTANTS;
import ch.berawan.springrest.data.dto.StockLevel;
import ch.berawan.springrest.service.StockLevelService;

import java.util.List;


/**
 * for dev/test and play around with less complexity. idea: no dependencies to datasources/third party services
 */
public class TrivialStockLevelService implements StockLevelService {

    @Override
    public StockLevel getById(long id) {
        return new StockLevel(42l, "trivial product", "trivial warehouse", 42);
    }

    @Override
    public List<StockLevel> getAll() {

        return CONSTANTS.initialStocklevels();
    }

    @Override
    public List<StockLevel> addStockLevels(final List<StockLevel> stockLevels) {
        return null;
    }
}