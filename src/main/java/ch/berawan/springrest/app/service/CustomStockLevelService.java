package ch.berawan.springrest.app.service;

import ch.berawan.springrest.app.data.dto.ModifyStockLevel;
import ch.berawan.springrest.app.data.entity.StockLevel;
import ch.berawan.springrest.app.data.repository.StockLevelRepository;
import ch.berawan.springrest.app.exception.ElementNotCreatedException;
import ch.berawan.springrest.app.exception.ElementNotModifiedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomStockLevelService implements StockLevelService {

    final Logger logger = LoggerFactory.getLogger(CustomStockLevelService.class);

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
                Comparator.comparing(StockLevel::getProduct)
        ).collect(Collectors.toList());
    }

    @Override
    public StockLevel addStockLevel(final StockLevel stockLevel) {

        final StockLevel createdStockLevel = stockLevelRepository.save(stockLevel);

        if (createdStockLevel == null) {
            throw new ElementNotCreatedException("StockLevel " + stockLevel.getId() + " not created");
        }

        return createdStockLevel;
    }

    @Override
    public void updateStockLevel(final StockLevel stockLevel) {

        final long updateResult = stockLevelRepository.updateStockLevel(stockLevel);

        // be aware: if new data is exactly same like old data, updateResult will be '0' as well
        if (updateResult == 0l) {
            throw new ElementNotModifiedException(
                    "StockLevel 'product: " + stockLevel.getProduct() +
                            ", warehouse: " + stockLevel.getWarehouse() +
                            ", amount inc/dec: " + stockLevel.getLevel() + "' not updated");
        }
    }

    @Override
    public void modifyStockLevel(final ModifyStockLevel modifyStockLevel) {

        final long modifyResult = stockLevelRepository.modifyStockLevel(modifyStockLevel);

        if (modifyResult != 1l) {
            throw new ElementNotModifiedException(
                    "StockLevel 'product: " + modifyStockLevel.getProduct() +
                            ", warehouse: " + modifyStockLevel.getWarehouse() +
                            ", amount inc/dec: " + modifyStockLevel.getAmount() + "' not modified");
        }
    }
}