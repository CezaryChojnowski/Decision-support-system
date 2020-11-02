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
}

