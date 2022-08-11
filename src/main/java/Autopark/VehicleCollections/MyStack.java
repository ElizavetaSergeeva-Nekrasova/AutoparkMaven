package Autopark.VehicleCollections;

public class MyStack<T> {
    private Object[] array;
    private int size;
    private int capacity;
    private double DEFAULT_LOAD_FACTOR = 0.75;
    private int DEFAULT_CAPACITY = 10;


    public MyStack() {
        capacity = DEFAULT_CAPACITY;
        array = new Object[capacity];
    }

    public void push(T element) {
        double loadFactor = (double) size / (double) capacity;
        if (loadFactor >= DEFAULT_LOAD_FACTOR) {
            resizeArray();
        }

        array[size] = element;
        size++;
    }

    public T peek() {
        if (size == 0) {
            throw new IllegalArgumentException("Stack is empty");
        }

        return (T) array[size - 1];
    }

    public T pop() {
        if (size == 0) {
            throw new IllegalArgumentException("Stack is empty");
        }

        T lastElement = (T) array[size - 1];
        size--;

        return lastElement;
    }

    public int size() {
        return size;
    }

    private void resizeArray() {
        capacity = capacity * 2;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);

        array = newArray;
    }
}