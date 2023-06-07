package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.properties.ClientPropertyStorage;
import com.example.monopoly.gamelogic.properties.Field;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;


public class ClientPropertyStorageTest {
    static ClientPropertyStorage clientPropertyStorage;
    static Player player;

    @BeforeAll
    static void init(){
        clientPropertyStorage = ClientPropertyStorage.getInstance();
        player = mock(Player.class);
    }

    @BeforeEach
    void reset() throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field instance = ClientPropertyStorage.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        clientPropertyStorage = ClientPropertyStorage.getInstance();
        Mockito.reset(player);
    }

    @Test
    void updateOwnerTest(){
        clientPropertyStorage.updateOwner("strandbad", player);
        for(Field field : clientPropertyStorage.getPropertyList()){
            if(field.getName().equals("strandbad")){
                assertEquals(player, field.getOwner());
                break;
            }
        }
    }

    @Test
    void getPropertyListTest(){
        List<Field> properties = clientPropertyStorage.getPropertyList();
        int randomFieldIndex = 10;
        properties.get(randomFieldIndex).setOwner(player);
        assertNotNull(clientPropertyStorage.getPropertyList().get(randomFieldIndex).getOwner());
    }
}
