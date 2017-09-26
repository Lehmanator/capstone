package io.swagger.api;

import io.swagger.model.Image;
import io.swagger.model.Probability;

import io.swagger.annotations.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-26T03:53:34.812Z")

@Controller
public class ClassifyApiController implements ClassifyApi {

    public ResponseEntity<Probability> recognizeImage(@ApiParam(value = "Image to run classifier on"  ) @RequestBody Image image,
        @ApiParam(value = "authentication token for a logged in user"  ) @RequestHeader(value="user", required=false) String user) {
        // do some magic!
        return new ResponseEntity<Probability>(HttpStatus.OK);
    }

}
