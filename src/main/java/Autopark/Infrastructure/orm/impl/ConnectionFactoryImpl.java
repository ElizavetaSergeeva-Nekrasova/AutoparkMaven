package Autopark.Infrastructure.orm.impl;

import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Infrastructure.core.annotations.Property;
import Autopark.Infrastructure.orm.ConnectionFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

@Setter
@Getter
@NoArgsConstructor
public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property("url")
    private String url;

    @Property("username")
    private String username;

    @Property("password")
    private String password;

    private Connection connection;

    @SneakyThrows
    @InitMethod
    public void init() {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}