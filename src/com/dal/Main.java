package com.dal;

import com.dal.history.GetInfo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {

        GetInfo getInfo = new GetInfo();
        List<String> products = getInfo.getListOfProducts();

        for (int i = 0; i < 1; i++) {
            System.out.println(getInfo.getProductHistory(products.get(i)));
            System.out.println(getInfo.getCurrentStockLevel(products.get(i)));
            System.out.println(getInfo.getReorderLevel(products.get(i)));
            System.out.println(getInfo.getMaximumInventoryStorage(products.get(i)));
            getInfo.getReorderHistory(new ArrayList<>(), 8, 2, 5);
        }
    }
}
