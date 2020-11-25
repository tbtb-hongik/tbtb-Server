 package io.psol.tbtb.tbtb.controller;

import io.psol.tbtb.tbtb.model.TBModel;
import io.psol.tbtb.tbtb.service.TBService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.cloud.vision.v1.*;

import java.util.*;


 @Controller
public class TBController {
    @Autowired
    TBService tbService;

    @RequestMapping(value = "/android", method = RequestMethod.POST)
    public @ResponseBody String Android(@RequestParam("url") String url, @RequestParam("os") String os) {
        // url 은 받은 데이터
        System.out.println(os + " URL : \n" + url);
        analysisImage(url);

        // AI api 처리된 데이터
        String TTS = "";

        // DB 저장
//        TBModel image = new TBModel();
//        image.setOs(os);
//        image.setUrl(url);
//        image.setResult(TTS);
//        tbService.insert(image);

        return TTS;
    }

    @RequestMapping(value = "/ios", method = RequestMethod.POST)
    public @ResponseBody String iOS(@RequestParam("url") String url, @RequestParam("os") String os) {
        // url 은 받은 데이터
        System.out.println(os + " URL : \n" + url);

        // AI api 처리된 데이터
        String TTS = "iOS";

        // DB 저장
//        TBModel image = new TBModel();
//        image.setOs(os);
//        image.setUrl(url);
//        image.setResult(TTS);
//        tbService.insert(image);

        return TTS;
    }

    public void analysisImage(String url) {
        ImageSource imgUri = ImageSource.newBuilder().setImageUri(url).build();
        Image img = Image.newBuilder().setSource(imgUri).build();

        Feature detectLabelFeat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        Feature detectTextFeat = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();
        Feature detectObject = Feature.newBuilder().setType(Feature.Type.OBJECT_LOCALIZATION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(detectLabelFeat).addFeatures(detectTextFeat).addFeatures(detectObject).setImage(img).build();

        List<AnnotateImageRequest> requests = new ArrayList<>();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }
                //Text annotation
                ArrayList<Integer> TextInfoList = getText(res.getTextAnnotationsList());
                //Label
//                ArrayList<Integer> LabelInfoList = getLabel(res.getLabelAnnotationsList());
                
                System.out.printf("test : %s\n", res.getFullTextAnnotation().getText());
                System.out.printf("test2 : %s\n ", res.getLabelAnnotationsList());
                System.out.printf("test2 : %s\n ", res.getLocalizedObjectAnnotationsList());

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getArea(ArrayList<Pair> list){
        int a = 0;
        int b = 0;
        for(int i=0; i<list.size()-1; i++) {
            Pair X = list.get(i);
            Pair Y = list.get(i+1);

            a += X.xInt*Y.yInt;
            b += X.yInt*Y.xInt;
        }
        return Math.abs(a-b);
    }

    public ArrayList<Integer> getText(List<EntityAnnotation> annotations){
        ArrayList<Pair> IdxAreaInfo = new ArrayList<Pair>();
        ArrayList<Pair> getPairList = new ArrayList<Pair>();
        for (int i = 0; i < annotations.size(); i++){
            //현재 annotations 원소의 크기
            int annotaionSize = annotations.get(i).getBoundingPoly().getVerticesCount();

            //리스트 초기화
            getPairList.clear();
            //ccw 넓이 구하기, 총 4개의 원소에서 3개만 필요
            for (int j = 0; j < annotaionSize - 1; j++) {
                int nowX = annotations.get(i).getBoundingPoly().getVertices(j).getX();
                int nowY = annotations.get(i).getBoundingPoly().getVertices(j).getY();
                getPairList.add(new Pair(nowX, nowY));
            }
            //ccw 첫번째 원소 재추가
            getPairList.add(getPairList.get(0));
            //(넓이, 인덱스) 순으로 IdxAreaInfo 리스트에 삽입
            int boxArea = getArea(getPairList);
            IdxAreaInfo.add(new Pair(boxArea, i));
        }
        //내림차순 정렬
        Collections.sort(IdxAreaInfo, new DescendingInt());
        //인덱스 최대 5순위 까지 저장
        ArrayList<Integer> IdxInfoList = new ArrayList<Integer>();
        for (int count = 0; count < 5 && count < IdxAreaInfo.size(); count++){
            IdxInfoList.add(IdxAreaInfo.get(count).yInt);
        }

        if (IdxInfoList.size() == 0){
            //IdxInfoList의 크기가 0일 때
        }
        else{
//            for (int i =0; i < IdxInfoList.size(); i++){
//                System.out.println(IdxInfoList.get(i));
//                int tmpIdx = IdxInfoList.get(i);
//                System.out.println(annotations.get(tmpIdx).getDescription());
//            }
            //IdxInfoList의 크기가 0이 아닐 때
        }

        return IdxInfoList;
    }

//    public ArrayList<Integer> getLabel(List<EntityAnnotation> annotations){
//
//        return
//    }


     public ArrayList<String> getObjectName(List<LocalizedObjectAnnotation> annotations){
        HashMap<String, Boolean> checkObjNameMap = new HashMap<>();
        ArrayList<String> infoList = new ArrayList<>();
        for (int i = 0; i < annotations.size(); i++){
            String name = annotations.get(i).getName();
            if (checkObjNameMap.containsKey(name)){
                //이름이 이미 있을 때
                //do nothing
            }
            else{
                //이름이 없을 때
                checkObjNameMap.put(name, true);
                infoList.add(name);
            }
        }
        ArrayList<String> retInfoList = new ArrayList<>();
         for (int count = 0; count < 5 && count < retInfoList.size(); count++){
             retInfoList.add(retInfoList.get(count));
         }
        return retInfoList;
     }


}

class Pair{
     int xInt, yInt;

     public Pair(int x, int y){
         this.xInt = x;
         this.yInt = y;
     }
}


class DescendingInt implements Comparator<Pair>{
     public int compare(Pair a, Pair b)
     {
         return b.xInt - a.xInt;
     }
 }