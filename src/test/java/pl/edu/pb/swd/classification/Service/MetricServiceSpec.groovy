package pl.edu.pb.swd.classification.Service

import pl.edu.pb.swd.dataOperations.Service.ReadWriteService
import spock.lang.Specification
import static java.util.Arrays.*;

class MetricServiceSpec extends Specification {
    ReadWriteService readWriteService;
    MetricService metricService

    def setup() {
        readWriteService = Stub(ReadWriteService)
        metricService = new MetricService(readWriteService);
    }

    def "Should return list with names of only numeric column"() {
        given: "Defined data to call method"
        LinkedList<LinkedList<String>> listOfLists = asList(
                asList("LISDLG", "LISSZE", "PLADLG", "PLASZE", "ODMIRYS") as LinkedList<String>,
                asList("5.0", "3.3", "1.4", "0.2", "SETOSA") as LinkedList<String>,
                asList("6.4", "2.8", "5.6", "2.2", "VIRGINIC") as LinkedList<String>);
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> listOfLists
        when: "Try get list with names of only numeric column"
        LinkedList<String> result = metricService.getNumericHeaders();
        then: "Result should be list with names of only numeric column"
        result == asList("LISDLG", "LISSZE", "PLADLG", "PLASZE")
    }

    def "Should return list with index of only numeric column"() {
        given: "Defined data to call method"
        LinkedList<LinkedList<String>> listOfLists = asList(
                asList("LISDLG", "LISSZE", "PLADLG", "PLASZE", "ODMIRYS") as LinkedList<String>,
                asList("5.0", "3.3", "1.4", "0.2", "SETOSA") as LinkedList<String>,
                asList("6.4", "2.8", "5.6", "2.2", "VIRGINIC") as LinkedList<String>);
        LinkedList<String> temp5 = asList("LISDLG", "LISSZE", "PLADLG", "PLASZE");
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> listOfLists
        when: "Try get list with index of only numeric column"
        LinkedList<Integer> result = metricService.getIndexesNumericColumn(temp5)
        then: "Result should be list with index of only numeric column"
        result == asList(0, 1, 2, 3)
    }

    def "Should return map with index and name of only numeric column"() {
        given: "Defined data to call method"
        LinkedList<Integer> indexes = asList(0, 1, 2, 3);
        LinkedList<String> values = asList("1.5", "2.5", "3.5", "4.5");
        Map<Integer, Double> integerDoubleMap = new HashMap<Integer, Double>() {
            {
                put(0, 1.5);
                put(1, 2.5);
                put(2, 3.5);
                put(3, 4.5);
            }
        }
        when: "Try get map with index and names of only numeric column"
        Map<Integer, Double> result = metricService.createMapWithIndexAndValueNewObject(indexes, values);
        then: "Result should be map with index and name of only numeric column"
        result == integerDoubleMap;
    }
}