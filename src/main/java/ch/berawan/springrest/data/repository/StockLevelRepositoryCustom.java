package ch.berawan.springrest.data.repository;

import ch.berawan.springrest.data.dto.StockLevel;

public interface StockLevelRepositoryCustom {

    long updateStockLevel(final StockLevel stockLevel);
}
