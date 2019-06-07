package ch.berawan.springrest.service.impl;

import ch.berawan.springrest.data.dto.StockLevel;
import ch.berawan.springrest.data.repository.StockLevelRepository;
import ch.berawan.springrest.exception.ElementNotModifiedException;
import ch.berawan.springrest.service.StockLevelService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class DefaultStockLevelService implements StockLevelService {

    final Logger logger = LoggerFactory.getLogger(DefaultStockLevelService.class);


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

    @Override
    public long updateStockLevel(final StockLevel stockLevel) {

        final long updateResult = stockLevelRepository.updateStockLevel(stockLevel);

        if (updateResult == 0l) {
            throw new ElementNotModifiedException("StockLevel Object " + stockLevel.getId() + " not updated");
        }

        return updateResult;
    }
}