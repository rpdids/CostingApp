package com.softeng.jobcosting.jobcostingapp.BusinessLogic;

import com.softeng.jobcosting.jobcostingapp.Database.Database;
import com.softeng.jobcosting.jobcostingapp.Database.GlobalDatabase;

public class Calculations {
    private Database db;

    public Calculations() {
        db = GlobalDatabase.getDB();
    }

    public String newOrder() {
        db.setTable("Orders");
        db.insert("Date", "Date()");
        return db.query();
    }

    public String newItem(String orderID, String store, String description, String type, String price) {
        String result = null;

        db.setTable("Costs");

        //if all inserts were successful, returns the result of the query method
        if (db.update("Store", store, "OrderID", orderID) &&
                db.update("Description", description, "OrderID", orderID) &&
                db.update("Type", type, "OrderID", orderID) &&
                db.update("Price", price, "OrderID", orderID)) {
            result = db.query();
        }

        return result;
    }

    public void editItem(String field, String newValue, int costID) {
        db.setTable("Costs");
        boolean success = db.update(field, newValue, "CostID", Integer.toString(costID));
    }

    public String getItems(int orderID) {
        db.setTable("Costs");
        boolean success = db.where("OrderID", Integer.toString(orderID));
        return db.query();
    }

    public int[] getOrderIDs()  {

        db.setTable("Orders");
//        db.select();
        String []orderTable = db.query().split("\n");
        String unfmtdNums = "";

        for(int i = 0; i < orderTable.length; i++)  {
            unfmtdNums += orderTable[i].split(",")[0];
            if(i != orderTable.length-1)   {
                unfmtdNums += ",";
            }
        }

        String[] unfmtdArray = unfmtdNums.split(",");
        int[] orderNums = new int[unfmtdArray.length];

        for(int i = 0; i < orderNums.length; i++)   {
            orderNums[i] = Integer.parseInt(unfmtdArray[i]);
        }



        return orderNums;

    }

    public float getProfit(int orderID) {
        float profit = 0;

        profit = getTotal(orderID) / getPSTCharged(orderID);

        return profit;
    }

    public float getMargin() {

    }

    public float getTotal(int orderID) {
        float total = 0;
        String query = null;
        String[] tokens = null;

        db.setTable("Costs");

        if(db.where("OrderID", "Price"))
        {
            query = db.query();
            tokens = query.split(",");

            for(int i = 0; i < tokens.length; i++)
            {
                total += Float.parseFloat(tokens[i]);
            }
        }

        return total;
    }

    public float getPSTCharged(int orderID)
    {
        float PSTCharged = 0;
        String query = null;
        String[] tokens = null;

        db.setTable("Costs");

        if(db.where("Type", "PSTCharged"))
        {
            query = db.query();
            tokens = query.split(",");

            for(int i = 0; i < tokens.length; i++)
            {
                PSTCharged += Float.parseFloat(tokens[i]);
            }
        }

        return PSTCharged;
    }
}