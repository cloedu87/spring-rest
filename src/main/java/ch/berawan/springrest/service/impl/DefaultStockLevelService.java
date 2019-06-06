package ch.berawan.springrest.service.impl;

import ch.berawan.springrest.data.dto.StockLevel;
import ch.berawan.springrest.data.repository.StockLevelRepository;
import ch.berawan.springrest.service.StockLevelService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

        return stockLevelRepository.saveAll(stockLevels).stream().sorted(
                (final StockLevel o1, final StockLevel o2) -> {
                    if (o1.getProduct().compareTo(o2.getProduct()) > 0)
                        return 1;
                    else
                        return -1;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public StockLevel addStockLevel(final StockLevel stockLevel) {
        return stockLevelRepository.save(stockLevel);
    }
}