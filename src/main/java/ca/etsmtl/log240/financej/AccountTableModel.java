package ca.etsmtl.log240.financej;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class AccountTableModel extends AbstractTableModel {

    private String[] columnNames = {"Name", "Description"};
    private Connection conn = null;

    public AccountTableModel(Connection DBConn) {
        conn = DBConn;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        ResultSet AccountResult;
        Statement s;

        if (conn != null) {
            try {
                s = conn.createStatement();
                AccountResult = s.executeQuery("select count(name) from account");
                while (AccountResult.next()) {
                    return AccountResult.getInt(1);
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountTableModel getRowCount");
                e.printStackTrace();
            }
        }

        return 0;
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
                AccountResult = s.executeQuery("select * from account order by name");
                while (AccountResult.next()) {
                    if (CurrentRow == row) {
                        if (col == 0) {
                            return AccountResult.getString(1);
                        } else if (col == 1) {
                            return AccountResult.getString(2);
                        }
                    }
                    CurrentRow++;
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountTableModel getValueAt");
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


        if (col == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        String SQLString;

        String AccountName;
        try {
            AccountName = (String) getValueAt(row, 0);
            Statement s = conn.createStatement();
            SQLString = "update account set description ='" + (String) value + "' where name = '" + AccountName + "'";
            System.out.println(SQLString);
            s.execute(SQLString);

            fireTableCellUpdated(row, col);
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in setValueAt in Account.java");
            e.printStackTrace();
        }
    }

    public void DeleteAccount(int row) {
        Statement s;
        String AccountName;
        String SQLString;

        if (conn != null) {
            try {
                AccountName = (String) getValueAt(row, 0);
                s = conn.createStatement();
                SQLString = "DELETE FROM account WHERE name = '" + AccountName + "'";
                System.out.println(SQLString);
                s.executeUpdate(SQLString);
                fireTableDataChanged();
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountTableModel DeleteAccount");
                e.printStackTrace();
            }
        }
    }

    public int AddAccount(String Name, String Description) {
        int ErrorCode = 0;
        PreparedStatement psInsert;

        try {
            psInsert = conn.prepareStatement("insert into account(name, description) values(?,?)");
            psInsert.setString(1, Name);
            psInsert.setString(2, Description);

            psInsert.executeUpdate();
            fireTableRowsInserted(getRowCount() + 1, getRowCount() + 1);
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: AddAccount");
            e.printStackTrace();
            ErrorCode = 1;
        }

        return ErrorCode;
    }
}
