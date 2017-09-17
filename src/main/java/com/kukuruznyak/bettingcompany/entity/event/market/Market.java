package com.kukuruznyak.bettingcompany.entity.event.market;

import com.kukuruznyak.bettingcompany.entity.enums.MarketNames;

import java.util.HashSet;
import java.util.Set;

public class Market {
    private MarketNames name;
    private Set<MarketItem> marketItems;
    private boolean isChangeable = true;
    private int numberOfItems;

    public MarketNames getName() {
        return name;
    }

    public void setName(MarketNames name) {
        this.name = name;
    }

    public Set<MarketItem> getMarketItems() {
        return marketItems;
    }

    public void addItem(MarketItem marketItem) {
        if(this.marketItems.contains(marketItem)){
            return;
        }
        this.marketItems.add(marketItem);
        this.numberOfItems++;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setChangeable(boolean isChangeable) {
        isChangeable = isChangeable;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
