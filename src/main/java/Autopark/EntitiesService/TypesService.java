package Autopark.EntitiesService;

import Autopark.Entity.Types;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Infrastructure.orm.EntityManager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class TypesService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Types get(Long id) {
        return entityManager.get(id, Types.class).orElse(new Types());
    }

    public List<Types> getAll() {
        return null;
    }

    public void save(Types types) {
        entityManager.save(types);
    }
}