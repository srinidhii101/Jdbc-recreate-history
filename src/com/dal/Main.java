package com.dal;

import com.dal.history.GetInfo;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        GetInfo getInfo = new GetInfo();
        List<String> products = getInfo.getListOfProducts();

        for (String productCode : products) {
            System.out.println(productCode);
            getInfo.getProductHistory(productCode);
        }

    }
}
