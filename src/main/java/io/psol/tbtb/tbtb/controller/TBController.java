package io.psol.tbtb.tbtb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class TBController {
    @RequestMapping(value = "/android", method = RequestMethod.POST)
    public @ResponseBody String test(@RequestParam("url") HashMap<String, String> url){
        // url 은 받은 데이터
        String imgUrl = url.get(url.keySet());
        System.out.println(url + "\n/android Requested URL : \n" + imgUrl);

        // AI api 처리된 데이터
        String TTS = "초절정 귀요미 말티즈 민이, Cute cute 별이";
        
        return TTS;
    }
}
