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
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Before
    public void setUp() throws Exception {

    }


    @After
    public void tearDown() throws Exception {
        Connection conn = DerbyUtils.getInstance().getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM category");
    }



    @Test
    public void testAdd() {
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
        try {
            categoryDAO.add("Category Name", "Description", 20);
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
        try {
            categoryDAO.add("Category Name", "Description", 20);
            categoryDAO.updateBudget("155", "Category Name");
            String newBudget = categoryDAO.getCategoryBudgetByName("Category Name");
            assertEquals("155.0", newBudget);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testDeleteCategory() {
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
        try {
            categoryDAO.add("Category1", "Description", 15f);
            categoryDAO.add("Category2", "Description", 25f);
            categoryDAO.add("Category3", "Description", 35f);
            int count = Integer.valueOf(categoryDAO.getNameCount());
            assertEquals(3, count);
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testGetAllCategoryOrderByName() {
        try {
            ResultSet categories = categoryDAO.getAllCategoryOrderByName();
            int count = Integer.valueOf(categoryDAO.getNameCount());
            assertEquals(count, categories.getRow());
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testIsConnection() {
        assertTrue(categoryDAO.isConnection());
        assertTrue(DerbyUtils.getInstance().getConnection() != null);
    }

    @Test
    public void testGetCategoryDescriptionByName() {
        try {
            categoryDAO.add("bbb", "description X", 236);
            String description = categoryDAO.getCategoryDescriptionByName("bbb");
            assertEquals(description, "description X");
        }
        catch(Exception e) {
            System.out.println(e);
        };
    }

    @Test
    public void testGetCategoryBudgetByName() {
        try {
            categoryDAO.add("aaa", "Description", 236);
            String budget = categoryDAO.getCategoryBudgetByName("aaa");
            assertEquals(budget, "236.0");
        }
        catch(Exception e) {
            System.out.println(e);
        };
    }
}