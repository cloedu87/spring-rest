package ch.berawan.springrest.data.repository;

import ch.berawan.springrest.data.dto.StockLevel;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class StockLevelRepositoryImpl implements StockLevelRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public long updateStockLevel(final StockLevel stockLevel) {

        final Query query = new Query(Criteria
                .where("product").is(stockLevel.getProduct())
                .and("warehouse").is(stockLevel.getWarehouse()));

        final Update update = new Update();
        update.set("level", stockLevel.getLevel());

        final UpdateResult result = mongoTemplate.updateFirst(query, update, StockLevel.class);

        //if result is null we wanna fail here
        return result.getModifiedCount();
    }
}
