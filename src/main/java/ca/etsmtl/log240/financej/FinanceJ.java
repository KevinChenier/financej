package ca.etsmtl.log240.financej;
/*
 * FinanceJ.java
 *
 * Created on March 4, 2008, 10:46 PM
 */


import ca.etsmtl.log240.db.DerbyUtils;

import java.sql.*;
import javax.swing.table.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

/**
 *
 * @author  rovitotv
 */
public class FinanceJ extends JFrame {


    private Account AccountDialog;
    private Category CategoryDialog;
    private Ledger LedgerDialog;
    private Reports ReportsDialog;
    private AccountTotalTableModel dataModel;
    
    public void UpdateTotal() {
        ResultSet LedgerResult;
        Statement s;
        String TotalStr;

        TotalStr = "$0.00";
        if (DerbyUtils.getInstance().getConnection() != null) {
            try {
                s = DerbyUtils.getInstance().getConnection().createStatement();
                LedgerResult = s.executeQuery("select sum(amount) from ledger");
                while (LedgerResult.next()) {
                    if (LedgerResult.getFloat(1) <= 0) {
                        Color fg = new Color(255,51,50);
                        TotalLabel.setForeground(fg);
                    } else {
                        Color fg = new Color(0, 0, 0);
                        TotalLabel.setForeground(fg);
                    }
                  TotalStr = String.format("$%12.2f", LedgerResult.getFloat(1));
                  TotalLabel.setText(TotalStr);
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountListTableModel getValueAt");
                e.printStackTrace();
            }
        }
    
    }
    
    /** Creates new form FinanceJ */
    public FinanceJ() {
        DerbyUtils derbyUtils = DerbyUtils.getInstance("org.apache.derby.jdbc.EmbeddedDriver");
        LoadDBDriver();
        derbyUtils.createDBConnection();
        derbyUtils.createDBTables();

        initComponents();

        LedgerDialog = new Ledger(this, true);
        LedgerDialog.setVisible(false);
        LedgerDialog.SetDBConnection(derbyUtils.getConnection());

        AccountDialog = new Account(this, true);
        AccountDialog.setVisible(false);
        AccountDialog.SetDBConnection(derbyUtils.getConnection());
        
        CategoryDialog = new Category(this, true);
        CategoryDialog.setVisible(false);
        CategoryDialog.SetDBConnection(derbyUtils.getConnection());
        
        ReportsDialog = new Reports(this, true);
        ReportsDialog.setVisible(false);
        ReportsDialog.SetDBConnection(derbyUtils.getConnection());
        
        dataModel = new AccountTotalTableModel(derbyUtils.getConnection());
        AccountTotalTable.setModel(dataModel);
        AccountTotalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        UpdateTotal();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ExitButton = new javax.swing.JButton();
        AccountButton = new javax.swing.JButton();
        CategoriesButton = new javax.swing.JButton();
        LedgerButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        AccountTotalTable = new javax.swing.JTable();
        ReportsButton = new javax.swing.JButton();
        TotalLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FinanceJ");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        ExitButton.setText("Exit");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        AccountButton.setText("Accounts");
        AccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccountButtonActionPerformed(evt);
            }
        });

        CategoriesButton.setText("Categories");
        CategoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CategoriesButtonActionPerformed(evt);
            }
        });

        LedgerButton.setText("Ledger");
        LedgerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LedgerButtonActionPerformed(evt);
            }
        });

        AccountTotalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Account", "Balance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(AccountTotalTable);

        ReportsButton.setText("Reports");
        ReportsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportsButtonActionPerformed(evt);
            }
        });

        TotalLabel.setForeground(new java.awt.Color(255, 51, 51));
        TotalLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TotalLabel.setText("$0.00");

        jLabel1.setText("Net Worth:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(TotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(CategoriesButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(LedgerButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(AccountButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(ReportsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(ExitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(LedgerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CategoriesButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AccountButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReportsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ExitButton)
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        DerbyUtils.getInstance().shutdownDB();
    }//GEN-LAST:event_formWindowClosing

    private void ExitButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
       dispose(); 
    }//GEN-LAST:event_ExitButtonActionPerformed

    private void formWindowClosed(WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        DerbyUtils.getInstance().shutdownDB();
    }//GEN-LAST:event_formWindowClosed

    private void AccountButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AccountButtonActionPerformed
        AccountDialog.setVisible(true);
    }//GEN-LAST:event_AccountButtonActionPerformed

    private void CategoriesButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_CategoriesButtonActionPerformed
        CategoryDialog.setVisible(true);
    }//GEN-LAST:event_CategoriesButtonActionPerformed

    private void LedgerButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_LedgerButtonActionPerformed
        LedgerDialog.BuildAccountsComboBox();
        LedgerDialog.BuildCategoryComboBox();
        LedgerDialog.setVisible(true);
    }//GEN-LAST:event_LedgerButtonActionPerformed

    private void formWindowActivated(WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // refreshes the window
        dataModel.fireTableDataChanged();        
        UpdateTotal();
    }//GEN-LAST:event_formWindowActivated

    private void ReportsButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_ReportsButtonActionPerformed
        ReportsDialog.setVisible(true);
}//GEN-LAST:event_ReportsButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinanceJ().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton AccountButton;
    private JTable AccountTotalTable;
    private JButton CategoriesButton;
    private JButton ExitButton;
    private JButton LedgerButton;
    private JButton ReportsButton;
    private JLabel TotalLabel;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}

class AccountTotalTableModel extends AbstractTableModel {

    private String[] columnNames = {"Account", "Balance"};
    private Connection conn = null;

    public AccountTotalTableModel(Connection DBConn) {
        conn = DBConn;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        ResultSet AccountResult;
        Statement s;
        int NumRecords = 0;

        if (conn != null) {
            try {
                s = conn.createStatement();
                AccountResult = s.executeQuery("select account, sum(amount) from ledger group by account");
                while (AccountResult.next()) {
                    NumRecords++;
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountListTableModel getRowCount");
                e.printStackTrace();
            }
        }

        return NumRecords;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        ResultSet AccountResult;
        Statement s;
        int CurrentRow = 0;

        if (conn != null) {
            try {
                s = conn.createStatement();
                AccountResult = s.executeQuery("select account, sum(amount) from ledger group by account");
                while (AccountResult.next()) {
                    if (CurrentRow == row) {
                        if (col == 0) {
                            return AccountResult.getString(1);
                        } else if (col == 1) {
                            return AccountResult.getFloat(2);
                        }
                    }
                    CurrentRow++;
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountListTableModel getValueAt");
                e.printStackTrace();
            }
        }
        return "";
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.

        return false;
    }

}

