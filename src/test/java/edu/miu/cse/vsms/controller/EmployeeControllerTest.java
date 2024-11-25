package edu.miu.cse.vsms.controller;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController;
    @Mock
    private EmployeeService employeeService;

    @Test
    void addEmployee_success_returnBook() {
        EmployeeRequestDto employeeRequestDto=new EmployeeRequestDto("Hsu","hsu@gmail.com","12345678", LocalDate.now());
        EmployeeResponseDto employeeResponseDto= new EmployeeResponseDto(1L, "Hsu", "hsu@gmail.com  ", "12345678", LocalDate.now(),null);
        Mockito.when(employeeService.addEmployee(employeeRequestDto)).thenReturn(employeeResponseDto);

        ResponseEntity<EmployeeResponseDto> responseEntity=employeeController.addEmployee(employeeRequestDto);

        assert responseEntity.getStatusCode()== HttpStatus.CREATED;
        assert responseEntity.getBody()!=null;
        assert responseEntity.getBody()==employeeResponseDto;
    }
}