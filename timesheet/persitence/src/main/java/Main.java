import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/timesheet?autoReconnect=true&useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating database...");
           /* stmt = conn.createStatement();
            stmt.execute( "CREATE TABLE category " +
                    "(id VARCHAR(255) not NULL , " +
                    " name VARCHAR(255), " +
                    " PRIMARY KEY ( id ))");*/
           /* JdbcCategoryRepository jdbcCategoryRepository = new JdbcCategoryRepository(conn);
           JdbcClientRepository jdbcClientRepository = new JdbcClientRepository(conn);
            JdbcEmployeeRepository jdbcEmployeeRepository = new JdbcEmployeeRepository(conn);
            JdbcDailyTimeSheetRepository jdbcDailyTimeSheetRepository = new JdbcDailyTimeSheetRepository(conn);
            JdbcProjectRepository jdbcProjectRepository = new JdbcProjectRepository(conn);*/
            // jdbcCategoryRepository.add(new Category(UUID.randomUUID(), "FRONTEND"));
            // Category cat = new Category(UUID.randomUUID(), "BAACKEND");
            // jdbcCategoryRepository.findBy("pera", 'd', 1,2);
            // jdbcCategoryRepository.remove(UUID.fromString("596e9381-5259-4217-93bb-267572ee7529"));
            // jdbcCategoryRepository.removeByName("MIKAssA");
            //jdbcCategoryRepository.findAll();
            //System.out.println("Database created successfully...");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end JDBCExample
