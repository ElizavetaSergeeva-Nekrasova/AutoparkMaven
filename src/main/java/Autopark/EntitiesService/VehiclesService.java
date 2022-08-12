package Autopark.EntitiesService;

import Autopark.Entity.Vehicles;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Infrastructure.orm.EntityManager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VehiclesService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Vehicles get(Long id) {
        return null;
    }

    public List<Vehicles> getAll() {
        return null;
    }

    public Long save(Vehicles vehicles) {
        return null;
    }
}