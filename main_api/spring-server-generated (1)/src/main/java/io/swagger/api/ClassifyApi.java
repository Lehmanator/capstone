package io.swagger.api;

import io.swagger.model.Image;
import io.swagger.model.Probability;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-26T03:53:34.812Z")

@Api(value = "classify", description = "the classify API")
public interface ClassifyApi {

    @ApiOperation(value = "uploads an image for recognition", notes = "Runs recognition on image", response = Probability.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Probability classes with probability values", response = Probability.class),
        @ApiResponse(code = 400, message = "invalid input, object invalid", response = Probability.class) })
    @RequestMapping(value = "/classify",
        produces = { "application/json" }, 
        consumes = { "image/_*" },
        method = RequestMethod.POST)
    ResponseEntity<Probability> recognizeImage(@ApiParam(value = "Image to run classifier on"  ) @RequestBody Image image,
        @ApiParam(value = "authentication token for a logged in user"  ) @RequestHeader(value="user", required=false) String user);

}
