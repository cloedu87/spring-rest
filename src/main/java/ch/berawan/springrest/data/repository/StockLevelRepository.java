package ch.berawan.springrest.data.repository;

import ch.berawan.springrest.data.entity.StockLevel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface StockLevelRepository extends MongoRepository<StockLevel, String>, StockLevelRepositoryCustom {

    StockLevel findFirstById(final long id);

    @Query("{product:'?0', warehouse:'?1'}")
    StockLevel findFirstByProductAndWarehouse(final String product, final String warehouse);
}
