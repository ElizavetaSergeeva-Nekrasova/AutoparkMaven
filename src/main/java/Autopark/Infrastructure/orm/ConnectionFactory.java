package Autopark.Infrastructure.orm;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}