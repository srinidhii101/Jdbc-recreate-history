package com.dal.inventory.control;

import com.dal.history.GetInfo;
import com.dal.jdbc.JDBCConnector;
import com.dal.jdbc.JdbcConfig;
import com.dal.models.ProductHistory;
import com.dal.models.ReorderHistory;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class InventoryControl implements InventoryControlInterface {

    JdbcConfig jdbcConfig;
    JDBCConnector jdbcConnector;

    public InventoryControl() {
        this.jdbcConfig = new JdbcConfig();
        this.jdbcConnector = new JDBCConnector();
    }

    @Override
    public void Ship_order(int orderNumber) throws OrderException {
        if (isShipped(orderNumber)) {
            System.out.println(" Order has not been shipped yet! Please recheck after some time. ");
        } else {
            System.out.println(" Order has been shipped. ");
        }
    }

    @Override
    public int Issue_reorders(int year, int month, int day) throws SQLException, ClassNotFoundException, ParseException {
        GetInfo getInfo = new GetInfo();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date target = simpleDateFormat.parse(year + "-" + month + "-" + day);
        List<String> listOfSuppliers = getInfo.getListOfSuppliers();
        for (String supplier : listOfSuppliers) {
            System.out.println("\nSupplier Id = " + supplier);
            List<String> productList = getInfo.getListOfProductsForSuppliers(supplier);
            for (int i = 0; i < productList.size(); i++) {
                List<ReorderHistory> listOfReorderHistories = new ArrayList<>();
                System.out.println("\nProduct Code = " + productList.get(i) + "\n");
                String productCode = productList.get(i);
                List<ProductHistory> productHistories = getInfo.getProductHistory(productCode);
                Integer currentStockLevel = getInfo.getCurrentStockLevel(productCode);
                Integer reorderLevel = getInfo.getReorderLevel(productCode);
                Integer maxInventoryStorage = getInfo.getMaximumInventoryStorage(productCode);
                if (maxInventoryStorage < reorderLevel) {
                    System.out.println("Unable to compute history since Re-Order Levels are higher than Max Inventory Storage");
                    continue;
                }
                getInfo.getReorderHistory(productHistories, maxInventoryStorage, reorderLevel, currentStockLevel, productCode);
                for (ReorderHistory reorderHistory : getInfo.reorderHistories) {
                    if (reorderHistory.getReorderDate().equals(target)) {
                        listOfReorderHistories.add(reorderHistory);
                    }
                }
                if (listOfReorderHistories.size() > 0) {
                    System.out.println("ReorderDate\tUnitPrice\tReorderUnits\tTotalPrice");
                    for (ReorderHistory reorderHistory : listOfReorderHistories) {
                        System.out.println(simpleDateFormat.format(reorderHistory.reorderDate) + "\t" + reorderHistory.unitPrice.shortValue() + "\t\t\t" + reorderHistory.reorderUnits + "\t\t\t\t" + reorderHistory.totalPrice.shortValue());
                    }
                } else {
                    System.out.println("No Supplies Required for this product");
                }

            }
        }
        return 0;
    }

    @Override
    public void Receive_order(int internal_order_reference) throws OrderException {
        if (isArrived(internal_order_reference)) {
            System.out.println(" Order has not arrived yet! Please recheck after some time. ");
        } else {
            System.out.println(" Order has arrived. ");
        }
    }

    @Override
    public boolean isShipped(int orderNumber) throws OrderException {
        try {
            Integer isShipped = 0;
            Connection connection = jdbcConnector.connectionProvider(jdbcConfig);

            Statement useDatabaseStatement = connection.createStatement();
            useDatabaseStatement.executeQuery("USE " + jdbcConfig.getDatabase());

            Statement productInformationStatement = connection.createStatement();
            ResultSet productInformationResults = productInformationStatement.executeQuery("SELECT class_3901.orders.ShippedDate, IF(class_3901.orders.ShippedDate IS NULL, 1 , 0)\n" +
                    "from class_3901.orders\n" +
                    "where class_3901.orders.OrderID = " + orderNumber + ";");

            ResultSetMetaData resultSetMetaData = productInformationResults.getMetaData();
            while (productInformationResults.next()) {
                if (resultSetMetaData.getColumnCount() == 1) {
                    isShipped = (Integer) productInformationResults.getObject(1);
                }
            }
            connection.close();
            return isShipped != 1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new OrderException();
        }
    }

    @Override
    public boolean isArrived(int orderNumber) throws OrderException {
        try {
            Integer isShipped = 0;
            Connection connection = jdbcConnector.connectionProvider(jdbcConfig);

            Statement useDatabaseStatement = connection.createStatement();
            useDatabaseStatement.executeQuery("USE " + jdbcConfig.getDatabase());

            Statement productInformationStatement = connection.createStatement();
            ResultSet productInformationResults = productInformationStatement.executeQuery("SELECT class_3901.InventoryOrders.ArrivedDate , IF(class_3901.InventoryOrders.ArrivedDate IS NULL, 1 , 0)\n" +
                    "FROM class_3901.InventoryOrders\n" +
                    "where class_3901.InventoryOrders.ReferenceNumber = " + orderNumber + ";");

            ResultSetMetaData resultSetMetaData = productInformationResults.getMetaData();
            while (productInformationResults.next()) {
                if (resultSetMetaData.getColumnCount() == 1) {
                    isShipped = (Integer) productInformationResults.getObject(1);
                }
            }
            connection.close();
            return isShipped != 1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new OrderException();
        }
    }

}
