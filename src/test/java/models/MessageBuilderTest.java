package models;

import models.MessageBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBuilderTest {

    @Test
    public void testNameMkyong() {

        MessageBuilder obj = new MessageBuilder();
        assertEquals("Hello mkyong", obj.getMessage("mkyong"));

    }

}