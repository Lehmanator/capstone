package api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created by sushrutshringarputale on 10/10/17.
 */
@RestController
public class UploadLogoController {
    @RequestMapping(value = "/recognition", method = RequestMethod.POST)
    public ApiResponse upload (
            @RequestParam("image")MultipartFile image
            ) {
        
        return null;
    }

}
