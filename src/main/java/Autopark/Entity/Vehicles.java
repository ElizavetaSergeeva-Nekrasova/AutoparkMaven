package Autopark.Entity;

import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;

import lombok.*;

@Table(name = "Vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @ID
    private Long id;

    @Column(name = "typeId", unique = true)
    private Integer typeId;

    @Column(name = "model", unique = true)
    private String model;

    @Column(name = "stateNumber", unique = true)
    private String stateNumber;

    @Column(name = "weight", unique = true)
    private Double weight;

    @Column(name = "year", unique = true)
    private Integer year;

    @Column(name = "mileage", unique = true)
    private Integer mileage;

    @Column(name = "color", unique = true)
    private String color;

    @Column(name = "engineType", unique = true)
    private String engineType;
}