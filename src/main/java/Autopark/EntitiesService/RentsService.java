package Autopark.EntitiesService;

import Autopark.Entity.Rents;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Infrastructure.orm.EntityManager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RentsService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Rents get(Long id) {
        return null;
    }

    public List<Rents> getAll() {
        return null;
    }

    public Long save(Rents rents) {
        return null;
    }
}