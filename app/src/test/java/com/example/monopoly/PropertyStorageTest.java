package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.properties.IllegalFieldException;
import com.example.monopoly.gamelogic.properties.PropertyStorage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class PropertyStorageTest {
    static PropertyStorage propertyStorage;
    static private Player player;
    static private Player player2;

    @BeforeAll
    static void init() {
        propertyStorage = PropertyStorage.getInstance();
        player = mock(Player.class);
        player2 = mock(Player.class);
    }

    @BeforeEach
    void reset() throws NoSuchFieldException, IllegalAccessException {
        // reset PropertyStorage using reflection
        Field instance = PropertyStorage.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        propertyStorage = PropertyStorage.getInstance();
        Mockito.reset(player, player2);
    }

    @Test
    void buyPropertyTest() {
        String propertyId = "baerenland";
        Player newOwner = mock(Player.class);

        propertyStorage.buyProperty(propertyId, newOwner);

        assertEquals(newOwner, propertyStorage.getOwner(propertyId));
    }

    @Test
    void buyPropertyWithWrongNameTest() {
        String propertyId = "abc";
        Player newOwner = mock(Player.class);

        assertThrows(IllegalFieldException.class, () -> {
            propertyStorage.buyProperty(propertyId, newOwner);
        });
    }

    @Test
    void addHouseToPropertyTest() {
        buyAllPropertiesOfSameColor();

        assertEquals(1, propertyStorage.addHouse("strandbad", player));
        assertEquals(2, propertyStorage.addHouse("strandbad", player));
        assertEquals(3, propertyStorage.addHouse("strandbad", player));
        assertEquals(4, propertyStorage.addHouse("strandbad", player));
        assertThrows(IllegalStateException.class, () -> {
            propertyStorage.addHouse("strandbad", player);
        });
    }

    @Test
    void addHouseToWrongFieldTest(){
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHouse("s_bahn_kaernten", player);});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHouse("kelag", player);});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHouse("abc", player);});
    }

    @Test
    void addHotelToPropertyFieldTest(){
        buyAllHousesOnProperty();

        assertDoesNotThrow(() -> {propertyStorage.addHotel("strandbad", player);});
    }

    @Test
    void addHotelOnIllegalFieldTest(){
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad", player);});
        propertyStorage.buyProperty("strandbad", player);
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad", player);});
        propertyStorage.buyProperty("city_arkaden", player);
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad", player);});
        propertyStorage.addHouse("strandbad", player);
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad", player);});
        propertyStorage.addHouse("strandbad", player);
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad", player);});
        propertyStorage.addHouse("strandbad", player);
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad", player);});
        propertyStorage.addHouse("strandbad", player);
        assertDoesNotThrow(() -> {propertyStorage.addHotel("strandbad", player);});

        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHotel("kelag", player);});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHotel("s_bahn_tirol", player);});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHotel("sas", player);});
    }

    @Test
    void rentOnUnownedFieldTest(){
        assertEquals(0, propertyStorage.getRentOnPropertyField("strandbad", player));
        assertEquals(0, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void rentOnOwnedFieldTest(){
        propertyStorage.buyProperty("strandbad", player);
        assertEquals(0, propertyStorage.getRentOnPropertyField("strandbad", player));
        assertEquals(2, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void rentOnAllColorsFieldTest(){
        buyAllPropertiesOfSameColor();
        assertEquals(4, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void rentWithHousesTest(){
        buyAllPropertiesOfSameColor();
        propertyStorage.addHouse("strandbad", player);
        assertEquals(10, propertyStorage.getRentOnPropertyField("strandbad", player2));
        propertyStorage.addHouse("strandbad", player);
        assertEquals(30, propertyStorage.getRentOnPropertyField("strandbad", player2));
        propertyStorage.addHouse("strandbad", player);
        assertEquals(90, propertyStorage.getRentOnPropertyField("strandbad", player2));
        propertyStorage.addHouse("strandbad", player);
        assertEquals(160, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void rentWithHotelTest(){
        buyHotelOnProperty();
        assertEquals(250, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void rentOnTrainStationTest(){
        assertEquals(0, propertyStorage.getRentOnPropertyField("s_bahn_tirol", player2));
        propertyStorage.buyProperty("s_bahn_tirol", player);
        assertEquals(25, propertyStorage.getRentOnPropertyField("s_bahn_tirol", player2));
        propertyStorage.buyProperty("s_bahn_wien", player);
        assertEquals(50, propertyStorage.getRentOnPropertyField("s_bahn_tirol", player2));
        propertyStorage.buyProperty("s_bahn_kaernten", player);
        assertEquals(100, propertyStorage.getRentOnPropertyField("s_bahn_tirol", player2));
        propertyStorage.buyProperty("s_bahn_steiermark", player);
        assertEquals(200, propertyStorage.getRentOnPropertyField("s_bahn_tirol", player2));
    }

    @Test
    void rentOnIllegalFieldTest(){
        propertyStorage.buyProperty("kelag", player);
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.getRentOnPropertyField("kelag", player2);});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.getRentOnPropertyField("dad", player2);});
    }

    private void buyAllPropertiesOfSameColor() {
        propertyStorage.buyProperty("strandbad", player);
        propertyStorage.buyProperty("city_arkaden", player);
    }

    private void buyHotelOnProperty(){
        buyAllHousesOnProperty();
        propertyStorage.addHotel("strandbad", player);
    }

    private void buyAllHousesOnProperty(){
        buyAllPropertiesOfSameColor();
        for(int i = 0; i < 4; i++)
            propertyStorage.addHouse("strandbad", player);
    }
}