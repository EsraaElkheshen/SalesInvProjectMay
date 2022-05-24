
package com.sales.model;


public class LineHeader {
    private String itemName;
    private double price;
    private int count;
    private InvoiceHeader invoice;

    public LineHeader() {
    }

    public LineHeader(String itemName, double price, int count, InvoiceHeader invoice) {
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }

    public double getLineTotal() {
        return price * count;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getItem() {
        return itemName;
    }

    public void setItem(String item) {
        this.itemName = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Line{" + "number=" + invoice.getInvNumber() + ", item=" + itemName + ", price=" + price + ", count=" + count + '}';
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }
    
    public String getAsCSV() {
        return invoice.getInvNumber() + "," + itemName + "," + price + "," + count;
    }
}
    
    

