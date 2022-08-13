package Autopark.EntitiesService;

import Autopark.Entity.Types;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.orm.EntityManager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TypesService {
    @Autowired
    EntityManager entityManager;

    public Types get(Long id) {
        return entityManager.get(id, Types.class).orElse(new Types());
    }

    public List<Types> getAll() {
        return entityManager.getAll(Types.class);
    }

    public void save(Types types) {
        entityManager.save(types);
    }
}