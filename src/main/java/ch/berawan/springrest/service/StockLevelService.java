package ch.berawan.springrest.service;

import ch.berawan.springrest.data.dto.StockLevel;

import java.util.Set;

public interface StockLevelService {

    Set<StockLevel> getAll();
}