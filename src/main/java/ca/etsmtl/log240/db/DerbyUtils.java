package ca.etsmtl.log240.db;

import ca.etsmtl.log240.financej.DBUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyUtils {

    // define the driver to use
    private final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    // the database name
    private static final String dbName = "FinanceJDB";
    // define the Derby connection URL to use
    private static final String connectionURL = "jdbc:derby:" + dbName + ";create=true";

    private Connection connnection = null;

    private static DerbyUtils instance = null;

    private DerbyUtils() {
        loadDBDriver();
        createDBConnection();
    }

    public static DerbyUtils getInstance() {
        if(instance == null) {
            instance = new DerbyUtils();
        }
        return instance;
    }

    public void loadDBDriver() {
        try {
            /*
             **  Load the Derby driver.
             **     When the embedded Driver is used this action start the Derby engine.
             **  Catch an error and suggest a CLASSPATH problem
             */
           Class.forName(DRIVER).newInstance();
            System.out.println(DRIVER + " loaded. ");
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
            System.out.println("\n    >>> Please check your CLASSPATH variable   <<<\n");
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            System.out.println("\n    >>> Instantiation Exception   <<<\n");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            System.out.println("\n    >>> Illegal Access Exception   <<<\n");
            e.printStackTrace();
        }
    }


    public void createDBConnection() {
        try {
            connnection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + dbName);
        } catch (Throwable e) {
            /*       Catch all exceptions and pass them to
             **       the exception reporting method             */
            System.out.println(" . . . exception thrown:");
           // errorPrint(e);
        }
    }

    public void createDBTables() {
        String CreateStringAccount = "create table account (name varchar(50) primary key, description varchar(250))";
        String CreateStringCategory = "create table category (name varchar(50) primary key, description varchar(250)," +
                " budget float)";
        String CreateStringLedger = "create table ledger (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY " +
                "(START WITH 1, INCREMENT BY 1),rec integer, tdate date, payee  varchar(50), description varchar(250)," +
                " account varchar(50), category varchar(50), amount float)";
        Statement s;

        try {
            s = connnection.createStatement();
            if (!DBUtils.ChkTableAccount(connnection)) {
                System.out.println(" . . . . creating table account");
                s.execute(CreateStringAccount);
            }
            if (!DBUtils.ChkTableCategory(connnection)) {
                System.out.println(" . . . . creating table category");
                s.execute(CreateStringCategory);
            }
            if (!DBUtils.ChkTableLedger(connnection)) {
                System.out.println(" . . . . creating table ledger");
                s.execute(CreateStringLedger);
            }

            s.close();
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            errorPrint(e);
        }
    }

    public void shutdownDB() {
        try {
            connnection.close();
            System.out.println("Closed connection");
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            errorPrint(e);
        }

        /*** In embedded mode, an application should shut down Derby.
        Shutdown throws the XJ015 exception to confirm success. ***/
        if (DRIVER.equals("org.apache.derby.jdbc.EmbeddedDriver")) {
            boolean gotSQLExc = false;
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
                if (se.getSQLState().equals("XJ015")) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            } else {
                System.out.println("Database shut down normally");
            }
        }
    }

      //   ## DERBY EXCEPTION REPORTING CLASSES  ##
    /***     Exception reporting methods
     **      with special handling of SQLExceptions
     ***/
    private void errorPrint(Throwable e) {
        if (e instanceof SQLException) {
            SQLExceptionPrint((SQLException) e);
        } else {
            System.out.println("A non SQL error occured.");
            e.printStackTrace();
        }

    }  // END errorPrint

    //  Iterates through a stack of SQLExceptions
    private void SQLExceptionPrint(SQLException sqle) {
        while (sqle != null) {
            System.out.println("\n---SQLException Caught---\n");
            System.out.println("SQLState:   " + (sqle).getSQLState());
            System.out.println("Severity: " + (sqle).getErrorCode());
            System.out.println("Message:  " + (sqle).getMessage());
            sqle.printStackTrace();
            sqle =
                    sqle.getNextException();
        }
    }  //  END SQLExceptionPrint

    public Connection getConnection() {
        return connnection;
    }

    public void setConnnection(Connection connnection) {
        this.connnection = connnection;
    }
}
