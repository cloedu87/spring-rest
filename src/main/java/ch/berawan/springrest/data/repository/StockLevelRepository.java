package ch.berawan.springrest.data.repository;

import ch.berawan.springrest.data.dto.StockLevel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockLevelRepository extends MongoRepository<StockLevel, String> {

    StockLevel findFirstById(final long id);

// Todo: test and make real implementation, super experimental :)
//    @Query("{product:'?0', warehouse:'?1'}")
//    StockLevel findByProductAndWarehouse(final String product, final String warehouse);
}
