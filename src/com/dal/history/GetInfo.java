package com.dal.history;


import com.dal.jdbc.JDBCConnector;
import com.dal.jdbc.JdbcConfig;
import com.dal.models.ProductHistory;
import com.dal.models.ReorderHistoryTransaction;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<ProductHistory> getProductHistory(String productCode) throws SQLException, ClassNotFoundException, ParseException {
        List<ProductHistory> productHistory = new ArrayList<>();
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
            Date dateValue = new SimpleDateFormat("yyyy-MM-dd").parse(productHistoryResults.getString(1));
            int orderValue = Integer.parseInt(productHistoryResults.getString(2));
            productHistory.add(new ProductHistory(dateValue, orderValue));
        }
        return productHistory;
    }

    @Override
    public double getReorderHistory(List<ProductHistory> productHistories, Integer maxInventoryStorage, Integer reorderLevel, Integer currentStockLevel) throws SQLException, ClassNotFoundException, ParseException {
        List<ReorderHistoryTransaction> reorderHistoryTransactions = new ArrayList<>();

        ProductHistory p1 = new ProductHistory(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"), 2);
        ProductHistory p2 = new ProductHistory(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-02"), 3);
        ProductHistory p3 = new ProductHistory(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-03"), 2);
        ProductHistory p4 = new ProductHistory(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-04"), 3);
        ProductHistory p5 = new ProductHistory(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-05"), 2);
        ProductHistory p6 = new ProductHistory(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-06"), 2);
        ProductHistory p7 = new ProductHistory(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-07"), 1);

        List<ProductHistory> tempList = new ArrayList<>();
        tempList.add(p7);
        tempList.add(p6);
        tempList.add(p5);
        tempList.add(p4);
        tempList.add(p3);
        tempList.add(p2);
        tempList.add(p1);

        productHistories = tempList;

        // Setting Initial Rows
        ReorderHistoryTransaction initialTransaction = new ReorderHistoryTransaction();
        initialTransaction.setTransactionDate(productHistories.get(0).getDateOfPurchase());
        initialTransaction.setDayEndInventory(currentStockLevel);
        initialTransaction.setReorderUnits(0);
        initialTransaction.setSalesDayEndInventory(currentStockLevel);
        initialTransaction.setDaySales(productHistories.get(0).getNoOfProducts());
        initialTransaction.setDayStartInventory(initialTransaction.salesDayEndInventory + initialTransaction.daySales);
        reorderHistoryTransactions.add(initialTransaction);

        for (int i = 1; i < productHistories.size(); i++) {
            ReorderHistoryTransaction reorderHistoryTransaction = new ReorderHistoryTransaction();
            reorderHistoryTransaction.setTransactionDate(productHistories.get(i).getDateOfPurchase());
            reorderHistoryTransaction.setDayEndInventory(reorderHistoryTransactions.get(i - 1).getDayStartInventory());
            reorderHistoryTransaction.setDaySales(productHistories.get(i).getNoOfProducts());
            if (reorderHistoryTransactions.get(i - 1).getDayStartInventory() >= maxInventoryStorage) {
                Integer noOfReorderUnits = reorderHistoryTransactions.get(i -1).getDayStartInventory() - reorderLevel;
                reorderHistoryTransaction.setReorderUnits(noOfReorderUnits);
                reorderHistoryTransaction.setSalesDayEndInventory(reorderHistoryTransactions.get(i - 1).getDayEndInventory() - noOfReorderUnits);
            } else {
                reorderHistoryTransaction.setSalesDayEndInventory(reorderHistoryTransactions.get(i - 1).getDayStartInventory());
                reorderHistoryTransaction.setReorderUnits(0);
            }
            reorderHistoryTransaction.setDayStartInventory(reorderHistoryTransaction.salesDayEndInventory + reorderHistoryTransaction.daySales);
            reorderHistoryTransactions.add(reorderHistoryTransaction);
        }
        return 0;
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
                unitsOnOrderLevel = (Integer) unitsOnOrderInformationResults.getObject(1);
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
                currentStockLevel = (Integer) stockLevelInformationResults.getObject(1);
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
                reorderLevel = (Integer) reorderInformationResults.getObject(1);
            }
        }
        return reorderLevel;
    }
}
