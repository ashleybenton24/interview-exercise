package com.acme.mytrader.strategy;

import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceListenerImpl;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

public class TradingStrategyTest {

    @Test
    public void testNothing() {
    }


    //Not really familiar with mockito
    //Aim is to test buy and sells
    //Creating answer objects that return whether or not we should be buying or selling based on trading strategy
    @Test
    public void testListener() {
        TradingStrategy strategy = new TradingStrategy(55, 100, 125, 50);
        PriceListener listener = mock(PriceListenerImpl.class);
        //return true if we want to buy
        Answer<Boolean> answerBuy = new Answer<Boolean>() {
            public Boolean answer(InvocationOnMock invocation) {
                String security = invocation.getArgument(0, String.class);
                Double price = invocation.getArgument(1, Double.class);
                if (price < strategy.getBuyPrice())
                    return true;
                else
                    return false;
            }
        };
        //return true if we want to sell
        Answer<Boolean> answerSell = new Answer<Boolean>() {
            public Boolean answer(InvocationOnMock invocation) {
                String security = invocation.getArgument(0, String.class);
                Double price = invocation.getArgument(1, Double.class);
                if (price > strategy.getBuyPrice())
                    return true;
                else
                    return false;
            }
        };

        //should return true?
        doAnswer(answerBuy).when(listener).priceUpdate("IBM", 40);

        //should return true?
        doAnswer(answerSell).when(listener).priceUpdate("IBM", 140);

    }
}
