 package io.psol.tbtb.tbtb.controller;

import io.psol.tbtb.tbtb.model.TBModel;
import io.psol.tbtb.tbtb.service.TBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.cloud.vision.v1.*;

import java.util.ArrayList;
import java.util.List;

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

            System.out.println("Text : ");
            System.out.printf("requests: %s\n", requests);
//            System.out.printf("response: %s\n", response);
////            System.out.printf("responses: %s\n", responses);

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                List<EntityAnnotation> annotations = res.getTextAnnotationsList();
                System.out.println("for문 : ");
                for (EntityAnnotation annotation : annotations){
                    System.out.println("##getDescription() : " + annotation.getDescription());
                    System.out.println("##getLocale() : " + annotation.getLocale());
                    System.out.println("##getMid() : " + annotation.getMid());
                    System.out.println("##getXY : " + annotation.getBoundingPoly());
                    System.out.println(annotation.getBoundingPoly().getVerticesCount());
//                    for (int i = 0; i < annotation.getBoundingPoly().getVerticesCount(); i++) {
//                        System.out.println("~~getX : " + annotation.getBoundingPoly().getVertices(i).getX());
//                        System.out.println("~~getY : " + annotation.getBoundingPoly().getVertices(i).getY());
//                    }
//                    for (Vertex vertex : annotation.getBoundingPoly().getVerticesList()) {
//                        System.out.println("~~getX : " + vertex.getX());
//                        System.out.println("~~getY : " + vertex.getY());
//                    }
                    System.out.println("##toString() : " + annotation.toString());



                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
			    	/*for (EntityAnnotation annotation : res.getTextAnnotationsList()) {

						//System.out.printf("Text: %s\n", annotation.getDescription());
						//System.out.printf("Position : %s\n", annotation.getBoundingPoly());
					}*/
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
