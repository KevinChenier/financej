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

public class CategoryTest extends FinancejAbstractTest {
    private Table categoryTable;

    /**
     * Nom
     */
    // Le nom de la categorie est vide (invalide)
    public void testAddingValidCategoryName () throws Exception {
         WindowInterceptor.init(categoriesButton.triggerClick())
          .process(new WindowHandler() {
              public Trigger process(Window window) {
                // setup
                categoryTable = window.getTable();
                int initialRowCount = categoryTable.getRowCount();
                String validBudget = "10.00";
                String validDescription = "Lorem ipsum dolor.";
                fillValuesForTest(window, "valid", validDescription, validBudget);
                window.getButton("Add Category").triggerClick();
                assertEquals(categoryTable.getRowCount(), initialRowCount + 1);
              return window.getButton("Close").triggerClick();
            }
        })
        .run();
    }


    // Le nom de la categorie comporte 0 caracteres (invalide)
    // Le nom de la categorie comporte 250 caracteres (invalide)
    public void testAddingInvalidCategoryName () throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
            .process(new WindowHandler() {
                public Trigger process(Window window) {
                    // setup
                    categoryTable = window.getTable();
                    int initialRowCount = categoryTable.getRowCount();
                    String validBudget = "10.00";
                    String validDescription = "Lorem ipsum dolor.";
                    fillValuesForTest(window, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam consequat, dolor laoreet fringilla iaculis, magna quam faucibus enim, quis aliquam neque enim quis mi. Aenean dui enim, accumsan vitae neque ut, laoreet tincidunt ex. Integer massa nunct.", validDescription, validBudget);
                    window.getButton("Add Category").triggerClick();
                    assertEquals(categoryTable.getRowCount(), initialRowCount);

                    fillValuesForTest(window, "", validDescription, validBudget);
                    window.getButton("Add Category").triggerClick();
                    assertEquals(categoryTable.getRowCount(), initialRowCount);

                    return window.getButton("Close").triggerClick();
                }
            })
            .run();
    }

    /**
     * Description
     */

    // La description contient 251 caracteres (invalide)
    public void testAddingInvalidCategoryDescription () throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
            .process(new WindowHandler() {
                public Trigger process(Window window) {
                    // setup
                    categoryTable = window.getTable();
                    int initialRowCount = categoryTable.getRowCount();
                    String validBudget = "10.00";
                    String invalidDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam consequat, dolor laoreet fringilla iaculis, magna quam faucibus enim, quis aliquam neque enim quis mi. Aenean dui enim, accumsan vitae neque ut, laoreet tincidunt ex. Integer massa nunc1.";
                    fillValuesForTest(window, "invalid_description_1", invalidDescription, validBudget);
                    window.getButton("Add Category").triggerClick();
                    assertEquals(categoryTable.getRowCount(), initialRowCount);
                    return window.getButton("Close").triggerClick();
                }
            })
            .run();
    }

    // La description contient 0 caracteres (valide)
    // La description contient 250 caracteres (valide)
    public void testAddingValidCategoryDescription () throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        categoryTable = window.getTable();
                        int initialRowCount = categoryTable.getRowCount();
                        String validBudget = "10.00";
                        String validDescription1 = "";
                        String validDescription2 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam consequat, dolor laoreet fringilla iaculis, magna quam faucibus enim, quis aliquam neque enim quis mi. Aenean dui enim, accumsan vitae neque ut, laoreet tincidunt ex. Integer massa nunc.";
                        fillValuesForTest(window, "valid_description_1", validDescription1, validBudget);
                        window.getButton("Add Category").triggerClick();
                        assertEquals(categoryTable.getRowCount(), initialRowCount + 1);

                        fillValuesForTest(window, "valid_description_2", validDescription2, validBudget);
                        window.getButton("Add Category").triggerClick();
                        assertEquals(categoryTable.getRowCount(), initialRowCount + 2);
                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    /**
     * Budget
     */
    // Le champs comporte une entree numerique valide (valide)
    public void testAddingValidCategoryBudget () throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        categoryTable = window.getTable();
                        int initialRowCount = categoryTable.getRowCount();
                        String validBudget = "10.00";
                        String validDescription1 = "";
                        fillValuesForTest(window, "valid_budget_1", validDescription1, validBudget);
                        window.getButton("Add Category").triggerClick();
                        assertEquals(categoryTable.getRowCount(), initialRowCount + 1);
                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    // Le champs comporte une entree numerique trop grande (invalide)
    // Le champs est vide (invalide)
    // Le champs n'est pas numérique (invalide)
    public void testAddingInvalidCategoryBudget () throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        categoryTable = window.getTable();
                        int initialRowCount = categoryTable.getRowCount();
                        String validDescription1 = "valid description";
                        fillValuesForTest(window, "invalid_budget_1", validDescription1, "9999999999999932044674494988399999999999.00");
                        window.getButton("Add Category").triggerClick();
                        assertEquals(categoryTable.getRowCount(), initialRowCount);

                        fillValuesForTest(window, "invalid_budget_2", validDescription1, "");
                        window.getButton("Add Category").triggerClick();
                        assertEquals(categoryTable.getRowCount(), initialRowCount);

                        fillValuesForTest(window, "invalid_budget_3", validDescription1, "string");
                        window.getButton("Add Category").triggerClick();
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
