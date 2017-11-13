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

  public List<Logos> getLogos(String username) {
    return logos.stream().filter(Logos.USERNAME.equal(username)).collect(Collectors.toList());
  }

  public Logos addLogo(String piclink, String username, String result) {
    Logos newLogo = new LogosImpl();
    newLogo.setPiclink(piclink);
    newLogo.setUsername(username);
    newLogo.setTime(new Timestamp(new Date().getTime()));
    newLogo.setResult(result);
    return logos.persist(newLogo);
  }
}
