package com.dal.models;

import java.util.Date;

public class ProductHistory {
    public Date dateOfPurchase;
    public Integer noOfProducts;

    public ProductHistory(Date dateOfPurchase, Integer noOfProducts) {
        this.dateOfPurchase = dateOfPurchase;
        this.noOfProducts = noOfProducts;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Integer getNoOfProducts() {
        return noOfProducts;
    }

    public void setNoOfProducts(Integer noOfProducts) {
        this.noOfProducts = noOfProducts;
    }
}
