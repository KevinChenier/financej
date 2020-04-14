package ca.etsmtl.log240.financej;
import org.uispec4j.Table;
import org.uispec4j.Trigger;
import org.uispec4j.Window;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

public class CategoryDialogTest extends FinancejAbstractTest {
    private Table categoryTable;
    private final int MAX_SIZE_NAME= 50;
    private final int MAX_SIZE_DESCRIPTION= 250;
    private final int MAX_SIZE_BUDGET= 64;

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreerCategoryValideBudgetDecimal() throws Exception {
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

    public void testCreerCategoryValideBudgetSimple() throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        categoryTable = window.getTable();
                        int initialRowCount = categoryTable.getRowCount();
                        final String NAME_INPUT = "Name";
                        final String BUDGET_INPUT = "10";
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

    public void testCreerCategoryNomVide() throws Exception {
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

    public void testCreerCategoryBudgetVide() throws Exception {
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

    public void testCreerCategoryDescriptionVide() throws Exception {
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
                        final String NAME_INPUT = "Name";
                        final String BUDGET_INPUT = "10.00";
                        final String DESCRIPTION_INPUT = bufferChaine.toString();
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

    public void testCreerCategoryBudgetMore64Bits() throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        categoryTable = window.getTable();
                        int initialRowCount = categoryTable.getRowCount();

                        StringBuffer bufferChaine = new StringBuffer(MAX_SIZE_BUDGET + 1);
                        for (int i = 0; i < MAX_SIZE_BUDGET + 1 + 1; i++) {
                            bufferChaine.append("5");
                        }
                        final String NAME_INPUT = "Name";
                        // huge budget > 64 bits
                        final String BUDGET_INPUT = bufferChaine.toString();
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

    public void testCreerCategoryBudgetPasNumerique() throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        categoryTable = window.getTable();
                        int initialRowCount = categoryTable.getRowCount();
                        final String NAME_INPUT = "Name";
                        // budget contenant caractères non numériques
                        final String BUDGET_INPUT = "5 dollars";
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
    public void testDeleteCategory() throws Exception {
        WindowInterceptor.init(categoriesButton.triggerClick())
                .process(new WindowHandler() {
                    public Trigger process(Window window) {
                        // setup
                        categoryTable = window.getTable();
                        int initialRowCount = categoryTable.getRowCount();
                        final String NAME_INPUT = "Name";
                        final String BUDGET_INPUT = "10";
                        final String DESCRIPTION_INPUT = "Lorem ipsum dolor.";
                        fillValuesForTest(window, NAME_INPUT, DESCRIPTION_INPUT, BUDGET_INPUT);
                        window.getButton("Add Category").click();
                        // delete category
                        window.getButton("Delete Category");
                        assertEquals(initialRowCount, categoryTable.getRowCount());
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
