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

    @Column(name = "vehicleId")
    private Long vehicleId;

    @Column(name = "date")
    private Date date;

    @Column(name = "cost")
    private Double cost;
}