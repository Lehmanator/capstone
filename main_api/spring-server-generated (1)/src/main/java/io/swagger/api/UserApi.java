package io.swagger.api;

import io.swagger.model.Definitionshistory;
import io.swagger.model.Newuser;
import io.swagger.model.User;

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

@Api(value = "user", description = "the user API")
public interface UserApi {

    @ApiOperation(value = "create a new user", notes = "Creates a new user in the database", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User data", response = User.class),
        @ApiResponse(code = 400, message = "Bad parameters", response = User.class),
        @ApiResponse(code = 500, message = "Server/Database error", response = User.class) })
    @RequestMapping(value = "/user",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<User> createUser(@ApiParam(value = "The user's name"  ) @RequestBody Newuser user);


    @ApiOperation(value = "returns the image recognition history for a user", notes = "returns the image recognition history for a user", response = Definitionshistory.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Image recognition history for user", response = Definitionshistory.class),
        @ApiResponse(code = 400, message = "Bad parameters", response = Definitionshistory.class),
        @ApiResponse(code = 404, message = "User not found", response = Definitionshistory.class),
        @ApiResponse(code = 500, message = "Internal Server/Database error", response = Definitionshistory.class) })
    @RequestMapping(value = "/user/{uid}/history",
        produces = { "application/json" }, 
        consumes = { "string" },
        method = RequestMethod.GET)
    ResponseEntity<Definitionshistory> getHistory(@ApiParam(value = "", required = true) @RequestParam(value = "uid", required = true) String uid);


    @ApiOperation(value = "returns the data for the current logged in user", notes = "returns the data for the current user", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User data", response = User.class),
        @ApiResponse(code = 400, message = "Bad request. No user string provided", response = User.class),
        @ApiResponse(code = 404, message = "User not found", response = User.class) })
    @RequestMapping(value = "/user",
        produces = { "application/json" }, 
        consumes = { "string" },
        method = RequestMethod.GET)
    ResponseEntity<User> getUser(@ApiParam(value = "authentication token for a logged in user"  ) @RequestHeader(value="user", required=false) String user);

}
