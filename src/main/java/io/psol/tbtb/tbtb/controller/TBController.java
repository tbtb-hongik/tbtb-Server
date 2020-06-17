package io.psol.tbtb.tbtb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TBController {
    @RequestMapping(value = "/android", method = RequestMethod.POST)
    public @ResponseBody String test(@RequestParam("url") String url){
        // url 은 받은 데이터
        System.out.println("\n/android Requested URL : \n" + url);

        // AI api 처리된 데이터
        String TTS = url;
        return TTS;
    }
}
