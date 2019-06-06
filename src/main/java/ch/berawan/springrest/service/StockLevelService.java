package ch.berawan.springrest.service;

import ch.berawan.springrest.data.dto.StockLevel;

import java.util.List;

public interface StockLevelService {

    StockLevel getById(final long id);

    List<StockLevel> getAll();

    List<StockLevel> addStockLevels(final List<StockLevel> stockLevels);

    StockLevel addStockLevel(final StockLevel stockLevel);
}