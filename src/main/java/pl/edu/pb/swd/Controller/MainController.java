package pl.edu.pb.swd.Controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.pb.swd.Model.Range;
import pl.edu.pb.swd.Service.*;

import java.io.IOException;
import java.util.*;

import static pl.edu.pb.swd.Service.ReusableOperationsService.*;

@RestController
@RequestMapping("/data")
public class MainController {

    final
    ReadWriteService readWriteService;

    final TextToNumericService textToNumericService;

    final RangeService rangeService;

    final ReusableOperationsService reusableOperationsService;

    final NormalizationService normalizationService;

    final DiscretizationService discretizationService;

    public MainController(ReadWriteService readWriteService, TextToNumericService textToNumericService, RangeService rangeService, ReusableOperationsService reusableOperationsService, NormalizationService normalizationService, DiscretizationService discretizationService) {
        this.readWriteService = readWriteService;
        this.textToNumericService = textToNumericService;
        this.rangeService = rangeService;
        this.reusableOperationsService = reusableOperationsService;
        this.normalizationService = normalizationService;
        this.discretizationService = discretizationService;
    }

    @RequestMapping(value = "/originalFile",method = RequestMethod.GET)
    public LinkedList<LinkedList<String>> getDataFromOriginalFile(
            @RequestParam String filePath,
            @RequestParam String separator) throws IOException {
        LinkedList<LinkedList<String>> record = readWriteService.readDataFromOriginalFile(filePath, separator);
        readWriteService.saveToWorkingFile(record);
        return readWriteService.readDataFromOriginalFile(filePath, separator);
    }

    @RequestMapping(value = "/textToNumeric",method = RequestMethod.POST)
    public LinkedList<LinkedList<String>> convertTextValuesToNumeric(
            @RequestParam String columnName,
            @RequestParam String newColumnName,
            @RequestParam boolean alphabetical) throws IOException {
            return textToNumericService.chooseMethodConvertTextValuesToNumeric(columnName,
                    newColumnName,
                    alphabetical);
    }

    @RequestMapping(value = "/overwittenFile", method = RequestMethod.POST)
    public void ovewriteFile(
            @RequestBody LinkedList<LinkedList<String>> newData) throws IOException {
        readWriteService.saveToWorkingFile(newData);
    }

    @RequestMapping(value = "/workingFile", method = RequestMethod.GET)
        public LinkedList<LinkedList<String>> getDataFromWorkingFile(){
            return readWriteService.readDataFromWorkingFile();
        }

    @RequestMapping(value = "/newRange", method = RequestMethod.POST)
        public LinkedList<LinkedList<String>> newRange(@RequestParam String columnName,
                                                   @RequestParam String newColumnName,
                                                   @RequestParam Double newMin,
                                                   @RequestParam Double newMax) throws IOException {
        LinkedList<String> column = reusableOperationsService.getWholeColumnByIndex(columnName);
        LinkedList<Double> columnDouble = convertStringValueToDouble(column);
        LinkedList<String> newColumn =  rangeService.
                createNewColumnWithChangedRangeOfValuesByMinMaxNormalizationMethod(
                        columnDouble,
                        newMin,
                        newMax);
        return reusableOperationsService.mergingExistingColumnsWithNewColumn(newColumnName, newColumn);
    }

    @RequestMapping(value = "/normalization", method = RequestMethod.POST)
    public LinkedList<LinkedList<String>> normalization(@RequestParam String columnName,
                                                        @RequestParam String newColumnName) throws IOException {
        LinkedList<String> column = reusableOperationsService.getWholeColumnByIndex(columnName);
        LinkedList<Double> columnDouble = convertStringValueToDouble(column);
        LinkedList<String> newColumn = normalizationService.normalizationRow(columnDouble);
        return reusableOperationsService.mergingExistingColumnsWithNewColumn(newColumnName, newColumn);
    }

    @RequestMapping(value = "/percentageHighestOrSmallestValues", method = RequestMethod.GET)
    public LinkedList<LinkedList<String>> getPercentageHighestOrSmallestValues(
            @RequestParam String minOrMax,
            @RequestParam String sortBy,
            @RequestParam Integer percentageValues
    ){
        LinkedList<LinkedList<String>> linkedLists = readWriteService.readDataFromWorkingFile();
        int first = 0,
                indexColumn = findColumnIndex(
                        linkedLists.get(first),
                        sortBy),
                numberOfObservation = (linkedLists.size()-1) * percentageValues/100;
        linkedLists = reusableOperationsService.sortLinkedListOfLinkedListByIndex(linkedLists, indexColumn);
        return reusableOperationsService.getPercentageOfValue(linkedLists, minOrMax, numberOfObservation);
    }

    @RequestMapping(value = "/discretization", method=RequestMethod.POST)
    public LinkedList<LinkedList<String>> discretization(@RequestParam String columnName,
                                                         @RequestParam Integer numberOfRanges,
                                                         @RequestParam String newColumnName) throws IOException {
        LinkedList<String> column = reusableOperationsService.getWholeColumnByIndex(columnName);
        LinkedList<Double> columnDouble = convertStringValueToDouble(column);
        LinkedList<Range> rangeLinkedList = discretizationService.createListRange(columnName, numberOfRanges);
        LinkedList<String> newColumn = discretizationService.discretizationRow(columnDouble, rangeLinkedList);
        return reusableOperationsService.mergingExistingColumnsWithNewColumn(newColumnName, newColumn);
    }

    @RequestMapping(value = "/2dChart", method=RequestMethod.GET)
    public LinkedList<LinkedList<String>> getDataTo2dChart(@RequestParam String firstColumnName,
                                                           @RequestParam String secondColumnName,
                                                           @RequestParam(required = false) String thirdColumnName){
        LinkedList<String> firstColumn = reusableOperationsService.getWholeColumnByIndex(firstColumnName);
        LinkedList<String> secondColumn = reusableOperationsService.getWholeColumnByIndex(secondColumnName);
        if(!thirdColumnName.equals("")){
            LinkedList<String> thirdColumn = reusableOperationsService.getWholeColumnByIndex(thirdColumnName);

        }
        return null;
    }
}

