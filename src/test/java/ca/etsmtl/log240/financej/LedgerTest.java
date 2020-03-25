package ca.etsmtl.log240.financej;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;


public class LedgerTest extends FinancejAbstractTest {

    private String validCategoryName = "Épicerie";

    protected void setUp() throws Exception {
        super.setUp();
        addNecessaryFieldsForTests();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testValidLedger () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", "Bonne description", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount + 1);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testBlankDate () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "", "Roger", "Bonne description", "150.00", false, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testWrongDateFormat () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "11-12-1999", "Roger", "Bonne description", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testCharacterInDate () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-1f", "Roger", "Bonne description", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testDayLessThan1() {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-00", "Roger", "Bonne description", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testDayGreaterThan31() {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-32", "Roger", "Bonne description", "150.00", false, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testMonthLessThan1() {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-00-11", "Roger", "Bonne description", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testMonthMoreThan12() {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-13-11", "Roger", "Bonne description", "150.00", false, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testBisextileOutOfBound() {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "2007-02-29", "Roger", "Bonne description", "150.00", false, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testDayInMonthOutOfBound () {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-11-31", "Roger", "Bonne description", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testBlankPayee () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "", "Bonne description", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testLessThan2CharactersPayee () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "R", "Bonne description", "150.00", false, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testOver50CharactersPayee () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", new String(new char[51]).replace("\0", "a"), "Bonne description", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testBlankDescription () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", "", "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testLessThan3CharactersDescription () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", "Lo", "150.00", false, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testOver250CharactersDescription () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", new String(new char[251]).replace("\0", "a"), "150.00", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testBlankCategory () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", "Bonne description", "150.00", false, "");
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testBlankAmount () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", "Bonne description", "", false, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testCharacterInAmount () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", "Bonne description", "15d.ur", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testAmountIs0 () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", "Bonne description", "0", true, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    public void testAmountIsGreaterThan64Bit () throws Exception {
        WindowInterceptor.init(ledgerButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        ledgerTable = window.getTable();
                        int initialRowCount = ledgerTable.getRowCount();

                        fillValuesForTest(window, "1999-12-11", "Roger", "Bonne description", "999999999999999999999999999999999999999999999.00", false, validCategoryName);
                        window.getButton("Add Transaction").click();

                        assertEquals(ledgerTable.getRowCount(), initialRowCount);

                        return window.getButton("Close").triggerClick();
                    }
                })
                .run();
    }

    private void addNecessaryFieldsForTests() {
        WindowInterceptor.init(accountsButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        window.getTextBox("NAME_TEXT_FIELD").setText("Roger");
                        window.getTextBox("DESC_TEXT_FIELD").setText("Bonne Description");
                        window.getButton("Add Account").click();

                        return window.getButton("Close").triggerClick();
                    }
                }).run();

        WindowInterceptor.init(categoriesButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        window.getTextBox("NAME_TEXT_FIELD").setText(validCategoryName);
                        window.getTextBox("DESC_TEXT_FIELD").setText("Bonne Description");
                        window.getTextBox("BUDG_DOUBLE_FIELD").setText("1000.00");
                        window.getButton("Add Category").click();

                        return window.getButton("Close").triggerClick();
                    }
                }).run();
    }

    private void fillValuesForTest (Window window, String date, String payee, String description, String amount,
                                    boolean recValue, String category) {
        window.getTextBox("DATE_TEXT_FIELD").setText(date);
        window.getTextBox("PAYEE_TEXT_FIELD").setText(payee);
        window.getTextBox("DESC_TEXT_FIELD").setText(description);
        window.getTextBox("AMOUNT_DOUBLE_FIELD").setText(amount);

        if (recValue)
            window.getCheckBox("REC_BOOL_FIELD").select();

        window.getComboBox("CATEGORY_TEXT_FIELD").select(category);
    }

}
