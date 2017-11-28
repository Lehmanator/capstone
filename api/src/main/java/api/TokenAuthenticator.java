package api;

import api.response.ApiResponse;
import api.response.Error;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;

public class TokenAuthenticator {
  private static Logger logger = Logger.getLogger(TokenAuthenticator.class.getName());

  public static String verifyToken(String token) {
    try {
      FirebaseAuthenticator auth = FirebaseAuthenticator.getInstance();
      FirebaseToken decodedToken = auth.getFirebaseAuth().verifyIdTokenAsync(token).get();
      return decodedToken.getUid();
    } catch (InterruptedException|ExecutionException|IOException e) {
      e.printStackTrace();
      logger.info(e.getMessage());
      return null;
    }
  }

  public static ApiResponse getAuthenticationErrorResponse() {
    return new Error(HttpStatus.UNAUTHORIZED,
        "{\"message\":\"User is not authorized.\"}");
  }
}
