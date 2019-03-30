package com.dal.models;

import java.util.Date;

public class ReorderHistoryTransaction {
    public Date transactionDate;
    public Integer dayEndInventory;
    public Integer reorderUnits;
    public Integer salesDayEndInventory;
    public Integer daySales;
    public Integer dayStartInventory;

    public ReorderHistoryTransaction(Date transactionDate, Integer dayEndInventory, Integer reorderUnits, Integer salesDayEndInventory, Integer daySales, Integer dayStartInventory) {
        this.transactionDate = transactionDate;
        this.dayEndInventory = dayEndInventory;
        this.reorderUnits = reorderUnits;
        this.salesDayEndInventory = salesDayEndInventory;
        this.daySales = daySales;
        this.dayStartInventory = dayStartInventory;
    }

    public ReorderHistoryTransaction(){

    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getDayEndInventory() {
        return dayEndInventory;
    }

    public void setDayEndInventory(Integer dayEndInventory) {
        this.dayEndInventory = dayEndInventory;
    }

    public Integer getReorderUnits() {
        return reorderUnits;
    }

    public void setReorderUnits(Integer reorderUnits) {
        this.reorderUnits = reorderUnits;
    }

    public Integer getSalesDayEndInventory() {
        return salesDayEndInventory;
    }

    public void setSalesDayEndInventory(Integer salesDayEndInventory) {
        this.salesDayEndInventory = salesDayEndInventory;
    }

    public Integer getDaySales() {
        return daySales;
    }

    public void setDaySales(Integer daySales) {
        this.daySales = daySales;
    }

    public Integer getDayStartInventory() {
        return dayStartInventory;
    }

    public void setDayStartInventory(Integer dayStartInventory) {
        this.dayStartInventory = dayStartInventory;
    }
}
