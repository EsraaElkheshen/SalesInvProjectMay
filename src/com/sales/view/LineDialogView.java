
package com.sales.view;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LineDialogView extends JDialog{
    private JTextField itemName;
    private JTextField itemCount;
    private JTextField itemPrice;
    private JLabel itemNameLabel;
    private JLabel itemCountLabel;
    private JLabel itemPriceLabel;
    private JButton ButtonOk;
    private JButton ButtonCancel;
    
    public LineDialogView(InvoiceFrameView invoiceFrame) {
        itemName = new JTextField(20);
        itemNameLabel = new JLabel("Item Name");
        
        itemCount = new JTextField(20);
        itemCountLabel = new JLabel("Item Count");
        
        itemPrice = new JTextField(20);
        itemPriceLabel = new JLabel("Item Price");
        
        ButtonOk = new JButton("OK");
        ButtonCancel = new JButton("Cancel");
        
        ButtonOk.setActionCommand("createNewLineOK");
        ButtonCancel.setActionCommand("cancelCreateNewLine");
        
        ButtonOk.addActionListener(invoiceFrame.getController());
        ButtonCancel.addActionListener(invoiceFrame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLabel);
        add(itemName);
        add(itemCountLabel);
        add(itemCount);
        add(itemPriceLabel);
        add(itemPrice);
        add(ButtonOk);
        add(ButtonCancel);
        
        pack();
    }

    public JTextField getItemName() {
        return itemName;
    }

    public JTextField getItemCount() {
        return itemCount;
    }

    public JTextField getItemPrice() {
        return itemPrice;
    }
}