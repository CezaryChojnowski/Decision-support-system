package pl.edu.pb.swd.classification.Controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.pb.swd.classification.Service.MetricService;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
       LinkedList<String> lista1 = metricService.getNumericHeaders();
       System.out.println(lista1);
        LinkedList<Integer> lista2 = metricService.getIndexesNumericColumn(lista1);
        System.out.println(lista2);
        LinkedList<String> nowalista = new LinkedList<>();
        nowalista.add("2.3");
        nowalista.add("2.3");
        nowalista.add("2.3");
        nowalista.add("2.3");
        Map<Integer, Double> integerDoubleMap = metricService.createMapWithIndexAndValueNewObject(lista2, nowalista);
        System.out.println(integerDoubleMap);
//        LinkedList<Double> lista3 = metricService.getVectorOfDistance(integerDoubleMap);
//        System.out.println(lista3);
        return null;
    }

    @RequestMapping(value = "/object", method = RequestMethod.POST)
    public String classifyObject(@RequestBody List<String> newObject,
                                 @RequestParam Integer numberOfNeighbors,
                                 @RequestParam Integer metrics){
        return null;
    }
}
