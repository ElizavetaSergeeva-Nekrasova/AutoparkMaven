package Autopark.EntitiesService;

import Autopark.Entity.Orders;
import Autopark.Entity.Rents;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.orm.EntityManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrdersService {
    @Autowired
    EntityManager entityManager;

    public Orders get(Long id) {
        return entityManager.get(id, Orders.class).orElse(new Orders());
    }

    public List<Orders> getAll() {
        return entityManager.getAll(Orders.class);
    }

    public void save(Orders orders) {
        entityManager.save(orders);
    }

    public void delete(Orders orders) {
        entityManager.delete(orders);
    }
}