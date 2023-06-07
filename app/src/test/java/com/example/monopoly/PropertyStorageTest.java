package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.properties.IllegalFieldException;
import com.example.monopoly.gamelogic.properties.PropertyStorage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.stream.Stream;

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

        assertEquals(1, propertyStorage.addHouse("strandbad"));
        assertEquals(2, propertyStorage.addHouse("strandbad"));
        assertEquals(3, propertyStorage.addHouse("strandbad"));
        assertEquals(4, propertyStorage.addHouse("strandbad"));
        assertThrows(IllegalStateException.class, () -> {
            propertyStorage.addHouse("strandbad");
        });
    }

    @Test
    void addHouseToWrongFieldTest(){
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHouse("s_bahn_kaernten");});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHouse("kelag");});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHouse("abc");});
    }

    @Test
    void addHotelToPropertyField(){
        buyAllHousesOnProperty();

        assertDoesNotThrow(() -> {propertyStorage.addHotel("strandbad");});
    }

    @Test
    void addHotelOnIllegalField(){
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad");});
        propertyStorage.buyProperty("strandbad", player);
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad");});
        propertyStorage.buyProperty("city_arkaden", player);
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad");});
        propertyStorage.addHouse("strandbad");
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad");});
        propertyStorage.addHouse("strandbad");
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad");});
        propertyStorage.addHouse("strandbad");
        assertThrows(IllegalStateException.class, () -> {propertyStorage.addHotel("strandbad");});
        propertyStorage.addHouse("strandbad");
        assertDoesNotThrow(() -> {propertyStorage.addHotel("strandbad");});

        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHotel("kelag");});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHotel("s_bahn_tirol");});
        assertThrows(IllegalFieldException.class, () -> {propertyStorage.addHotel("sas");});
    }

    @Test
    void testRentOnUnownedField(){
        assertEquals(0, propertyStorage.getRentOnPropertyField("strandbad", player));
        assertEquals(0, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void testRentOnOwnedField(){
        propertyStorage.buyProperty("strandbad", player);
        assertEquals(0, propertyStorage.getRentOnPropertyField("strandbad", player));
        assertEquals(2, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void testRentOnAllColorsField(){
        buyAllPropertiesOfSameColor();
        assertEquals(4, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void testRentWithHouses(){
        buyAllPropertiesOfSameColor();
        propertyStorage.addHouse("strandbad");
        assertEquals(10, propertyStorage.getRentOnPropertyField("strandbad", player2));
        propertyStorage.addHouse("strandbad");
        assertEquals(30, propertyStorage.getRentOnPropertyField("strandbad", player2));
        propertyStorage.addHouse("strandbad");
        assertEquals(90, propertyStorage.getRentOnPropertyField("strandbad", player2));
        propertyStorage.addHouse("strandbad");
        assertEquals(160, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    @Test
    void testRentWithHotel(){
        buyHotelOnProperty();
        assertEquals(250, propertyStorage.getRentOnPropertyField("strandbad", player2));
    }

    private void buyAllPropertiesOfSameColor() {
        propertyStorage.buyProperty("strandbad", player);
        propertyStorage.buyProperty("city_arkaden", player);
    }

    private void buyHotelOnProperty(){
        buyAllHousesOnProperty();
        propertyStorage.addHotel("strandbad");
    }

    private void buyAllHousesOnProperty(){
        buyAllPropertiesOfSameColor();
        for(int i = 0; i < 4; i++)
            propertyStorage.addHouse("strandbad");
    }
}