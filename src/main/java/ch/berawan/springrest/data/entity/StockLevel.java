package ch.berawan.springrest.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "StockLevel")
public class StockLevel {

    public StockLevel(final String product, final String warehouse, final int level) {
        this.product = product;
        this.warehouse = warehouse;
        this.level = level;
    }

    @Getter
    @Setter
    @Id
    //@Indexed(unique = true) todo: check why this leads to an MongoDbException during build/test
    private String id;

    @Getter
    @Setter
    private String product; // todo: get rid of id and use product as id

    @Getter
    @Setter
    private String warehouse;

    @Getter
    @Setter
    private int level;
}