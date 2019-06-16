package ch.berawan.springrest.app.data.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ModifyStockLevel {

    @Getter
    @Setter
    private String product;

    @Getter
    @Setter
    private String warehouse;

    @Getter
    @Setter
    private int amount;
}
