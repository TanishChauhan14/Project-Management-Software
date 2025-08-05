package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class AuthController {


// Testing Api
@GetMapping("/")
public String Test() {
    return "Hello this is just testing!";
}


}
