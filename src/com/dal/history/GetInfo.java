package com.dal.history;


import com.dal.jdbc.JDBCConnector;
import com.dal.jdbc.JdbcConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetInfo implements IGetInfo {

    JdbcConfig jdbcConfig;
    JDBCConnector jdbcConnector;

    public GetInfo() {
        this.jdbcConfig = new JdbcConfig();
        this.jdbcConnector = new JDBCConnector();
    }


    private void printResultSet(ResultSet productInformationResults, ResultSetMetaData rsmd, int columnsNumber) throws SQLException {
        while (productInformationResults.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = productInformationResults.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println();
        }
    }

    @Override
    public Map<String, String> getProductHistory(String productCode) throws SQLException, ClassNotFoundException {
        Map<String, String> productHistory = new HashMap<>();
        Connection connection = jdbcConnector.connectionProvider(jdbcConfig);
        Statement useDatabaseStatement = connection.createStatement();
        useDatabaseStatement.executeQuery("use " + jdbcConfig.getDatabase() + ";");

        String productInfoQuery = "SELECT orders.OrderDate, sum(orderdetails.Quantity) AS TotalOrders\n " +
                "FROM class_3901.orders, class_3901.orderdetails\n" +
                "where orders.orderID = orderdetails.orderID\n" +
                "and orderdetails.productId = " + productCode + "\n" +
                "GROUP BY orders.OrderDate " +
                "ORDER BY orders.OrderDate DESC;";

        Statement productHistoryStatement = connection.createStatement();
        ResultSet productHistoryResults = productHistoryStatement.executeQuery(productInfoQuery);

        while (productHistoryResults.next()) {
            String dateValue = productHistoryResults.getString(1);
            String orderValue = productHistoryResults.getString(1);
            productHistory.put(dateValue, orderValue);
        }
        return productHistory;
    }


    @Override
    public List<String> getListOfProducts() throws SQLException, ClassNotFoundException {
        List<String> listOfProducts = new ArrayList<>();
        Connection connection = jdbcConnector.connectionProvider(jdbcConfig);

        Statement useDatabaseStatement = connection.createStatement();
        useDatabaseStatement.executeQuery("USE " + jdbcConfig.getDatabase());

        Statement productInformationStatement = connection.createStatement();
        ResultSet productInformationResults = productInformationStatement.executeQuery("Select products.ProductID from products;");

        ResultSetMetaData resultSetMetaData = productInformationResults.getMetaData();
        while (productInformationResults.next()) {
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                String columnValue = productInformationResults.getString(i);
                listOfProducts.add(columnValue);
            }
        }
        return listOfProducts;
    }

    public double getMaximumInventoryStorage(String productCode) throws SQLException, ClassNotFoundException {
        Integer unitsOnOrderLevel = 0;
        Connection connection = jdbcConnector.connectionProvider(jdbcConfig);

        Statement useDatabaseStatement = connection.createStatement();
        useDatabaseStatement.executeQuery("USE " + jdbcConfig.getDatabase());

        Statement unitsOnOrderInformationStatement = connection.createStatement();
        ResultSet unitsOnOrderInformationResults = unitsOnOrderInformationStatement.executeQuery("Select UnitsOnOrder from products where products.ProductID = " + productCode + ";");

        ResultSetMetaData resultSetMetaData = unitsOnOrderInformationResults.getMetaData();
        while (unitsOnOrderInformationResults.next()) {
            if (resultSetMetaData.getColumnCount() == 1) {
                unitsOnOrderLevel = (Integer) unitsOnOrderInformationResults.getObject(0);
            }
        }
        if (unitsOnOrderLevel != null && unitsOnOrderLevel != 0) return unitsOnOrderLevel;
        else if (unitsOnOrderLevel == 0) return (4 * getReorderLevel(productCode));
        else {
            if (unitsOnOrderLevel == 0) {
                getReorderLevel(productCode);
            }
        }
        return 0;
    }

    @Override
    public Integer getCurrentStockLevel(String productCode) throws SQLException, ClassNotFoundException {
        Integer currentStockLevel = 0;
        Connection connection = jdbcConnector.connectionProvider(jdbcConfig);

        Statement useDatabaseStatement = connection.createStatement();
        useDatabaseStatement.executeQuery("USE " + jdbcConfig.getDatabase());

        Statement stockLevelInformationStatement = connection.createStatement();
        ResultSet stockLevelInformationResults = stockLevelInformationStatement.executeQuery("Select UnitsInStock from products where products.ProductID = " + productCode + ";");

        ResultSetMetaData resultSetMetaData = stockLevelInformationResults.getMetaData();
        while (stockLevelInformationResults.next()) {
            if (resultSetMetaData.getColumnCount() == 1) {
                currentStockLevel = (Integer) stockLevelInformationResults.getObject(0);
            }
        }
        return currentStockLevel;
    }

    @Override
    public Integer getReorderLevel(String productCode) throws SQLException, ClassNotFoundException {
        Integer reorderLevel = 0;
        Connection connection = jdbcConnector.connectionProvider(jdbcConfig);

        Statement useDatabaseStatement = connection.createStatement();
        useDatabaseStatement.executeQuery("USE " + jdbcConfig.getDatabase());

        Statement reorderInformationStatement = connection.createStatement();
        ResultSet reorderInformationResults = reorderInformationStatement.executeQuery("Select ReorderLevel from products where products.ProductID = " + productCode + ";");

        ResultSetMetaData resultSetMetaData = reorderInformationResults.getMetaData();
        while (reorderInformationResults.next()) {
            if (resultSetMetaData.getColumnCount() == 1) {
                reorderLevel = (Integer) reorderInformationResults.getObject(0);
            }
        }
        return reorderLevel;
    }
}
