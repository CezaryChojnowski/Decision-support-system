package pl.edu.pb.swd.cuts.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.swd.cuts.Model.CutResultForMultiDimensionalPlane;
import pl.edu.pb.swd.cuts.Model.CutResultForTwoDimensionalPlane;
import pl.edu.pb.swd.cuts.Model.Line;
import pl.edu.pb.swd.cuts.Model.ResultInfoMultiDimensionalPlane;
import pl.edu.pb.swd.cuts.Service.CutsService;

import java.util.List;


@RestController
@RequestMapping(value = "/cuts")
public class CutsController {
    final CutsService cutsService;

    public CutsController(CutsService cutsService) {
        this.cutsService = cutsService;
    }

    @GetMapping("/rightDimensional")
    public boolean checkThatTheSetIsTheRightSize() {
        return cutsService.checkIfTheSetIsTwoDimensional();
    }

    @GetMapping
    public List<Line> getAllCutsForTwoDimensionalPlane() {
        CutResultForTwoDimensionalPlane cutResultForTwoDimensionalPlane = cutsService.createCuts();
        return cutResultForTwoDimensionalPlane.getLines();
    }

    @GetMapping("/multidimensionalSet")
    public ResultInfoMultiDimensionalPlane getAllCutsForMultiDimensionalPlane(){
        int numberOfObjectsRemoved = 2;
        int numberOfCuts = 3;
        return new ResultInfoMultiDimensionalPlane(numberOfObjectsRemoved,numberOfCuts);
    }
}
