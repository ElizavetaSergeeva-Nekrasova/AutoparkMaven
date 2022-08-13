package Autopark.Entity;

import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "Types")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Types {
    @ID
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "coefTaxes", unique = true)
    private Double coefTaxes;
}