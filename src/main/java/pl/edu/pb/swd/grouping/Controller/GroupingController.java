package pl.edu.pb.swd.grouping.Controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.pb.swd.grouping.Service.GroupingService;

import java.io.IOException;
import java.util.LinkedList;

@RestController
@RequestMapping(value = "/grouping")
public class GroupingController {

    final GroupingService groupingService;

    public GroupingController(GroupingService groupingService) {
        this.groupingService = groupingService;
    }


    @PostMapping
    public LinkedList<LinkedList<String>> grouping(@RequestParam Integer k,
                                                   @RequestParam Integer method,
                                                   @RequestParam String columnName) throws IOException {

        return groupingService.
                grouping(
                        method,
                        groupingService.randObject(k),
                        columnName);


    }
}
