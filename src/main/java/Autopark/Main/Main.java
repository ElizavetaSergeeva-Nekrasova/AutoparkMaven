package Autopark.Main;

import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Infrastructure.orm.service.PostgresDataBaseService;
import Autopark.Servlets.ContextUtil;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = ApplicationContext.getInstance("Autopark", ContextUtil.getInterfaceToImplementation());
        PostgresDataBaseService postgresDataBaseService = applicationContext.getObject(PostgresDataBaseService.class);
    }
}