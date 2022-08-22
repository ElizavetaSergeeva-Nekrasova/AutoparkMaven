package Autopark.Parser;

import Autopark.EntitiesService.OrdersService;
import Autopark.Entity.Orders;
import Autopark.Infrastructure.core.annotations.Autowired;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParserOrdersFromDB {
    @Autowired
    OrdersService ordersService;

    public List<Orders> getOrders() {
        return ordersService.getAll();
    }

    public void saveOrder(Orders orders) {
        ordersService.save(orders);
    }

    public void delete(Orders orders) {
        ordersService.delete(orders);
    }
}