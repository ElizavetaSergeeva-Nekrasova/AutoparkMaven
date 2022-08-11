package Autopark.RandomizeUtils;

public class Randomizer {
    public static int getRandomFromZeroToNMinusOne(int n) {
        return (int) (Math.random() * n);
    }

    public static int getRandomFromOneToN(int n) {
        return (int) ((Math.random() * n) + 1);
    }
}