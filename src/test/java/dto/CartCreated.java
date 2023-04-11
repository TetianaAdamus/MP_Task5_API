package dto;

import io.cucumber.java.it.Data;

import java.util.ArrayList;

public class CartCreated {

    public String type, cartType, code, priceType, formattedValue, guid;
    public Double value;
    public ArrayList<Object> entries;
    public Integer gwpQuantity, payWithPoints, pointTotalAfterOrder, redeemLoyaltyPoints, totalItems;
    public Price gwpTotalPrice, totalPrice, totalPriceWithTax;
    public boolean inapplicableVipRewardsRemoved;

    public CartCreated() {
    }

    public CartCreated(String type, String cartType, String code, String priceType,
            String formattedValue, String guid, Double value, ArrayList<Object> entries,
            Integer gwpQuantity, Integer payWithPoints, Integer pointTotalAfterOrder, Integer redeemLoyaltyPoints,
            Integer totalItems, Price gwpTotalPrice, Price totalPrice, Price totalPriceWithTax,
            boolean inapplicableVipRewardsRemoved) {
        this.type = type;
        this.cartType = cartType;
        this.code = code;
        this.priceType = priceType;
        this.formattedValue = formattedValue;
        this.guid = guid;
        this.value = value;
        this.entries = entries;
        this.gwpQuantity = gwpQuantity;
        this.payWithPoints = payWithPoints;
        this.pointTotalAfterOrder = pointTotalAfterOrder;
        this.redeemLoyaltyPoints = redeemLoyaltyPoints;
        this.totalItems = totalItems;
        this.gwpTotalPrice = gwpTotalPrice;
        this.totalPrice = totalPrice;
        this.totalPriceWithTax = totalPriceWithTax;
        this.inapplicableVipRewardsRemoved = inapplicableVipRewardsRemoved;
    }

    public String getGuid() {
        return guid;
    }
}
