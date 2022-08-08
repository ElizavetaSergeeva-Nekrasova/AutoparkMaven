package Autopark.Infrastructure.config;

import Autopark.Infrastructure.core.Scanner;

public interface Config {
    <T> Class<? extends T> getImplementation(Class<T> target);
    Scanner getScanner();
}
