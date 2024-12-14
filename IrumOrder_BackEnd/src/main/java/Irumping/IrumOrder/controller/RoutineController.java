package Irumping.IrumOrder.controller;


import Irumping.IrumOrder.dto.RoutineDto;
import Irumping.IrumOrder.dto.RoutineResponseDto;
import Irumping.IrumOrder.service.RoutineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 클래스 설명: 사용자 루틴을 관리하는 컨트롤러.
 * 루틴 조회, 생성, 수정, 삭제와 같은 기능을 제공한다.
 *
 * 작성자: 양나슬
 * 마지막 수정일: 2024-12-02
 */

@RestController
@RequestMapping("/api/users/{userId}/routines")
public class RoutineController {

    private final RoutineService routineService;

    @Autowired
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    /**
     * 사용자의 모든 루틴을 조회하는 메서드.
     *
     * @param userId 사용자 ID
     * @return ResponseEntity<List<RoutineResponseDto>> 사용자의 모든 루틴 정보
     */
    @Operation(
            summary = "Get all routines by user ID",
            description = "Fetches all routines associated with a specific user ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched routines"),
                    @ApiResponse(responseCode = "404", description = "User or routines not found")
            }
    )
    @GetMapping
    public ResponseEntity<List<RoutineResponseDto>> getAllRoutinesByUserId(@Parameter(description = "ID of the user whose routines are to be fetched", example = "123")
                                                                               @PathVariable(name = "userId") Integer userId) {
        List<RoutineResponseDto> responseDtos = routineService.getRoutinesByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    /**
     * 새로운 루틴을 생성하는 메서드.
     *
     * @param routineDto 생성할 루틴 정보
     * @return ResponseEntity<RoutineResponseDto> 생성된 루틴의 정보
     */
    @Operation(
            summary = "Create a new routine",
            description = "Adds a new routine for the user.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Routine successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping("/add")
    public ResponseEntity<RoutineResponseDto> createRoutine(
            @Parameter(description = "Details of the routine to be created") @RequestBody RoutineDto routineDto) {
        RoutineResponseDto createdRoutine = routineService.addRoutine(routineDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoutine);
    }


    /**
     * 루틴 정보를 수정하는 메서드.
     *
     * @param routineId 수정할 루틴 ID
     * @param routineDto 수정할 루틴 정보
     * @return ResponseEntity<RoutineResponseDto> 수정된 루틴의 정보
     */
    @Operation(
            summary = "Update an existing routine",
            description = "Updates the details of an existing routine by routine ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Routine successfully updated"),
                    @ApiResponse(responseCode = "404", description = "Routine not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    // 루틴 수정 메서드
    @PutMapping("/{routineId}")
    public ResponseEntity<RoutineResponseDto> updateRoutine(
            @Parameter(description = "ID of the routine to be updated", example = "1")
            @PathVariable(name = "routineId") Integer routineId,

            @Parameter(description = "Updated details of the routine")
            @RequestBody RoutineDto routineDto) {

        RoutineResponseDto updatedRoutine = routineService.updateRoutine(routineId, routineDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRoutine);
    }

    /**
     * 루틴을 삭제하는 메서드.
     *
     * @param routineId 삭제할 루틴 ID
     * @param userId 사용자 ID
     * @return ResponseEntity<Void> 삭제 성공 응답
     */
    @Operation(
            summary = "Delete a routine",
            description = "Deletes a routine by its ID for a specific user.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Routine successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Routine not found"),
                    @ApiResponse(responseCode = "403", description = "User not authorized to delete this routine")
            }
    )
    //루틴 삭제 메서드
    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(
            @Parameter(description = "ID of the routine to be deleted", example = "1")
            @PathVariable(name = "routineId") Integer routineId,

            @Parameter(description = "ID of the user who owns the routine", example = "123")
            @PathVariable(name="userId") Integer userId) {
        routineService.deleteRoutine(routineId, userId);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }

}