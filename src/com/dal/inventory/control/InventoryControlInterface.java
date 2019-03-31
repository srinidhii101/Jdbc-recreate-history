package com.dal.inventory.control;

import java.sql.SQLException;
import java.text.ParseException;

interface InventoryControlInterface {

    public void Ship_order(int orderNumber) throws OrderException, SQLException, ClassNotFoundException;

    public int Issue_reorders(int year, int month, int day) throws SQLException, ClassNotFoundException, ParseException;

    public void Receive_order(int internal_order_reference) throws OrderException;

    public boolean isShipped(int orderNumber) throws OrderException;

    public boolean isArrived(int orderNumber) throws OrderException;
}
