package test;

import model.Quadratic;
import model.Text;
import module.Util;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AnswerTest {

    @Test
    public void quadraticAnswerTest() {
        //Check various cases for a known question
        Quadratic quadratic = new Quadratic(1, 4, -285);
        assertEquals(true, quadratic.checkAnswer("15,-19"));
        assertEquals(true, quadratic.checkAnswer("-19,15"));
        assertEquals(true, quadratic.checkAnswer("15.0,-19"));
        assertEquals(true, quadratic.checkAnswer("   15.0,  -19.0   "));
        assertEquals(false, quadratic.checkAnswer("   15.0,  -19.1   "));
    }

}
