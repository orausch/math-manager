package Generators;

/**
 * Created by oliver on 24/10/16.
 */
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber {
    public static double getRandomNumber(int lowest, int highest){
        return ThreadLocalRandom.current().nextInt(lowest, highest + 1);

    }
}
