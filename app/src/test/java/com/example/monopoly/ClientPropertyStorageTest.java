package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.properties.ClientPropertyStorage;
import com.example.monopoly.gamelogic.properties.Field;
import com.example.monopoly.gamelogic.properties.IllegalFieldException;
import com.example.monopoly.gamelogic.properties.PropertyField;

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

    @Test
    void getPropertyTest(){
        assertEquals("strandbad", clientPropertyStorage.getProperty("strandbad").getName());
        assertThrows(IllegalFieldException.class, () -> {clientPropertyStorage.getProperty("ads");});
    }

    @Test
    void addHouseTest(){
        clientPropertyStorage.addHouse("strandbad");
        PropertyField strandbad = (PropertyField) clientPropertyStorage.getProperty("strandbad");
        assertEquals(1, strandbad.getNumOfHouses());
    }

    @Test
    void addHouseOnIllegalFieldTest(){
        assertThrows(IllegalFieldException.class, () -> {clientPropertyStorage.addHouse("abc");});
        assertThrows(IllegalFieldException.class, () -> {clientPropertyStorage.addHouse("s_bahn_tirol");});
    }

    @Test
    void addHotelTest(){
        clientPropertyStorage.addHouse("strandbad");
        clientPropertyStorage.addHouse("strandbad");
        clientPropertyStorage.addHouse("strandbad");
        clientPropertyStorage.addHouse("strandbad");
        clientPropertyStorage.addHotel("strandbad");
        PropertyField strandbad = (PropertyField) clientPropertyStorage.getProperty("strandbad");
        assertTrue(strandbad.hasHotel());
    }

    @Test
    void addHotelOnIllegalFieldTest(){
        assertThrows(IllegalStateException.class, () -> {clientPropertyStorage.addHotel("strandbad");});
        assertThrows(IllegalFieldException.class, () -> {clientPropertyStorage.addHotel("ad");});
        assertThrows(IllegalFieldException.class, () -> {clientPropertyStorage.addHotel("s_bahn_tirol");});
    }

}
