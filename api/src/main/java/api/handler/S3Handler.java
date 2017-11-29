package api.handler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import java.io.InputStream;
import java.sql.Connection;

import java.util.logging.Logger;

public class S3Handler {
  private static final Logger logger = Logger.getLogger(S3Handler.class.getName());
  private static final String BUCKET_NAME = "capitalfun-images";
  private static final String BASE_URL =
      "https://s3.us-east-2.amazonaws.com/capitalfun-images/%s";

  private Connection connection;
  private AmazonS3 awsClient;

  public S3Handler(AmazonS3 awsClient) {
    this.awsClient = awsClient;
  }

  public void uploadImage(
      String userName,
      String name,
      InputStream stream,
      ObjectMetadata metadata) throws AmazonS3Exception {
    String fileName = createFileName(userName, name);
//        InitiateMultipartUploadResult result = awsClient.initiateMultipartUpload(new InitiateMultipartUploadRequest(BUCKET_NAME, fileName, metadata));
//        UploadPartResult result1 = awsClient.uploadPart(new UploadPartRequest().withUploadId(result.getUploadId()).withInputStream(stream));
//        CompleteMultipartUploadResult finalResult = awsClient.completeMultipartUpload(new CompleteMultipartUploadRequest(BUCKET_NAME, name, result.getUploadId(), Arrays.asList(result1.getPartETag())));
    AccessControlList acl = new AccessControlList();
    acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
    awsClient.putObject(
        new PutObjectRequest(
            BUCKET_NAME,
            fileName,
            stream,
            metadata).withAccessControlList(acl));
//        awsClient.setObjectAcl(BUCKET_NAME, fileName, CannedAccessControlList.PublicRead);
//        return finalResult.getLocation();
  }

  public InputStream getFile(String fileName) throws AmazonS3Exception {
    S3Object object = awsClient.getObject(BUCKET_NAME, fileName);
    return object.getObjectContent();
  }

  public String getImageUrl(String userName, String name) {
    return String.format(BASE_URL, createFileName(userName, name));
  }

  private String createFileName(String userName, String name) {
    String fileParams[] = name.split("\\.");
    return userName + Integer.toString(fileParams[0].hashCode()) + "." + fileParams[1];
  }
}
