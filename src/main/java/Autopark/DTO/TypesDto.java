package Autopark.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TypesDto {
    private Long id;
    private String name;
    private Double coefTaxes;
}