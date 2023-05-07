package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.monopoly.gamelogic.Dices;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

public class DicesTest {

    static Random random;
    static final int RANDOM_TEST_SIZE = 50;

    @BeforeAll
    public static void initRandom(){
        random = new Random();
    }

    @Test
    public void testCreateDices(){
        Dices dices = new Dices();
        assertNotNull(dices);
    }

    @Test
    public void testDiceRollFlawed(){
        Dices dices = new Dices();

        for(int i = 0; i < RANDOM_TEST_SIZE; i++){
            int sum = random.nextInt(11)+2;
            dices.rollDicesFlawed(sum);
            assertEquals(sum, dices.getDice1()+ dices.getDice2());
            if(sum % 2 == 0) {
                assertEquals(dices.getDice1(), dices.getDice2());
                assertEquals(dices.getDice1(), sum / 2);
            }
        }
    }

    @Test
    public void testDiceRoll(){
        Dices dices = new Dices();
        Random r = mock(Random.class);
        when(r.nextInt(anyInt())).then(new Answer<Integer>() {
            int count = 0;
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return count++ == 0 ? 2 : 3;
            }
        });
        dices.setRandom(r);
        dices.rollDices();
        assertFalse(dices.isLastRollFlawed());
        assertEquals(3, dices.getDice1());
        assertEquals(4, dices.getDice2());
    }

    @Test
    public void invalidSum(){
        Dices dices = new Dices();
        assertThrows(IllegalArgumentException.class, () -> {dices.rollDicesFlawed(100);});
    }

    @Test
    public void testSum(){
        Dices dices = new Dices();

        for(int i = 0; i < RANDOM_TEST_SIZE; i++){
            dices.rollDices();
            assertEquals(dices.getDice1() + dices.getDice2(), dices.getSum());
        }
    }
}
