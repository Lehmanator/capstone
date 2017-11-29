package api.handler;

import api.db.logos.Logos;
import api.db.logos.LogosImpl;
import api.db.logos.LogosManager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LogoHandler {
  private LogosManager logos;

  public LogoHandler(LogosManager logos) {
    this.logos = logos;
  }

  public List<Logos> getLogos(String userId) {
    return logos.stream().filter(Logos.USER_ID.equal(userId)).collect(Collectors.toList());
  }

  public void addLogo(String piclink, String userId, String result) {
    Logos newLogo = new LogosImpl();
    newLogo.setPiclink(piclink);
    newLogo.setUserId(userId);
    newLogo.setTime(new Timestamp(new Date().getTime()));
    newLogo.setResult(result);
    logos.persist(newLogo);
  }
}
