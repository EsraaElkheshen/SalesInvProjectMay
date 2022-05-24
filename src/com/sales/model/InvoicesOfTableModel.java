
package com.sales.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoicesOfTableModel extends AbstractTableModel {
    private ArrayList<InvoiceHeader> invoices;
    private String[] columns = {"No.", "Date", "CustomerName", "Total"};
    
    public InvoicesOfTableModel(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoice = invoices.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return invoice.getInvNumber();
            case 1: return invoice.getDate();
            case 2: return invoice.getCustomerName();
            case 3: return invoice.getITotalInvoice();
            default : return "";
        }
    }

}