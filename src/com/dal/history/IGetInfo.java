package com.dal.history;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IGetInfo {

    List<String> getListOfProducts() throws SQLException, ClassNotFoundException;

    double getMaximumInventoryStorage(String productCode) throws SQLException, ClassNotFoundException;

    Integer getCurrentStockLevel(String productCode) throws SQLException, ClassNotFoundException;

    Integer getReorderLevel(String productCode) throws SQLException, ClassNotFoundException;

    Map<String, String> getProductHistory(String productCode) throws SQLException, ClassNotFoundException;

}
