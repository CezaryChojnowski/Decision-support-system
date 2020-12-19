package pl.edu.pb.swd.cuts.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pb.swd.cuts.Model.Line;
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
    public List<Line> getAllCuts() {
        return cutsService.createCuts();
    }
}
