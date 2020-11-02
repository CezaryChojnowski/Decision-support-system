package pl.edu.pb.swd.Service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;

@Service
public class DataForChartsService {

    final ReusableOperationsService reusableOperationsService;

    public DataForChartsService(ReusableOperationsService reusableOperationsService) {
        this.reusableOperationsService = reusableOperationsService;
    }

    public LinkedList<LinkedList<String>> getDataTo2dChart(String firstColumnName,
                                               String secondColumnName,
                                               String thirdColumnName){
        LinkedList<String> firstColumn = reusableOperationsService.getWholeColumnByIndex(firstColumnName);
        LinkedList<String> secondColumn = reusableOperationsService.getWholeColumnByIndex(secondColumnName);
        LinkedList<LinkedList<String>> linkedLists = new LinkedList<>();
        linkedLists.addAll(Collections.singleton(firstColumn));
        linkedLists.addAll(Collections.singleton(secondColumn));
        if( thirdColumnName!=null && !thirdColumnName.isEmpty()){
            LinkedList<String> thirdColumn = reusableOperationsService.getWholeColumnByIndex(thirdColumnName);
            linkedLists.addAll(Collections.singleton(thirdColumn));
        }
        return linkedLists;
    }

}
