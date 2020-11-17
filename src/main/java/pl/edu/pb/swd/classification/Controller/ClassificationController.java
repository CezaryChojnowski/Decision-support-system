package pl.edu.pb.swd.classification.Controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.pb.swd.classification.Service.MetricService;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/classification")
public class ClassificationController {

    final MetricService metricService;

    final ReadWriteService readWriteService;

    public ClassificationController(MetricService metricService, ReadWriteService readWriteService) {
        this.metricService = metricService;
        this.readWriteService = readWriteService;
    }

    @RequestMapping(value = "/numericHeaders", method= RequestMethod.GET)
    public List<String> getNumericHeaders(){
       return metricService.getNumericHeaders();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String decisionClass(@RequestParam Integer neighbors,
                                 @RequestParam Integer method,
                                 @RequestBody LinkedList<String> newObject){
        return metricService.getDecisionClass(neighbors, method, newObject);
    }
}
