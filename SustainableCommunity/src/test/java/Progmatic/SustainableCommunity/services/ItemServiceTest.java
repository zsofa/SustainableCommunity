package Progmatic.SustainableCommunity.services;

import Progmatic.SustainableCommunity.models.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @AfterEach
    void tearDown() {
    }


    @Test
    void moneySaved() {
        Item test = new Item();
        test.setBorrowPrice(500);
        test.setItemValue(35000);

        assertEquals(34500, itemService.moneySaved(test));
    }

    @Test
    void spaceSaved() {

    }
}