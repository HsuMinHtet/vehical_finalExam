package edu.miu.cse.vsms.controller;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // Add a new employee
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> addEmployee(@RequestBody EmployeeRequestDto request) {
        EmployeeResponseDto employeeResponseDto=employeeService.addEmployee(request);
        if(employeeResponseDto != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        List<EmployeeResponseDto> employeeResponseDtos=employeeService.getAllEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDtos);
    }

    // Get a specific employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDto employeeResponseDto=employeeService.getEmployeeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDto);
    }

    // Update partially an existing employee
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> partiallyUpdateEmployee(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        EmployeeResponseDto employeeResponseDto=employeeService.partiallyUpdateEmployee(id, updates);
        if(employeeResponseDto!=null){
            return ResponseEntity.status(HttpStatus.OK).body(employeeResponseDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}