package Autopark.Infrastructure.orm.impl;

import Autopark.Infrastructure.core.Context;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.orm.ConnectionFactory;
import Autopark.Infrastructure.orm.EntityManager;
import Autopark.Infrastructure.orm.service.PostgresDataBaseService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
public class EntityManagerImpl implements EntityManager {
    @Autowired
    private ConnectionFactory connection;

    @Autowired
    private PostgresDataBaseService dataBaseService;

    @Autowired
    Context context;

    @Override
    public <T> Optional<T> get(long id, Class<T> clazz) {
        return dataBaseService.get(id, clazz);
    }

    @Override
    public void save(Object object) {
        dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dataBaseService.getAll(clazz);
    }
}