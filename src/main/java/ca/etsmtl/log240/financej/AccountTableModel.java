package ca.etsmtl.log240.financej;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.List;

public class AccountTableModel extends AbstractTableModel {
    private String[] columnNames = {"Name", "Description"};
    private AccountDAO accountDAO;
    private List<Account> accounts;

    public AccountTableModel() {
        try {
            accountDAO = new AccountDAO();
            accounts = accountDAO.getAllAccounts();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() { return accounts.size(); }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        Account currentAccount = accounts.get(row);
        return col == 0 ? currentAccount.getName() : col == 1 ? currentAccount.getDescription() : null;
    }

    /*
     * Don't need to implement this method unless your table's
     * editable. Note that the data/cell address is constant,
       no matter where the cell appears onscreen.
     */
    public boolean isCellEditable(int row, int col) { return col == 0 ? false : true; }

    public void setValueAt(Object value, int row, int col) {
        try {
            accountDAO.update(accounts.get(row).getName(), (String) value);
            updateAccounts();
            fireTableDataChanged();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(int row) {
        try {
            accountDAO.remove(accounts.get(row).getName());
            updateAccounts();
            fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addAccount(String name, String description) {
        int errorCode = 0;

        if (!Account.isValidName(name)) {
            errorCode = 2;
        } else if (!Account.isValidDescription(description)) {
            errorCode = 3;
        } else {
            try {
                accountDAO.add(name, description);
                updateAccounts();
                fireTableDataChanged();
            } catch (Throwable e) {
                e.printStackTrace();
                errorCode = 1;
            }
        }
        return errorCode;
    }

    public void updateAccounts() throws SQLException {
        accounts = accountDAO.getAllAccounts();
    }

}
