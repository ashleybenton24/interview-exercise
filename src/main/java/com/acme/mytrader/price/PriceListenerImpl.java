package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.strategy.TradingStrategy;

import java.util.HashMap;
import java.util.Map;

public class PriceListenerImpl implements PriceListener {

    private PriceSource priceSource;
    private ExecutionService executionService;
    private TradingStrategy tradingStrategy;
    private Map<String, Double> priceTracker;

    /** Connect the listener to the specified source.
     *
     * @param priceSource
     * @param executionService
     * @param tradingStrategy
     */
    public PriceListenerImpl(PriceSource priceSource, ExecutionService executionService, TradingStrategy tradingStrategy) {
        this.priceSource = priceSource;
        this.executionService = executionService;
        this.tradingStrategy = tradingStrategy;
        this.priceTracker = new HashMap<>();
        connectToSource();
    }

    //I'm assuming security is the stock we are interested in?
    //Using TradingStrategy as a definition store of what the buy and sell prices should be
    @Override
    public void priceUpdate(String security, double price) {
        //keep track of price
        if (!priceTracker.containsKey(security)) {
            priceTracker.put(security, price);
        }
        else {
            //only want to buy if price is trending downwards
            if (price < priceTracker.get(security)) {
                makeCallToBuy(security, price);
            }
            //only want to sell if price is trending upwards
            else if (price > priceTracker.get(security)) {
                makeCallToSell(security, price);
            }

            //update priceTracker with most recent price
            //in reality we might want to keep track of the last few prices
            priceTracker.replace(security, price);
        }
    }

    //connect to the PriceSource
    private void connectToSource() {
        if (priceSource != null) //avoid NPE, maybe we want to throw an exception here if there is no source
            priceSource.addPriceListener(this);
    }

    //if price is lower than the buy price defined by TradingStrategy then make call to execution service to buy
    private void makeCallToBuy(String security, double price) {
        if (price < tradingStrategy.getBuyPrice()) {
            executionService.buy(security, price, tradingStrategy.getBuyVolume());
        }
    }

    //if price is higher than the sell price defined by TradingStrategy then make call to execution service to sell
    private void makeCallToSell(String security, double price) {
        if (price > tradingStrategy.getSellPrice()) {
            executionService.sell(security, price, tradingStrategy.getSellVolume());
        }
    }



}
