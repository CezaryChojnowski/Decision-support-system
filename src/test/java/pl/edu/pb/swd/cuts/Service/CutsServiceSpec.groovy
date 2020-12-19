package pl.edu.pb.swd.cuts.Service

import pl.edu.pb.swd.cuts.Model.Row
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService
import spock.lang.Specification

import static java.util.Arrays.*;


class CutsServiceSpec extends Specification {

    ReadWriteService readWriteService
    CutsService cutsService

    def setup(){
        readWriteService = Stub(ReadWriteService)
        cutsService = new CutsService(readWriteService)
    }

    def "Should return false, determining whether the set is more than two-dimensional"(){
        given: "Defined data to call method"
        def data = getTestDataFourDimensional()
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> data
        when: "Check dimensional of set"
        def result = cutsService.checkIfTheSetIsTwoDimensional();
        then: "Result should be return false"
        result.equals(false);
    }

    def "Should return true, determining whether the set is two-dimensional"(){
        given: "Defined data to call method"
        def data = getTestDataTwoDimensional()
        and: "Method readDataFromWorkingFile always return this list of lists with data"
        readWriteService.readDataFromWorkingFile() >> data
        when: "Check dimensional of set"
        def result = cutsService.checkIfTheSetIsTwoDimensional();
        then: "Result should be return true"
        result.equals(true);
    }

    def "Should return true if list of string equals list of rows"(){
        given: "Defined data to call method"
        def data = getTestDataTwoDimensional()
        def listOfRows = getListOfRowObjects()
        when: "Check dimensional of set"
        def result = cutsService.listOfListToListOfRowObjects(data);
        then: "Result should be return true"
        result.equals(listOfRows)
    }

    LinkedList<LinkedList<String>> getTestDataFourDimensional(){
        return asList(
                asList("x1", "x2", "x3", "x4", "class") as LinkedList<String>,
                asList("5.0", "3.3", "1.4", "0.2", "SETOSA") as LinkedList<String>)
    }
    LinkedList<LinkedList<String>> getTestDataTwoDimensional(){
        return asList(
                asList("x1", "x2", "class") as LinkedList<String>,
                asList("5.0", "3.3", "SETOSA") as LinkedList<String>,
                asList("4.8", "2.3", "VIRGINIC") as LinkedList<String>,
                asList("6.8", "1.6", "VERSICOL") as LinkedList<String>,
                asList("7.8", "3.7", "SETOSA") as LinkedList<String>,)
    }

    List<Row> getListOfRowObjects(){
        return asList(
                new Row(5.0, 3.3, "SETOSA"),
                new Row(4.8, 2.3, "VIRGINIC"),
                new Row(6.8, 1.6, "VERSICOL"),
                new Row(7.8, 3.7, "SETOSA")
        )
    }
}
