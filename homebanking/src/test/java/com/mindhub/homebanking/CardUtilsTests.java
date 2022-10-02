package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardNumberLong(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,hasLength(19));
    }


    @Test
    public void cardCVVIsLonger(){
        int cvv = CardUtils.getCVV();
        assertThat(cvv,greaterThan(99));
    }

    @Test
    public void cardCVVShorter(){
        int cvv = CardUtils.getCVV();
        assertThat(cvv,lessThan(1000));
    }
}
