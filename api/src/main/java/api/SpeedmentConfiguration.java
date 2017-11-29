package api;

import api.db.CapitalfunApplication;
import api.db.CapitalfunApplicationBuilder;
import api.db.applications.ApplicationsManager;
import api.db.logos.LogosManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration file for Speedment
 * @author mayank
 */
@Configuration
public class SpeedmentConfiguration {
  private String schema = System.getenv("RDS_DB_NAME_CAP");
  private String username = System.getenv("RDS_USERNAME");
  private String password = System.getenv("RDS_PASSWORD");
  private String host = System.getenv("RDS_HOSTNAME");
  private int port = Integer.parseInt(System.getenv("RDS_PORT"));

  @Bean
  public CapitalfunApplication getCapitalfunApplication() {
    return new CapitalfunApplicationBuilder()
        .withIpAddress(host)
        .withPort(port)
        .withUsername(username)
        .withPassword(password)
        .withSchema(schema)
        .build();
  }

  @Bean
  public LogosManager getLogosManager(CapitalfunApplication app) {
    return app.getOrThrow(LogosManager.class);
  }

  @Bean
  public ApplicationsManager getApplicationsManager(CapitalfunApplication app) {
    return app.getOrThrow(ApplicationsManager.class);
  }
}