
package com.sales.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class InvoiceDialogView extends JDialog {
    private JTextField customerName;
    private JTextField invoiceDate;
    private JLabel customerNameLabel;
    private JLabel invoiceDateLabel;
    private JButton okButton;
    private JButton cancelButton;

    public InvoiceDialogView(InvoiceFrameView frame) {
        customerNameLabel = new JLabel("Customer Name:");
        customerName = new JTextField(20);
        invoiceDateLabel = new JLabel("Invoice Date:");
        invoiceDate = new JTextField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        okButton.setActionCommand("createNewInvoiceOK");
        cancelButton.setActionCommand("cancelCreateNewInvoice");
        okButton.addActionListener(frame.getController());
        cancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        add(invoiceDateLabel);
        add(invoiceDate);
        add(customerNameLabel);
        add(customerName);
        add(okButton);
        add(cancelButton);
        pack();   
    }

    public JTextField getCustomerName() {
        return customerName;
    }

    public JTextField getInvoiceDate() {
        return invoiceDate;
    }
    
}

    

