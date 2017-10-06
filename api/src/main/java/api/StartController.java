package api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {
private static final String template = "Hello, world!";

@RequestMapping("/")
  public Start start() {
    return new Start(template);
  }
}
