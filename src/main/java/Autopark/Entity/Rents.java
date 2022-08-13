package Autopark.Entity;

import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;
import lombok.*;

import java.sql.Date;

@Table(name = "Rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rents {
    @ID
    private Long id;

    @Column(name = "vehicleId", unique = true)
    private Long vehicleId;

    @Column(name = "date", unique = true)
    private Date date;

    @Column(name = "cost", unique = true)
    private Double cost;
}