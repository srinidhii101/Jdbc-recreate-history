package com.dal.history;

import com.dal.models.ProductHistory;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface IGetInfo {

    List<String> getListOfProducts() throws SQLException, ClassNotFoundException;

    double getMaximumInventoryStorage(String productCode) throws SQLException, ClassNotFoundException;

    Integer getCurrentStockLevel(String productCode) throws SQLException, ClassNotFoundException;

    Integer getReorderLevel(String productCode) throws SQLException, ClassNotFoundException;

    List<ProductHistory> getProductHistory(String productCode) throws SQLException, ClassNotFoundException, ParseException;

    double getReorderHistory(List<ProductHistory> productHistories, Integer maxInventoryStorage, Integer reorderLevel, Integer currentStockLevel) throws SQLException, ClassNotFoundException, ParseException;

}
