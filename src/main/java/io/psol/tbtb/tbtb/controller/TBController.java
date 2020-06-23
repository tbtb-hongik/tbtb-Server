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
    public @ResponseBody String test(@RequestParam("url") String url, @RequestParam("os") String os) {
        // url 은 받은 데이터
        System.out.println(os + " URL : \n" + url);

        // AI api 처리된 데이터
        String TTS = "안녕하세요! 스니커즈빌딩입니다. 스니커즈빌딩은 2020년 3월 개업한 소규모 슈즈 전문 스토어 입니다. 판매하는 모든 제품은 100% 정품임을 약속드리며 좋은 물건 합리적인 가격에 제공해드리는 스니커즈빌딩이 되도록 노력하겠습니다. 감사합니다.";

        // DB 저장
        TBModel image = new TBModel();
        image.setOs(os);
        image.setUrl(url);
        image.setResult(TTS);
        tbService.insert(image);

        return TTS;
    }

    @RequestMapping(value = "/ios", method = RequestMethod.POST)
    public @ResponseBody String iOS(@RequestParam("url") String url, @RequestParam("os") String os) {
        // url 은 받은 데이터
        System.out.println(os + " URL : \n" + url);

        // AI api 처리된 데이터
        String TTS = "iOS";

        // DB 저장
        TBModel image = new TBModel();
        image.setOs(os);
        image.setUrl(url);
        image.setResult(TTS);
        tbService.insert(image);

        return TTS;
    }
}
