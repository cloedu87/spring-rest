package ch.berawan.springrest;

import ch.berawan.springrest.data.dto.StockLevel;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

public final class CONSTANTS {


    private static final List<StockLevel> stockLevels = new LinkedList<>();

    public static final StockLevel STOCK_LEVEL = new StockLevel(1001l, "product1", "warehouse1", 33);

    public static List<StockLevel> initialStocklevels() {

        if (CollectionUtils.isEmpty(stockLevels)) {
            stockLevels.add(new StockLevel(1001l, "product1", "warehouse1", 33));
            stockLevels.add(new StockLevel(1002l, "product1", "warehouse2", 43));
            stockLevels.add(new StockLevel(1003l, "product1", "warehouse3", 59));
            stockLevels.add(new StockLevel(1004l, "product2", "warehouse1", 54));
            stockLevels.add(new StockLevel(1005l, "product2", "warehouse2", 92));
            stockLevels.add(new StockLevel(1006l, "product3", "warehouse1", 21));
            stockLevels.add(new StockLevel(1007l, "product3", "warehouse2", 44));
            stockLevels.add(new StockLevel(1008l, "product3", "warehouse3", 42));
            stockLevels.add(new StockLevel(1009l, "product3", "warehouse4", 24));
        }

        return stockLevels;
    }

    public static String TEST_STOCK_LEVELS_JSON = "[{\"id\":1001,\"product\":\"product1\",\"warehouse\":\"warehouse1\",\"level\":33},{\"id\":1002,\"product\":\"product1\",\"warehouse\":\"warehouse2\",\"level\":43},{\"id\":1003,\"product\":\"product1\",\"warehouse\":\"warehouse3\",\"level\":59},{\"id\":1004,\"product\":\"product2\",\"warehouse\":\"warehouse1\",\"level\":54},{\"id\":1005,\"product\":\"product2\",\"warehouse\":\"warehouse2\",\"level\":92},{\"id\":1006,\"product\":\"product3\",\"warehouse\":\"warehouse1\",\"level\":21},{\"id\":1007,\"product\":\"product3\",\"warehouse\":\"warehouse2\",\"level\":44},{\"id\":1008,\"product\":\"product3\",\"warehouse\":\"warehouse3\",\"level\":42},{\"id\":1009,\"product\":\"product3\",\"warehouse\":\"warehouse4\",\"level\":24}]";
    public static String TEST_STOCK_LEVEL_JSON = "{\"id\":1001,\"product\":\"product1\",\"warehouse\":\"warehouse1\",\"level\":33}";
}