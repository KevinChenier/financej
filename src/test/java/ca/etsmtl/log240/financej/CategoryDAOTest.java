package ca.etsmtl.log240.financej;

import junit.framework.TestCase;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDAOTest extends TestCase {

    // invalid DB settings
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String dbName = "FinanceJDB";
    private static String connectionURL = "jdbc:derby:" + dbName + ";create=true";

    // valid DB settings
    private static DerbyUtils derbyConnection;
    private static Connection conn = null;
    private static CategoryDAO dao;

    @BeforeAll
    public static void init() {
        derbyConnection = derbyConnection.init();
        conn = derbyConnection.getConnection();
        dao = new CategoryDAO(conn);
    }

    @Test
    @Order(1)
    public void testCreateCategory() {
        int responseFirstCategory = dao.create(new String[]{"Categorie", "Description", "10"});
        assertEquals( 0, responseFirstCategory);
        int responseSecondCategory = dao.create(new String[]{"Categorie2", "Description2", "101"});
        assertEquals( 0, responseSecondCategory);
    }

    @Test
    @Order(2)
    public void testRowCount() {
        assertEquals(2, dao.count());
    }

    @Test
    @Order(3)
    public void testGetRowCount() {
        assertEquals(2, dao.count());
    }

    @Test
    @Order(5)
    public void testGetValueAt_Name_Row0() {
        assertEquals(dao.getValueAt(0,0), "Categorie");
    }

    @Test
    @Order(6)
    public void testGetValueAt_Description_Row0() {
        assertEquals(dao.getValueAt(1,1), "Description2");
    }

    @Test
    @Order(7)
    public void testUpdateCategoryValid() {
        assertTrue(dao.update("description", "Description3", "name", "Categorie"));
        assertEquals( "Description3", dao.getValueAt(0,1));
    }

    @Test
    @Order(8)
    public void testUpdateCategoryInvalid() {
        assertFalse(dao.update( "description", "Description5", "name","Paul"));
    }

    @Test
    @Order(10)
    public void testReadAll() {
        ResultSet result = dao.readAll();
        Assertions.assertNotEquals(null, result);
    }

    @Test
    @Order(11)
    public void testRead_valid() {
        ResultSet result = dao.read(0);
        System.out.println(result);
        Assertions.assertNotEquals(null, result);
    }

    @Test
    @Order(12)
    public void testRead_invalid() {
        ResultSet result = dao.read(4);
        Assertions.assertEquals(null, result);
    }

    @Test
    @Order(13)
    public void testDeleteCategoryByName() {
        assertTrue(dao.delete( "name", "Categorie"));
    }

    @Test
    @Order(14)
    public void testUpdateDeleteCategoryByDescription() {
        assertTrue(dao.delete( "description", "Description2"));
    }

    @Test
    @Order(15)
    public void testUpdateDeleteCategoryWithInexistantName() {
        assertFalse(dao.delete( "name", "Paul"));
    }


    @Test
    @Order(16)
    public void testReadAll_noConnexion() {
        CategoryDAO dao3 = new CategoryDAO(null);
        ResultSet result = dao3.readAll();
        Assertions.assertEquals(null, result);
    }


    @Test
    @Order(17)
    public void testGetValueAt_nullConnexion() {
        CategoryDAO dao2 = new CategoryDAO(null);
        String result = (String)dao2.getValueAt(0,0);
        assertEquals(result, "");
    }

}