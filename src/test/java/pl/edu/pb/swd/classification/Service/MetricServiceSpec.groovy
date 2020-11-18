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
        def testData = getTestData()
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> testData
        when: "Try get list with names of only numeric column"
        LinkedList<String> result = metricService.getNumericHeaders()
        then: "Result should be list with names of only numeric column"
        result.equals(asList("LISDLG", "LISSZE", "PLADLG", "PLASZE"))
    }

    def "Should return list with index of only numeric column"() {
        given: "Defined data to call method"
        def testData = getTestData();
        LinkedList<String> temp5 = asList("LISDLG", "LISSZE", "PLADLG", "PLASZE");
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> testData
        when: "Try get list with index of only numeric column"
        LinkedList<Integer> result = metricService.getIndexesNumericColumn(temp5)
        then: "Result should be list with index of only numeric column"
        result.equals(asList(0, 1, 2, 3))
    }

    def "Should return map with index and name of only numeric column"() {
        given: "Defined data to call method"
        LinkedList<Integer> indexes = asList(0, 1, 2, 3);
        LinkedList<String> values = asList("1.5", "2.5", "3.5", "4.5")
        def newObject = getMapWithNewObject()
        when: "Try get map with index and names of only numeric column"
        Map<Integer, Double> result = metricService.createMapWithIndexAndValueNewObject(indexes, values)
        then: "Result should be map with index and name of only numeric column"
        result.equals(newObject)
    }

    def "Should return vector of distance by euclidean metric"() {
        given: "Defined data to call method"
        def testData = getTestData()
        def newObject = getMapWithNewObject()
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> testData
        when:
        LinkedList<Double> result = metricService.getVectorOfDistance(newObject, 1,-1)
        then:
        result.equals(asList(35.79, 33.8, 35.3, 36.22, 34.69))
    }

    def "Should return vector of distance by manhattan metric"() {
        given: "Defined data to call method"
        def testData = getTestData();
        def newObject = getMapWithNewObject()
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> testData
        when:
        LinkedList<Double> result = metricService.getVectorOfDistance(newObject, 2,-1)
        then:
        result.equals(asList(10.7,9.6,9.4,10.0,9.7))
    }

    def "Should return vector of distance by chebyshev metric"() {
        given: "Defined data to call method"
        def testData = getTestData();
        def newObject = getMapWithNewObject()
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> testData
        when:
        LinkedList<Double> result = metricService.getVectorOfDistance(newObject, 3, -1)
        then:
        result.equals(asList(4.3,4.9,5.0,5.2,4.8))
    }

    Map<Integer, Double> getMapWithNewObject(){
        return  new HashMap<Integer, Double>() {
            {
                put(0, 1.5);
                put(1, 2.5);
                put(2, 3.5);
                put(3, 4.5);
            }
        }
    }

    LinkedList<LinkedList<String>> getTestData(){
        return asList(
                asList("LISDLG", "LISSZE", "PLADLG", "PLASZE", "ODMIRYS") as LinkedList<String>,
                asList("5.0", "3.3", "1.4", "0.2", "SETOSA") as LinkedList<String>,
                asList("6.4", "2.8", "5.6", "2.2", "VIRGINIC") as LinkedList<String>,
                asList("6.5", "2.8", "4.6", "1.5", "VERSICOL") as LinkedList<String>,
                asList("6.7", "3.1", "5.6", "2.4", "VIRGINIC") as LinkedList<String>,
                asList("6.3", "2.8", "5.1", "1.5", "VIRGINIC") as LinkedList<String>)
    }
}