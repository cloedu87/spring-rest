package ch.berawan.springrest.app.data.repository;

import ch.berawan.springrest.app.data.dto.ModifyStockLevel;
import ch.berawan.springrest.app.data.entity.StockLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class StockLevelRepositoryImpl implements StockLevelRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoOperations mongoOperations;

    /**
     *  used for increment and decrement in a atomic way in the db. check https://kamranzafar.org/2016/10/25/atomic-updates-on-mongodb-with-spring-data/ for insights
     *
     * @param modifyStockLevel
     * @return
     */
    @Override
    public long modifyStockLevel(final ModifyStockLevel modifyStockLevel) {

        final Query query = new Query(Criteria
                                              .where("product").is(modifyStockLevel.getProduct())
                                              .and("warehouse").is(modifyStockLevel.getWarehouse()));

        final Update update = new Update().inc("level", modifyStockLevel.getAmount());

        //if result is null we wanna fail here
        return mongoOperations.updateFirst(query, update, StockLevel.class).getModifiedCount();
    }

    /**
     *  used to override the StockLevel.level attribute
     * @param stockLevel
     * @return
     */
    @Override
    public long updateStockLevel(final StockLevel stockLevel) {

        final Query query = new Query(Criteria
                                              .where("product").is(stockLevel.getProduct())
                                              .and("warehouse").is(stockLevel.getWarehouse()));

        final Update update = new Update().set("level", stockLevel.getLevel());

        //if result is null we wanna fail here
        return mongoOperations.updateFirst(query, update, StockLevel.class).getModifiedCount();
    }
}