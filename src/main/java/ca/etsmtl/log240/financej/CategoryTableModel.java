package ca.etsmtl.log240.financej;


import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

class CategoryTableModel extends AbstractTableModel {

    private String[] columnNames = {"Name", "Description", "Budget"};
    private CategoryDAO dao;

    public CategoryTableModel(Connection DBConn) {
        dao = new CategoryDAO(DBConn);
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return dao.count();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return dao.getValueAt(row, col);
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
        return col != 0;
    }

    public void setValueAt(Object value, int row, int col) {
        String CategoryName;
        CategoryName = (String) dao.getValueAt(row, 0);
        boolean querySuccessful = dao.update(getColumnName(col), (String)value, "name", CategoryName);
        if(querySuccessful) {
            fireTableCellUpdated(row, col);
        }
    }

    public void DeleteCategory(int row) {
        String CategoryName;
        CategoryName = (String) dao.getValueAt(row, 0);
        boolean querySuccessful = dao.delete("name", CategoryName);
        if(querySuccessful) {
            fireTableDataChanged();
        }
    }

    public int AddCategory(String Name, String Description, String budget) {
        float budgetFloat;
        if(budget.length() == 0) {
            return 1;
        }
        try{
            budgetFloat = Float.valueOf(budget.trim()).floatValue();
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in CategoryTableModel AddCategory");
            e.printStackTrace();
            return 1;
        }

        if(budgetFloat < 0) {
            return 1;
        }

        int querySuccessful = dao.create(new String[]{Name, Description, Float.toString(budgetFloat)});
        if(querySuccessful == 0) {
            fireTableRowsInserted(getRowCount() + 1, getRowCount() + 1);
        }
        return querySuccessful;
    }
}

