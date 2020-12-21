package pl.edu.pb.swd.cuts.Controller;

import org.springframework.web.bind.annotation.*;
import pl.edu.pb.swd.cuts.Model.*;
import pl.edu.pb.swd.cuts.Service.CutsService;

import java.io.IOException;
import java.util.LinkedList;
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
    @PostMapping("/classifyMoreThan2D")
    public String classifyMoreThan2D(@RequestBody LinkedList<Double> object){
        String result ="";
        for(Double value: object){
            result=result+value + ", ";
        }
        StringBuffer sb= new StringBuffer(result);
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    @PostMapping("/classify2D")
    public String classify2D(@RequestBody Row row){
        return cutsService.classifyTwoDimensionalObject(row);
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
