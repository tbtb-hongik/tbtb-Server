package io.psol.tbtb.tbtb.controller;

import io.psol.tbtb.tbtb.model.TBModel;
import io.psol.tbtb.tbtb.service.TBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TBController {
    @Autowired
    TBService tbService;

    @RequestMapping(value = "/android", method = RequestMethod.POST)
    public @ResponseBody
    String test(@RequestParam("url") String url, @RequestParam("os") String os) {
        // url 은 받은 데이터
        System.out.println(os + " URL : \n" + url);

        // AI api 처리된 데이터
        String TTS = "초절정 귀요미 민이, Cute cute 별이";

        // DB 저장
        TBModel image = new TBModel();
        image.setOs(os);
        image.setUrl(url);
        image.setResult(TTS);
        tbService.insert(image);

        return TTS;
    }
}
