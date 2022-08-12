package Autopark.Entity;

import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;
import Autopark.Rent.Rent;
import Autopark.Vehicle.Color;
import Autopark.Vehicle.Engine.Startable;
import Autopark.Vehicle.VehicleType;
import lombok.*;

import java.util.List;

@Table(name = "Vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @ID
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "stateNumber")
    private String stateNumber;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "year")
    private Integer year;

    @Column(name = "mileage")
    private Integer mileage;
}
