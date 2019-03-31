package com.dal;

import com.dal.history.GetInfo;
import com.dal.inventory.control.InventoryControl;
import com.dal.models.ProductHistory;
import com.dal.models.ReorderHistory;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {

        GetInfo getInfo = new GetInfo();
        List<String> products = getInfo.getListOfProducts();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

//        for (int i = 0; i < products.size(); i++) {
//            String productCode = products.get(i);
//            System.out.println();
//            System.out.println("Product Code = " + productCode);
//            List<ProductHistory> productHistories = getInfo.getProductHistory(productCode);
//            Integer currentStockLevel = getInfo.getCurrentStockLevel(productCode);
//            Integer reorderLevel = getInfo.getReorderLevel(productCode);
//            Integer maxInventoryStorage = getInfo.getMaximumInventoryStorage(productCode);
//            if (maxInventoryStorage < reorderLevel) {
//                System.out.println("Unable to compute history since Re-Order Levels are higher than Max Inventory Storage");
//                continue;
//            }
//            getInfo.getReorderHistory(productHistories, maxInventoryStorage, reorderLevel, currentStockLevel, productCode);
//            System.out.println("ReorderDate\tUnitPrice\tReorderUnits\tTotalPrice");
//            for (ReorderHistory reorderHistory : getInfo.reorderHistories) {
//                System.out.println(simpleDateFormat.format(reorderHistory.reorderDate) + "\t" + reorderHistory.unitPrice.shortValue() + "\t\t\t" + reorderHistory.reorderUnits + "\t\t\t\t" + reorderHistory.totalPrice.shortValue());
//            }
//        }
        InventoryControl inventoryControl = new InventoryControl();
        inventoryControl.Issue_reorders(1998, 4, 24);
    }
}
