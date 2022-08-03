package VehicleCollections;

public class MyQueue<T> {
    private Object[] array;
    private int size;
    private int capacity;
    private int indexOfFirst;
    private int indexOfLast = -1;
    private int DEFAULT_CAPACITY = 10;

    public MyQueue() {
        capacity = DEFAULT_CAPACITY;
        array = new Object[capacity];
    }

    public void enqueue(T element) {
        if (indexOfLast >= capacity - 1) {
            resizeArray();
        }

        indexOfLast++;
        array[indexOfLast] = element;
        size++;
    }

    public T dequeue() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }

        T firstElement = (T) array[indexOfFirst];;
        indexOfFirst++;
        size--;

        return firstElement;
    }

    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }

        return (T) array[indexOfFirst];
    }

    public int size() {
        return size;
    }

    private void resizeArray() {
        capacity = capacity * 2;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, indexOfFirst, newArray, 0, size);

        array = newArray;
        indexOfFirst = 0;
        indexOfLast = size - 1;
    }
}