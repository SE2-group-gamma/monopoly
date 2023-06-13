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
    private Bank bank;
    private Player player;
    static Color col;
    private double expectedMoney,expectedBank,transfer;
    @BeforeEach
    public void setUp(){
        col=mock(Color.class);
        bank = Bank.getInstance();
        player = new Player("Dummy",col,1000.00,true);
        bank.setCash(500000);

    }

    @Test
    public void testTransferMoneyBankToPlayer(){
        bank.transferMoneyBankToPlayer(player, 200);
        // player increased by $200
        assertEquals(1200, player.getCapital(), 0.0);
        //bank decreased by $200
        assertEquals(499800, bank.getCash(), 0.0);
    }

    @Test
    public void testTransferMoneyPlayerToBank() {
        double transfer = 200.00;
        double PlayerMoney=player.getCapital();
        double BankMoney = bank.getCash();
        bank.transferMoneyPlayerToBank(player, transfer);

        double expectedPlayerMoney = PlayerMoney-transfer;
        // player decreased by $200
        assertEquals(expectedPlayerMoney, player.getCapital());

        double expectedBankMoney= BankMoney+transfer;
        //bank increased by $200
        assertEquals(expectedBankMoney, bank.getCash());
    }

    @Test
    public void savingBank(){

        // Save the bank with 50000.00
        bank.savingBank(bank);

        // bank cash is now 50000.00
        assertEquals(500000.00, bank.getCash(), 0.0);
    }
}
