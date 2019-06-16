package ch.berawan.springrest.app.data.repository;

import ch.berawan.springrest.app.data.dto.ModifyStockLevel;
import ch.berawan.springrest.app.data.entity.StockLevel;

public interface StockLevelRepositoryCustom {

    long updateStockLevel(final StockLevel stockLevel);

    long modifyStockLevel(final ModifyStockLevel modifyStockLevel);
}
