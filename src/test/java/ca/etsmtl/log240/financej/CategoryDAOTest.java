package ca.etsmtl.log240.financej;

import ca.etsmtl.log240.db.DerbyUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class CategoryDAOTest {

    @Before
    public void setUp() throws Exception {

    }


    @AfterClass
    public static void tearDown() throws Exception {
        Connection conn = DerbyUtils.getInstance().getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM category");
       DerbyUtils.getInstance().shutdownDB();
    }


    @Test
    public void testAdd() {
        CategoryDAO categoryDAO = new CategoryDAO();
        try {
            int countBefore = Integer.valueOf(categoryDAO.getNameCount());
            categoryDAO.add("Category Name", "Description", 55f);
            int countAfter = Integer.valueOf(categoryDAO.getNameCount());
            assertEquals(countBefore + 1, countAfter);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testUpdateDescription() {
        CategoryDAO categoryDAO = new CategoryDAO();
        try {
            categoryDAO.updateDescription("new description", "Category Name");
            String newDescription = categoryDAO.getCategoryDescriptionByName("Category Name");
            assertEquals("new description", newDescription);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testUpdateBudget() {
        CategoryDAO categoryDAO = new CategoryDAO();
        try {
            categoryDAO.updateBudget("155", "Category Name");
            String newBudget = categoryDAO.getCategoryDescriptionByName("Category Name");
            assertEquals("155", newBudget);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testDeleteCategory() {
        CategoryDAO categoryDAO = new CategoryDAO();
        try {
            categoryDAO.add("temp", "Description", 55f);
            int countBefore = Integer.valueOf(categoryDAO.getNameCount());
            categoryDAO.delete("temp");
            int countAfter = Integer.valueOf(categoryDAO.getNameCount());
            assertEquals(countBefore-1, countAfter);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testGetNameCount() {
        CategoryDAO categoryDAO = new CategoryDAO();
        try {
            categoryDAO.add("Category1", "Description", 15f);
            categoryDAO.add("Category2", "Description", 25f);
            categoryDAO.add("Category3", "Description", 35f);
            int count = Integer.valueOf(categoryDAO.getNameCount());
            assertEquals(4, count);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testGetAllCategoryOrderByName() {
        CategoryDAO categoryDAO = new CategoryDAO();
        try {
            ResultSet categories = categoryDAO.getAllCategoryOrderByName();
            int count = Integer.valueOf(categoryDAO.getNameCount());
            categories.last();
            assertEquals(count, categories.getRow());
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testIsConnection() {
        CategoryDAO categoryDAO = new CategoryDAO();
        assertTrue(categoryDAO.isConnection());
        assertTrue(DerbyUtils.getInstance().getConnection() != null);
    }

    @Test
    public void testGetCategoryBudgetByName() {
        CategoryDAO categoryDAO = new CategoryDAO();
        try {
            categoryDAO.add("aaa", "Description", 236);
            String budget = categoryDAO.getCategoryBudgetByName("aaa");
            assertEquals(budget, "236");
        }
        catch(Exception e) {
            System.out.println(e);
        };
    }
}