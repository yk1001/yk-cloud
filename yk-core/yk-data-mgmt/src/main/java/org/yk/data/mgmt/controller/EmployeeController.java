package org.yk.data.mgmt.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.constant.ResultCode;
import org.yk.common.data.GeneralContentResult;
import org.yk.common.data.datamgmt.AddEmployeeReq;
import org.yk.common.data.datamgmt.AddEmployeeResp;
import org.yk.data.mgmt.data.entity.Employee;
import org.yk.data.mgmt.data.repository.EmployeeRepository;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@RequestMapping(value="/noauth/employee",method = RequestMethod.POST)
	@ApiOperation(value = "新增员工")
	public GeneralContentResult<AddEmployeeResp> addEmployee(@RequestBody AddEmployeeReq addEmployeeReq){
		log.debug("EmployeeController addEmployee addEmployeeReq = {}",addEmployeeReq);
		GeneralContentResult<AddEmployeeResp> result = new GeneralContentResult<AddEmployeeResp>();
		try {
			AddEmployeeResp addEmployeeResp = new AddEmployeeResp();
			Employee employee = new Employee();
			BeanUtils.copyProperties(addEmployeeReq, employee);
			employee = employeeRepository.save(employee);
			BeanUtils.copyProperties(employee, addEmployeeResp);
			result.setResultCode(ResultCode.OPERATE_SUCCESS);
			result.setResultContent(addEmployeeResp);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.setResultCode(ResultCode.SERVER_UNAVALIABLE);
			return result;
		}
	}
	
}
