import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void createNewRestaurant() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        LocalTime CloseTime =restaurant.closingTime;
        LocalTime OpenTime = restaurant.openingTime;
        LocalTime testTime = restaurant.closingTime.minusMinutes(60);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(testTime,CloseTime,OpenTime);

        assertTrue(spiedRestaurant.isRestaurantOpen());
        assertTrue(spiedRestaurant.isRestaurantOpen());
        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        LocalTime beforeOpenTime = restaurant.openingTime.minusMinutes(30);
        LocalTime afterCloseTime = restaurant.closingTime.plusMinutes(30);

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(beforeOpenTime,afterCloseTime);

        assertFalse(spiedRestaurant.isRestaurantOpen());
        assertFalse(spiedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //<<<<<<<<<<<<<<<<<<<<<<<<ORDER SUM>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void order_sum_should_return_sum_of_price_of_selected_items() {
        List<String> itemNames = new ArrayList<String>();
        itemNames.add("Sweet corn soup");
        assertEquals(119,restaurant.getOrderValue(itemNames));

        itemNames.add("Vegetable lasagne");
        assertEquals(388,restaurant.getOrderValue(itemNames));
    }

    @Test
    public void order_sum_should_return_0_when_no_items_selected() {
        List<String> itemNames = new ArrayList<String>();
        assertEquals(0,restaurant.getOrderValue(itemNames));
    }
    //<<<<<<<<<<<<<<<<<<<<<<ORDER SUM>>>>>>>>>>>>>>>>>>>>>>>>>
}