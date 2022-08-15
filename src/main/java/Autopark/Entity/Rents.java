package Autopark.Entity;

import Autopark.Exceptions.NotVehicleException;
import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;
import Autopark.Service.TechnicalSpecialist;
import lombok.*;

import java.util.Date;

@Table(name = "Rents")
@Builder
@Data
@ToString
@NoArgsConstructor
public class Rents {
    @ID
    private Long id;

    @Column(name = "vehicleId")
    private Long vehicleId;

    @Column(name = "date")
    private Date date;

    @Column(name = "cost")
    private Double cost;

    public Rents(Long id, Long vehicleId, Date date, Double cost) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.date = date;
        setCost(cost);
    }

    public void setCost(Double cost) {
        try {
            if (TechnicalSpecialist.validateCost(cost)) {
                this.cost = cost;
            }

            throw new NotVehicleException("Vehicle cost is wrong" + cost);
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }
}