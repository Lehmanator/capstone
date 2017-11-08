package api;

import api.handler.S3Handler;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.sql.SQLException;
import java.util.logging.Logger;

public class S3Connector {
  private static Logger logger = Logger.getLogger(S3Connector.class.getName());
  private static S3Connector instance = null;
  private AmazonS3 amazonS3Service;

  public S3Connector() throws SQLException {
    amazonS3Service = connectAws();
  }

  public AmazonS3 getAmazonS3() {
    return amazonS3Service;
  }

  public static S3Connector getInstance() throws SQLException {
    if (instance == null) {
      instance = new S3Connector();
    }
    return instance;
  }

  private AmazonS3 connectAws() {
    return AmazonS3ClientBuilder.standard()
        .withRegion(Regions.US_EAST_2)
        .withCredentials(new EnvironmentVariableCredentialsProvider()).build();
  }
}
