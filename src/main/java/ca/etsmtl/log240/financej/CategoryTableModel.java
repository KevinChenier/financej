package ca.etsmtl.log240.financej;

import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class CategoryTableModel extends AbstractTableModel {

    private String[] columnNames = {"Name", "Description", "Budget"};
    private CategoryDAO categoryDAO;

    public CategoryTableModel() {
        categoryDAO = new CategoryDAO();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        if (categoryDAO.isConnection()) {
            try {
                return categoryDAO.getNameCount();
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in CategoryListTableModel getRowCount");
                e.printStackTrace();
            }
        }
        return 0;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        ResultSet CategoryResult;
        int CurrentRow = 0;

        if (categoryDAO.isConnection()) {
            try {
                CategoryResult = categoryDAO.getAllCategoryOrderByName();
                while (CategoryResult.next()) {
                    if (CurrentRow == row) {
                        if (col == 0) {
                            return CategoryResult.getString(1);
                        } else if (col == 1) {
                            return CategoryResult.getString(2);
                        } else if (col == 2) {
                            return CategoryResult.getFloat(3);
                        }
                    }
                    CurrentRow++;
                }
            } catch (Throwable e) {
                System.out.println(" . . . exception thrown: in CategoryListTableModel getValueAt");
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
        return col != 0;
    }

    public void setValueAt(Object value, int row, int col) {
        String categoryName;
        try {
            categoryName = (String) getValueAt(row, 0);
            if (col == 1) {
                categoryDAO.updateDescription((String) value, categoryName);
            } else if (col == 2) {
                categoryDAO.updateBudget(value.toString(), categoryName);
            }
            fireTableCellUpdated(row, col);
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown: in setValueAt in Category.java");
            e.printStackTrace();
        }
    }

    public void deleteCategory(int row) {
        try {
            categoryDAO.delete((String) getValueAt(row, 0));
            fireTableDataChanged();
        } catch (SQLException e) {
            System.out.println(" . . . exception thrown: in CategoryTableModel DeleteAccount");
            e.printStackTrace();
        }
    }

    public int addCategory(String name, String description, float budget) {
        int errorCode = 0;
        try {
            categoryDAO.add(name, description, budget);
            fireTableRowsInserted(getRowCount() + 1, getRowCount() + 1);
        }
        catch (Throwable e) {
            System.out.println(" . . . exception thrown: AddCategory");
            e.printStackTrace();
            errorCode = 1;
        }

        return errorCode;
    }
}