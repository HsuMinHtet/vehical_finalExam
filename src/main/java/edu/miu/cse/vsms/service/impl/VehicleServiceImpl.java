package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.ServiceRequestDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.repository.VehicleServiceRepository;
import edu.miu.cse.vsms.service.VehicleService;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;
import java.util.Optional;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleServiceRepository vehicleServiceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public VehicleServiceResponseDto assignService(ServiceRequestDto request) {
        Optional<Employee> foundEmployee=employeeRepository.findById(request.employeeId());
        if(foundEmployee.isPresent()){
            VService vService = new VService(request.serviceName(), request.cost(), request.vehicleType(), foundEmployee.get());
            VService savedvService=vehicleServiceRepository.save(vService);
            VehicleServiceResponseDto vehicleServiceResponseDto= new VehicleServiceResponseDto(savedvService.getId(),savedvService.getServiceName(), savedvService.getCost(), savedvService.getVehicleType());
            return vehicleServiceResponseDto;
        }
        throw new ResourceNotFoundException("Employee: " +request.employeeId()+"Not found") ;
    }
}
