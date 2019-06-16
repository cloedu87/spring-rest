package ch.berawan.springrest.app.service;

import ch.berawan.springrest.app.data.dto.ModifyStockLevel;
import ch.berawan.springrest.app.data.entity.StockLevel;

import java.util.List;

public interface StockLevelService {

    StockLevel getById(final long id);

    List<StockLevel> getAll();

    List<StockLevel> addStockLevels(final List<StockLevel> stockLevels);

    StockLevel addStockLevel(final StockLevel stockLevel);

    void updateStockLevel(final StockLevel stockLevel);

    void modifyStockLevel(final ModifyStockLevel modifyStockLevel);
}