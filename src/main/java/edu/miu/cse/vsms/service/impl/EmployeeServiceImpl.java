package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.service.EmployeeService;
import edu.miu.cse.vsms.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseDto addEmployee(EmployeeRequestDto request) {
        Employee employee= new Employee();
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setPhone(request.phone());
        Employee savedEmployee=employeeRepository.save(employee);
        EmployeeResponseDto employeeResponseDto= mapToResponseDto(savedEmployee);
        return employeeResponseDto;
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> employees=employeeRepository.findAll();
        List<EmployeeResponseDto> employeeResponseDtos= new ArrayList<>();

        for(Employee employee:employees){
            EmployeeResponseDto employeeResponseDto=mapToResponseDto(employee);
            employeeResponseDtos.add(employeeResponseDto);

        }

        return employeeResponseDtos;
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        Optional<Employee> foundEmployee= employeeRepository.findById(id);
        if(foundEmployee.isPresent()){
            return mapToResponseDto(foundEmployee.get());
        }

        throw  new ResourceNotFoundException("Employee :"+id+"Not exist.");
    }

    @Override
    public EmployeeResponseDto partiallyUpdateEmployee(Long id, Map<String, Object> updates) {
        // Fetch the employee by ID or throw an exception if not found
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));

        // Apply each update based on the key
        updates.forEach((key, value) -> {
            switch (key) {
                case "name": {
                  // employee.setName(updates.get(key));
                }

                    break;
                case "email":
                    // Write your code here

                    break;
                case "phone":
                    // Write your code here

                    break;
                case "hireDate":
                    // Write your code here

                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });
        // Write your code here
        return mapToResponseDto(employeeRepository.save(employee));
    }

    private EmployeeResponseDto mapToResponseDto(Employee employee) {
        List<VehicleServiceResponseDto> serviceDtos = employee.getVServices().stream()
                .map(service -> new VehicleServiceResponseDto(
                        service.getId(),
                        service.getServiceName(),
                        service.getCost(),
                        service.getVehicleType()
                )).toList();

        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                serviceDtos
        );
    }
}
