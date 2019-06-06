package ch.berawan.springrest.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class StockLevel {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Map<String, Integer> warehouses;
}
