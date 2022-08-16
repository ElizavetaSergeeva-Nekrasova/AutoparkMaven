package Autopark.EntitiesService;

import Autopark.Entity.Rents;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.orm.EntityManager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RentsService {
    @Autowired
    EntityManager entityManager;

    public Rents get(Long id) {
        return entityManager.get(id, Rents.class).orElse(new Rents());
    }

    public List<Rents> getAll() {
        return entityManager.getAll(Rents.class);
    }

    public void save(Rents rents) {
        entityManager.save(rents);
    }
}