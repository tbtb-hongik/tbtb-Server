package io.psol.tbtb.tbtb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TBController {
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public @ResponseBody String test(@RequestParam("name") String name){
        System.out.println("/test Requested...");
        return "TEST SERVER RUNNING. You are free to use this server, " + name;
    }
}
