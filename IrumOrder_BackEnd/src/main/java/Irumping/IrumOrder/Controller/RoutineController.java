package Irumping.IrumOrder.Controller;


import Irumping.IrumOrder.Dto.RoutineDto;
import Irumping.IrumOrder.Service.RoutineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
@Tag(name = "RoutineSet")
@RequestMapping("/routine")
public class RoutineController {
    private RoutineService routineService;

    @PostMapping("/set")
    public @ResponseBody void addRoutine(@RequestBody RoutineDto routineDto){
        routineService.addRoutine(routineDto);

    }

}
