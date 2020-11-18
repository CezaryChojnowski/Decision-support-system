package pl.edu.pb.swd.classification.Controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.pb.swd.classification.Model.QualityRating;
import pl.edu.pb.swd.classification.Service.ClassificationQualityRatingService;
import pl.edu.pb.swd.classification.Service.MetricService;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/classification")
public class ClassificationController {

    final MetricService metricService;

    final ReadWriteService readWriteService;

    final ClassificationQualityRatingService classificationQualityRatingService;

    public ClassificationController(MetricService metricService, ReadWriteService readWriteService, ClassificationQualityRatingService classificationQualityRatingService) {
        this.metricService = metricService;
        this.readWriteService = readWriteService;
        this.classificationQualityRatingService = classificationQualityRatingService;
    }

    @RequestMapping(value = "/numericHeaders", method= RequestMethod.GET)
    public List<String> getNumericHeaders(){
       return metricService.getNumericHeaders();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String decisionClass(@RequestParam Integer neighbors,
                                 @RequestParam Integer method,
                                 @RequestBody LinkedList<String> newObject) throws IOException {
        return metricService.getDecisionClass(neighbors, method, newObject);
    }

    @RequestMapping(value = "/qualityRating", method = RequestMethod.GET)
    public List<QualityRating> classificationQualityRating(@RequestParam Integer method){
        return classificationQualityRatingService.getClassificationQualityRating(method);
    }
}
