package ch.berawan.springrest.service.impl;

import ch.berawan.springrest.data.dto.StockLevel;
import ch.berawan.springrest.data.repository.StockLevelRepository;
import ch.berawan.springrest.service.StockLevelService;

import javax.annotation.Resource;
import java.util.List;

public class DefaultStockLevelService implements StockLevelService {

    @Resource
    private StockLevelRepository stockLevelRepository;


    @Override
    public StockLevel getById(final long id) {

        return stockLevelRepository.findFirstById(id);
    }

    @Override
    public List<StockLevel> getAll() {

        return stockLevelRepository.findAll();
    }

    @Override
    public List<StockLevel> addStockLevels(final List<StockLevel> stockLevels) {

        return stockLevelRepository.saveAll(stockLevels);
    }
}