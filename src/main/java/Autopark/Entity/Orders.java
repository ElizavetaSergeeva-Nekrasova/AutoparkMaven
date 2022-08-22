package Autopark.Entity;

import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "Orders")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @ID
    private Long id;

    @Column(name = "vehicleId")
    private Long vehicleId;

    @Column(name = "orderLine")
    private String orderLine;

    public Orders(Long vehicleId, String orderLine) {
        this.vehicleId = vehicleId;
        this.orderLine = orderLine;
    }
}