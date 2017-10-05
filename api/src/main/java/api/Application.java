package api;

import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws SQLException {
      System.out.println(System.getenv("RDS_DB_NAME"));
      //DBConnector connector = DBConnector.getInstance();
      //DBHandler handler = new DBHandler(connector.getConnection(), connector.getAmazonS3());
      //SpringApplication.run(Application.class, args);
    }
}
