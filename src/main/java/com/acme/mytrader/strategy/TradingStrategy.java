package com.acme.mytrader.strategy;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy {

    private double buyPrice;
    private int buyVolume;
    private double sellPrice;
    private int sellVolume;

    /** Define the prices stocks should be bought and sold at as well as the volumes that should be bought and sold
     *  Assumes the strategy is the same for each stock - in reality we would probably have different strategies
     * @param buyPrice
     * @param buyVolume
     * @param sellPrice
     * @param sellVolume
     */
    public TradingStrategy(double buyPrice, int buyVolume, double sellPrice, int sellVolume) {
        this.buyPrice = buyPrice;
        this.buyVolume = buyVolume;
        this.sellPrice = sellPrice;
        this.sellVolume = sellVolume;
    }


    public double getBuyPrice() {
        return buyPrice;
    }

    public int getBuyVolume() {
        return buyVolume;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public int getSellVolume() {
        return sellVolume;
    }

}
