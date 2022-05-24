
package com.sales.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class InvoiceHeader {
    private int invNumber;
    private String date;
    private String customerName;
    private ArrayList<LineHeader> lines;
    
    public InvoiceHeader() {
    }

    public InvoiceHeader(int number, String date, String customerName) {
        this.invNumber = number;
        this.date = date;
        this.customerName = customerName;
    }

    public double getITotalInvoice() {
        double total = 0.0;
        for (LineHeader lineOfHeader : getLines()) {
            total += lineOfHeader.getLineTotal();
        }
        return total;
    }
    
    public ArrayList<LineHeader> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(int number) {
        this.invNumber = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "number=" + invNumber + ", date=" + date + ", customerName=" + customerName + '}';
    }
    
    public String getAsCSV() {
        
        return "" + getInvNumber() + "," +getDate() + "," + getCustomerName();
        
    }
    
    
    
    
    
    
    
}