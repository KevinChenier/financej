package ca.etsmtl.log240.financej;
import org.uispec4j.Table;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;
import org.uispec4j.utils.Utils;
import org.uispec4j.interception.handlers.ShownInterceptionDetectionHandler;
import org.uispec4j.interception.toolkit.UISpecDisplay;
import org.uispec4j.utils.ComponentUtils;
import org.uispec4j.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CategoryTest extends FinancejAbstractTest {
    private static String CONNECTION_URL = "jdbc:derby:FinanceJDB;create=true";
    private Table categoryTable;
    private final int MAX_SIZE_NAME= 50;
    private final int MAX_SIZE_DESCRIPTION= 250;

     @Override
    protected void setUp() throws Exception {
        super.setUp();
        // clear table before each test
        Connection conn = DriverManager.getConnection(CONNECTION_URL);
        Statement statement = conn.createStatement();
        statement.executeUpdate("DELETE FROM category");
    }

    public void testCreerCategoryValide () throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();
                final String NAME_INPUT = "Name";
                final String BUDGET_INPUT = "10.00";
                final String DESCRIPTION_INPUT = "Lorem ipsum dolor.";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
             //   assertEquals();
                assertEquals(categoryTable.getRowCount(), initialRowCount + 1);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }

    public void testCreerCategoryNomVide () throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();
                final String NAME_INPUT = "";
                final String BUDGET_INPUT = "10.00";
                final String DESCRIPTION_INPUT = "Lorem ipsum dolor.";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
                // pas de nouvelle ligne dans la table
                assertEquals(categoryTable.getRowCount(), initialRowCount);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }

    public void testCreerCategoryBudgetVide () throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();
                final String NAME_INPUT = "Name";
                final String BUDGET_INPUT = "";
                final String DESCRIPTION_INPUT = "Lorem ipsum dolor.";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
                // pas de nouvelle ligne dans la table
                assertEquals(categoryTable.getRowCount(), initialRowCount);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }

    public void testCreerCategoryDescriptionVide () throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();
                final String NAME_INPUT = "Name";
                final String BUDGET_INPUT = "10.00";
                final String DESCRIPTION_INPUT = "";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
                // pas de nouvelle ligne dans la table
                assertEquals(categoryTable.getRowCount(), initialRowCount);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }

    public void testCreerCategoryNomLessThreeChar() throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();
                final String NAME_INPUT = "ab";
                final String BUDGET_INPUT = "10.00";
                final String DESCRIPTION_INPUT = "Lorem ipsum dolor.";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
                // pas de nouvelle ligne dans la table
                assertEquals(categoryTable.getRowCount(), initialRowCount);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }

    public void testCreerCategoryBudgetNegative() throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();
                final String NAME_INPUT = "Name";
                final String BUDGET_INPUT = "-5.00";
                final String DESCRIPTION_INPUT = "Lorem ipsum dolor.";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
                // pas de nouvelle ligne dans la table
                assertEquals(categoryTable.getRowCount(), initialRowCount);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }

    public void testCreerCategoryDescriptionLessThreeChar() throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();
                final String NAME_INPUT = "Name";
                final String BUDGET_INPUT = "10.00";
                final String DESCRIPTION_INPUT = "ab.";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
                // pas de nouvelle ligne dans la table
                assertEquals(categoryTable.getRowCount(), initialRowCount);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }

    public void testCreerCategoryNameMore50Char() throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();

                StringBuffer bufferChaine = new StringBuffer(MAX_SIZE_NAME + 1);
                        for (int i = 0; i < MAX_SIZE_NAME + 1 + 1; i++) {
                            bufferChaine.append("n");
                        }
                final String NAME_INPUT = bufferChaine.toString();
                final String BUDGET_INPUT = "10.00";
                final String DESCRIPTION_INPUT = "Lorem ipsum dolor";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
                // pas de nouvelle ligne dans la table
                assertEquals(categoryTable.getRowCount(), initialRowCount);
                return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }

    public void testCreerCategoryDescMore250Char() throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();

                StringBuffer bufferChaine = new StringBuffer(MAX_SIZE_DESCRIPTION + 1);
                        for (int i = 0; i < MAX_SIZE_DESCRIPTION + 1 + 1; i++) {
                            bufferChaine.append("n");
                        }
                final String NAME_INPUT = bufferChaine.toString();
                final String BUDGET_INPUT = "10.00";
                final String DESCRIPTION_INPUT = "Lorem ipsum dolor";
                fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                window.getButton("Add Category").click();
                // pas de nouvelle ligne dans la table
                assertEquals(categoryTable.getRowCount(), initialRowCount);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }
    

    public void fillValuesForTest (Window window, String name, String desc, String budg) {
        window.getTextBox("NAME_TEXT_FIELD").setText(name);
        window.getTextBox("DESC_TEXT_FIELD").setText(desc);
        window.getTextBox("BUDG_DOUBLE_FIELD").setText(budg);
    }
}
