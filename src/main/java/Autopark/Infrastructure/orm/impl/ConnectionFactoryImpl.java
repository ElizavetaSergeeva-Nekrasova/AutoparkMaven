package Autopark.Infrastructure.orm.impl;

import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Infrastructure.core.annotations.Property;
import Autopark.Infrastructure.orm.ConnectionFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

@Setter
@Getter
public class ConnectionFactoryImpl implements ConnectionFactory {
    //@Property("url")
    private String url = "jdbc:postgresql://localhost:5432/MyDatabase";

    //@Property("username")
    private String username = "postgres";

    //@Property("password")
    private String password = "root";

    private Connection connection;

    public ConnectionFactoryImpl() {
    }

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}