package ch.berawan.springrest.data.repository;

import ch.berawan.springrest.data.dto.ModifyStockLevel;
import ch.berawan.springrest.data.entity.StockLevel;

public interface StockLevelRepositoryCustom {

    long updateStockLevel(final StockLevel stockLevel);

    long modifyStockLevel(final ModifyStockLevel modifyStockLevel);
}
