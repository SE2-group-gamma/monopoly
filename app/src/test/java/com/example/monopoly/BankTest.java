package com.example.monopoly;
import org.junit.jupiter.api.Test;

import android.graphics.Color;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.monopoly.gamelogic.Bank;
import com.example.monopoly.gamelogic.Player;

import org.junit.jupiter.api.Test;

public class BankTest {
    static Bank bank;
    static Player player;
    static Color col;
    @BeforeEach
    void setUp(){
        col=mock(Color.class);
        bank = new Bank();
        player = new Player("Dummy",col,1000.00,true);
    }

    @Test
    public void testTransferMoneyToPlayer(){
        bank.transferMoneyBankToPlayer(player, bank, 200);
        // player increased by $200
        assertEquals(1200, player.getCapital(), 0.0);
        //bank decreased by $200
        assertEquals(499800, bank.getCash(), 0.0);
    }

    @Test
    public void testTransferMoneyToBank() {
        bank.transferMoneyPlayerToBank(player, bank, 200);

        // player decreased by $200
        assertEquals(800, player.getCapital(), 0.0);

        //bank increased by $200
        assertEquals(500200, bank.getCash(), 0.0);
    }

    @Test
    public void savingBank(){

        // Save the bank with 50000.00
        bank.savingBank(bank);

        // bank cash is now 50000.00
        assertEquals(50000, bank.getCash(), 0.0);
    }
}
