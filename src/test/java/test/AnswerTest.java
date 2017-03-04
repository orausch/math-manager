package test;

import model.Quadratic;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnswerTest {

    /**
     * Check that the checkAnswer function of the Quadratic model works with various known cases.
     */
    @Test
    public void quadraticAnswerTest() {
        Quadratic quadratic = new Quadratic(1, 4, -285);
        assertEquals(true, quadratic.checkAnswer("15,-19"));
        assertEquals(true, quadratic.checkAnswer("-19,15"));
        assertEquals(true, quadratic.checkAnswer("15.0,-19"));
        assertEquals(true, quadratic.checkAnswer("   15.0,  -19.0   "));
        assertEquals(false, quadratic.checkAnswer("   15.0,  -19.1   "));
    }

}
