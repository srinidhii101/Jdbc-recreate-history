package com.dal.history;

import com.dal.models.ProductHistory;
import com.dal.models.ReorderHistoryTransaction;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface IGetInfo {

    List<String> getListOfProducts() throws SQLException, ClassNotFoundException;

    Integer getMaximumInventoryStorage(String productCode) throws SQLException, ClassNotFoundException;

    Integer getCurrentStockLevel(String productCode) throws SQLException, ClassNotFoundException;

    Integer getReorderLevel(String productCode) throws SQLException, ClassNotFoundException;

    List<ProductHistory> getProductHistory(String productCode) throws SQLException, ClassNotFoundException, ParseException;

    double getReorderHistory(List<ProductHistory> productHistories, Integer maxInventoryStorage, Integer reorderLevel, Integer currentStockLevel, String productCode) throws SQLException, ClassNotFoundException, ParseException;

    double getUnitPrice(ReorderHistoryTransaction reorderHistoryTransaction, String productCode) throws SQLException, ClassNotFoundException;

}
