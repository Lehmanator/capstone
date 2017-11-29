package api;

import api.handler.S3Handler;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.internal.FirebaseCredentialsAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class FirebaseAuthenticator {
  private static Logger logger = Logger.getLogger(FirebaseAuthenticator.class.getName());
  private static FirebaseAuthenticator instance = null;
  private FirebaseApp firebaseApp;

  public FirebaseAuthenticator() throws IOException {
    firebaseApp = init();
  }

  public FirebaseAuth getFirebaseAuth() {
    return FirebaseAuth.getInstance(firebaseApp);
  }

  public static FirebaseAuthenticator getInstance() throws IOException {
    if (instance == null) {
      instance = new FirebaseAuthenticator();
    }
    return instance;
  }

  private FirebaseApp init() throws IOException {
    try {
      S3Handler s3Handler = new S3Handler(S3Connector.getInstance().getAmazonS3());
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(FirebaseCredentialsAdapter.fromStream(
              s3Handler.getFile(Constants.FIREBASE_FILE_NAME)))
          .build();
      return FirebaseApp.initializeApp(options);
    } catch (SQLException e) {
      logger.info(e.toString());
    }
    return null;
  }
}
