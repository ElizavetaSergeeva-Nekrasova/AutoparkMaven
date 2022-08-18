package Autopark.EntitiesService;

import Autopark.Entity.Vehicles;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.orm.EntityManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VehiclesService {
    @Autowired
    EntityManager entityManager;

    public Vehicles get(Long id) {
        return entityManager.get(id, Vehicles.class).orElse(new Vehicles());
    }

    public List<Vehicles> getAll() {
        return entityManager.getAll(Vehicles.class);
    }

    public void save(Vehicles vehicles) {
        entityManager.save(vehicles);
    }
}