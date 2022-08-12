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

    @Column(name = "name")
    private String name;

    @Column(name = "coefTaxes")
    private Double coefTaxes;
}