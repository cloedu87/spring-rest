package ch.berawan.springrest.service.impl;

import ch.berawan.springrest.data.dto.StockLevel;
import ch.berawan.springrest.service.StockLevelService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultStockLevelService implements StockLevelService {


    @Override
    public Set<StockLevel> getAll() {

        final Set<StockLevel> stockLevels = new HashSet<>();
        final Map<String, Integer> wareHouses = new HashMap<>();

        wareHouses.put("warehouse 1", 2);
        wareHouses.put("warehouse 2", 4);
        wareHouses.put("warehouse 3", 25);
        wareHouses.put("warehouse 4", 3);
        wareHouses.put("warehouse 5", 77);
        wareHouses.put("warehouse 6", 34);
        wareHouses.put("warehouse 7", 42);

        stockLevels.add(new StockLevel("product1", wareHouses));

        return stockLevels;
    }
}