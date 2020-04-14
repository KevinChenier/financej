package ca.etsmtl.log240.financej;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AccountDAO extends AbstractTableModel {

    private Connection conn = null;
    private String[] columnNames = {"Name", "Description"};

    public AccountDAO(Connection DBConn) {
        conn = DBConn;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return rowCount();
    }







    /*
     * Don't need to implement this method unless your table's editable.
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
        AccountName = (String) getValueAt(row, 0);
        boolean querySuccessful = update("description", (String)value, "name", AccountName);
        if(querySuccessful) {
            fireTableCellUpdated(row, col);
        }
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
                System.out.println(" . . . exception thrown: in AccountDAO getValueAt");
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int c) {return getValueAt(0, c).getClass();}

    public boolean update(String fieldToUpdate, String valueToUpdate, String fieldWhere, String valueWhere){
        String SQLString;
        try {
            Statement s = conn.createStatement();
            SQLString = "update account set " + fieldToUpdate + " ='" + valueToUpdate + "' where " + fieldWhere + " LIKE '" + valueWhere + "'";
            System.out.println(SQLString);
            s.execute(SQLString);
            return true;
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in setValueAt in AccountDAO.java");
            e.printStackTrace();
            return false;
        }
    }

    public void DeleteAccount(int row) {
        String AccountName;
        AccountName = (String) getValueAt(row, 0);
        boolean querySuccessful = delete("name", AccountName);
        if(querySuccessful) {
            fireTableDataChanged();
        }
    }

    public int create(String Name, String Description){
        int ErrorCode = 0;
        PreparedStatement psInsert;
        try {
            psInsert = conn.prepareStatement("insert into account(name, description) values(?,?)");
            psInsert.setString(1, Name);
            psInsert.setString(2, Description);
            psInsert.executeUpdate();
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: AddAccount");
            e.printStackTrace();
            ErrorCode = 1;
        }
        return ErrorCode;
    }

    public int rowCount(){
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
                System.out.println(" . . . exception thrown: in AccountDAO getRowCount");
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int AddAccount(String Name, String Description) {
        if (Name.length() < 3 || Description.length() < 3) {
            return 0;
        }
        if (Name.length() > 250 || Description.length()  > 250 ) {
            return 0;
        }
        int querySuccessful = create(Name, Description);
        if(querySuccessful == 0) {
            fireTableRowsInserted(getRowCount() + 1, getRowCount() + 1);
        }
        return querySuccessful;
    }

    public boolean delete(String field, String value){
        Statement s;
        String SQLString;
        if (conn != null) {
            try {
                s = conn.createStatement();
                SQLString = "DELETE FROM account WHERE "+field+" LIKE '" + value + "'";
                System.out.println(SQLString);
                s.execute(SQLString);
                return true;
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in AccountDAO DeleteAccount");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
