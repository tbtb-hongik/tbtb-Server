package io.psol.tbtb.tbtb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TBController {
    @RequestMapping(value = "/android", method = RequestMethod.POST)
    public @ResponseBody String test(@RequestParam("url") String url){
        System.out.println("/android Requested...\nAI api 처리" + url);
        return "api 처리된 String, " + url;
    }
}
