package test;

import factory.QuadraticProblemFactory;
import factory.RightAngleTrigonometricProblemFactory;
import model.Quadratic;
import model.RightAngleTrigonometric;
import model.Text;
import module.DatabaseManager;
import module.Util;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {

    @Test // Check if a quadratic problem is equal when written and read from database
    public void quadraticReadWrite() {
        DatabaseManager write = new DatabaseManager(true);
        Quadratic quadratic = QuadraticProblemFactory.generateSolveQuadraticQuestion(3, 20, 20);
        write.insertProblem(quadratic);
        write.close();

        DatabaseManager read = new DatabaseManager(true);
        Quadratic quadratic1 = (Quadratic) read.getProblemsArray()[0];
        read.deleteDatabase();
        read.close();

        assertEquals(quadratic, quadratic1);
    }

    @Test // Check if a quadratic problem is equal when written and read from database
    public void trigReadWrite() {
        DatabaseManager write = new DatabaseManager(true);
        RightAngleTrigonometric trigonometric = RightAngleTrigonometricProblemFactory.generateRightAngleTrigonometricProblem(1);
        write.insertProblem(trigonometric);
        write.close();

        DatabaseManager read = new DatabaseManager(true);
        RightAngleTrigonometric trigonometric1 = (RightAngleTrigonometric) read.getProblemsArray()[0];
        read.deleteDatabase();
        read.close();

        assertEquals(trigonometric, trigonometric1);
    }

    @Test // Check if a text problem is equal when written and read from database
    public void textReadWrite() {
        //Generate a Random String to use as Question
        String randomQuestion = new BigInteger(130, new Random()).toString(32);
        String randomAnswer = String.valueOf(Util.getRandomNumber(10000,10000));

        DatabaseManager write = new DatabaseManager(true);
        Text text = new Text(randomQuestion, randomAnswer);
        write.insertProblem(text);
        write.close();

        DatabaseManager read = new DatabaseManager(true);
        Text text1 = (Text) read.getProblemsArray()[0];
        read.deleteDatabase();
        read.close();

        assertEquals(text, text1);
    }
}
