package api;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartController {

    private static final String template = "Hello, world!";

    @RequestMapping("/")
    public Start greeting() {
        return new Start(template);
    }
}
