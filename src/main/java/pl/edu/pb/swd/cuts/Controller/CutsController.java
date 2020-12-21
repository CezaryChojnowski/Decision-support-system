package pl.edu.pb.swd.cuts.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.swd.cuts.Model.*;
import pl.edu.pb.swd.cuts.Service.CutsService;

import java.io.IOException;
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
    public List<Line> getAllCutsForTwoDimensionalPlane() throws IOException {
        CutResultForTwoDimensionalPlane cutResultForTwoDimensionalPlane = cutsService.createCuts();
        cutsService.overwriteWithBinaryVector2DSet();
        return cutResultForTwoDimensionalPlane.getLines();

    }

    @GetMapping("/multidimensionalSet")
    public ResultInfoMultiDimensionalPlane getAllCutsForMultiDimensionalPlane() throws IOException {
        CutResultForMultiDimensionalPlane cutResultForMultiDimensionalPlane = cutsService.cutsMultiDimensionalSet();
        cutsService.overwriteWithBinaryVectorMoreThan2DSet();
        return new ResultInfoMultiDimensionalPlane(cutResultForMultiDimensionalPlane.getNumberOfObjectsRemoved(),
                cutResultForMultiDimensionalPlane.getNumberOfCuts());
    }

    @GetMapping("/twoDimensionalSet")
    public ResultInfoMultiDimensionalPlane getNumberOfCutsAndNumberOfRemovedObject(){
        CutResultForTwoDimensionalPlane cutResultForTwoDimensionalPlane = cutsService.createCuts();
        return new ResultInfoMultiDimensionalPlane(cutResultForTwoDimensionalPlane.getNumberOfObjectsRemoved(),
                cutResultForTwoDimensionalPlane.getNumberOfCuts());
    }
}
