package api;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
  private static DBConnector instance = null;
  private Connection connection;
  private AmazonS3 amazonS3Service;

  public DBConnector() throws SQLException {
    connection = connectDB();
    amazonS3Service = connectAws();
  }

  public Connection getConnection() {
    return connection;
  }

  public AmazonS3 getAmazonS3() {
    return amazonS3Service;
  }

  public static DBConnector getInstance() throws SQLException{
    if (instance == null) {
      instance = new DBConnector();
    }
    return instance;
  }

  private Connection connectDB() throws SQLException {
    String dbName = System.getenv("RDS_DB_NAME");
    String userName = System.getenv("RDS_USERNAME");
    String password = System.getenv("RDS_PASSWORD");
    String hostname = System.getenv("RDS_HOSTNAME");
    String port = System.getenv("RDS_PORT");

    String url = String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s",
        hostname,
        port,
        dbName,
        userName,
        password);
    return DriverManager.getConnection(url);
  }

  private AmazonS3 connectAws() {
    return AmazonS3ClientBuilder.standard()
        .withRegion(Regions.US_EAST_1)
        .withCredentials(new EnvironmentVariableCredentialsProvider()).build();
  }
}
