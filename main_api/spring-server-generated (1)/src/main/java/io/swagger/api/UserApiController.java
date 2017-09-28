package io.swagger.api;

import io.swagger.model.Definitionshistory;
import io.swagger.model.Newuser;
import io.swagger.model.User;

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
public class UserApiController implements UserApi {

    public ResponseEntity<User> createUser(@ApiParam(value = "The user's name"  ) @RequestBody Newuser user) {
        // do some magic!

        return new ResponseEntity<User>(HttpStatus.OK);
    }

    public ResponseEntity<Definitionshistory> getHistory(@ApiParam(value = "", required = true) @RequestParam(value = "uid", required = true) String uid) {
        // do some magic!
        return new ResponseEntity<Definitionshistory>(HttpStatus.OK);
    }

    public ResponseEntity<User> getUser(@ApiParam(value = "authentication token for a logged in user"  ) @RequestHeader(value="user", required=false) String user) {
        // do some magic!
        return new ResponseEntity<User>(HttpStatus.OK);
    }

}
