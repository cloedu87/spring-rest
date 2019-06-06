package ch.berawan.springrest.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "StockLevel")
public class StockLevel {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String product;

    @Getter
    @Setter
    private String warehouse;

    @Getter
    @Setter
    private int level;
}