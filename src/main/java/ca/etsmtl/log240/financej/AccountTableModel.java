package ca.etsmtl.log240.financej;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class AccountTableModel extends AbstractTableModel {

    private String[] columnNames = {"Name", "Description"};
    private AccountDAO dao = null;

    public AccountTableModel(Connection DBConn) {
        dao = new AccountDAO(DBConn);
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return dao.rowCount();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return dao.getValueAt(row, col);
    }

    public Class getColumnClass(int c) {
        return dao.getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        String AccountName;
        AccountName = (String) dao.getValueAt(row, 0);
        boolean querySuccessful = dao.update("description", (String)value, "name", AccountName);
        if(querySuccessful) {
            fireTableCellUpdated(row, col);
        }
    }

    public void DeleteAccount(int row) {
        String AccountName;
        AccountName = (String) dao.getValueAt(row, 0);
        boolean querySuccessful = dao.delete("name", AccountName);
        if(querySuccessful) {
            fireTableDataChanged();
        }
    }

    public int AddAccount(String Name, String Description) {
        int querySuccessful = dao.create(Name, Description);
        if(querySuccessful == 0) {
            fireTableRowsInserted(getRowCount() + 1, getRowCount() + 1);
        }
        return querySuccessful;
    }
}
