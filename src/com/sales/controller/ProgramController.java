
package com.sales.controller;

import com.sales.model.InvoiceHeader;
import com.sales.model.InvoicesOfTableModel;
import com.sales.model.LineHeader;
import com.sales.model.LinesOfTableModel;
import com.sales.view.InvoiceDialogView;
import com.sales.view.InvoiceFrameView;
import com.sales.view.LineDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProgramController implements ActionListener, ListSelectionListener {
    private InvoiceFrameView invoiceFrameView;
    private InvoiceDialogView invoiceDialogView;
    private LineDialogView lineDialogView;
    
    public ProgramController(InvoiceFrameView frameView) {
        this.invoiceFrameView = frameView;
    }
    @Override
    public void actionPerformed(ActionEvent E) {
        String actionCommand = E.getActionCommand();
        System.out.println("This Action is  : " + actionCommand);
        switch (actionCommand) {
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "New Invoice":
                newInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "New Line":
                newLine();
                break;
            case "Delete Line":
                deleteLine();
                break;
            case "cancelCreateNewInvoice":
                cancelCreateNewInvoice();
                break;
            case "createNewInvoiceOK":
                createNewInvoiceOK();
                break;
            case "createNewLineOK":
                createNewLineOK();
                break;
            case "cancelCreateNewLine":
                cancelCreateNewLine();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent E) {
        int selectedIndex = invoiceFrameView.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            InvoiceHeader currentInvoice = invoiceFrameView.getInvoices().get(selectedIndex);
            invoiceFrameView.getInvoiceNumberLabel().setText("" + currentInvoice.getInvNumber());
            invoiceFrameView.getInvoiceDateLabel().setText(currentInvoice.getDate());
            invoiceFrameView.getCustomerNameLabel().setText(currentInvoice.getCustomerName());
            invoiceFrameView.getInvoiceTotalLabel().setText("" + currentInvoice.getITotalInvoice());
            LinesOfTableModel linesTableModel = new LinesOfTableModel(currentInvoice.getLines());
            invoiceFrameView.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void loadFile() {
       JFileChooser fc = new JFileChooser();
        try {
            JOptionPane.showMessageDialog(invoiceFrameView,"Please Select Invoice Header File" , 
            "Invoice Header", JOptionPane.INFORMATION_MESSAGE);
            int result = fc.showOpenDialog(invoiceFrameView);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("Please Read Invoices");
                ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNumber = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];
                        InvoiceHeader invoice = new InvoiceHeader(invoiceNumber, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception E) {
                        E.printStackTrace();
                        JOptionPane.showMessageDialog(invoiceFrameView, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.out.println("Please Check point");
                 JOptionPane.showMessageDialog(invoiceFrameView,"Please Select Invoice Line File" , 
                "Invoice Line", JOptionPane.INFORMATION_MESSAGE);
                result = fc.showOpenDialog(invoiceFrameView);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("Please Read Lines");
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNumber = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            InvoiceHeader inv = null;
                            for (InvoiceHeader invoice : invoicesArray) {
                                if (invoice.getInvNumber() == invoiceNumber) {
                                    inv = invoice;
                                    break;
                                }
                            }
                            LineHeader line = new LineHeader(itemName, itemPrice, count, inv);
                            inv.getLines().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(invoiceFrameView, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    System.out.println("please Check point");
                }
                invoiceFrameView.setInvoices(invoicesArray);
                InvoicesOfTableModel invoicesTableModel = new InvoicesOfTableModel(invoicesArray);
                invoiceFrameView.setInvoicesTableModel(invoicesTableModel);
                invoiceFrameView.getInvoiceTable().setModel(invoicesTableModel);
                invoiceFrameView.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException E) {
            E.printStackTrace();
            JOptionPane.showMessageDialog(invoiceFrameView, "Cannot read file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<InvoiceHeader> invoices = invoiceFrameView.getInvoices();
        String headers = "";
        String lines = "";
        for (InvoiceHeader invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (LineHeader line : invoice.getLines()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        System.out.println("Please Check point");
        try {
            JOptionPane.showMessageDialog(invoiceFrameView,"Please Save File" , 
            "Save File", JOptionPane.INFORMATION_MESSAGE);
            JFileChooser lf = new JFileChooser();
            int result = lf.showSaveDialog(invoiceFrameView);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = lf.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = lf.showSaveDialog(invoiceFrameView);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = lf.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } 
        catch (Exception E) {
            E.printStackTrace();
            JOptionPane.showMessageDialog(invoiceFrameView, "Cannot Save file", "Error", JOptionPane.ERROR_MESSAGE);
        }       
    }

    private void newInvoice() {
        invoiceDialogView = new InvoiceDialogView(invoiceFrameView);
        invoiceDialogView.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = invoiceFrameView.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            invoiceFrameView.getInvoices().remove(selectedRow);
            invoiceFrameView.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void newLine() {
        lineDialogView = new LineDialogView(invoiceFrameView);
        lineDialogView.setVisible(true);
    }

    private void deleteLine() {
        int selectedRow = invoiceFrameView.getLineTable().getSelectedRow();
        if (selectedRow != -1) {
            LinesOfTableModel linesTableModel = (LinesOfTableModel) invoiceFrameView.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            invoiceFrameView.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void cancelCreateNewInvoice() {
        invoiceDialogView.setVisible(false);
        invoiceDialogView.dispose();
        invoiceDialogView = null;
    }
    

    private void createNewInvoiceOK() {
        String date = invoiceDialogView.getInvoiceDate().getText();
        String customerName = invoiceDialogView.getCustomerName().getText();
        int number = invoiceFrameView.getTheNextInvoiceNumber();
        try {
            String[] dateParts = date.split("-");  // "22-05-2013" -> {"22", "05", "2013"}  xy-qw-20ij
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(invoiceDialogView, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(invoiceDialogView, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                   InvoiceHeader invoice = new InvoiceHeader(number, date, customerName);
                   invoiceFrameView.getInvoices().add(invoice);
                   invoiceFrameView.getInvoicesTableModel().fireTableDataChanged();
                   invoiceDialogView.setVisible(false);
                   invoiceDialogView.dispose();
                   invoiceDialogView = null;
                }
            }
        } catch (Exception E) {
            JOptionPane.showMessageDialog(invoiceDialogView, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }   
      }
    private void createNewLineOK() {
        String item = lineDialogView.getItemName().getText();
        String countStr = lineDialogView.getItemCount().getText();
        String priceStr = lineDialogView.getItemPrice().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = invoiceFrameView.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvoiceHeader invoice = invoiceFrameView.getInvoices().get(selectedInvoice);
            LineHeader line = new LineHeader(item, price, count, invoice);
            invoice.getLines().add(line);
            LinesOfTableModel linesTableModel = (LinesOfTableModel) invoiceFrameView.getLineTable().getModel();
            //linesTableModel.getLines().add(line);
            linesTableModel.fireTableDataChanged();
            invoiceFrameView.getInvoicesTableModel().fireTableDataChanged();
        }
        lineDialogView.setVisible(false);
        lineDialogView.dispose();
        lineDialogView = null;
    }
    private void cancelCreateNewLine() {
        lineDialogView.setVisible(false);
        lineDialogView.dispose();
        lineDialogView = null;
    }
}



    
