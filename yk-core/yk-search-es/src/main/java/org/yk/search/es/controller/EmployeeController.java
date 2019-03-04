package org.yk.search.es.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yk.common.constant.ResultCode;
import org.yk.common.data.GeneralContentResult;
import org.yk.common.data.GeneralPagingResult;
import org.yk.common.data.GeneralResult;
import org.yk.common.data.PageInfo;
import org.yk.common.data.datamgmt.AddEmployeeReq;
import org.yk.common.data.datamgmt.AddEmployeeResp;
import org.yk.common.data.datamgmt.SearchEmployeeResp;
import org.yk.search.es.data.entity.Employee;
import org.yk.search.es.data.repository.EmployeeRepository;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@RequestMapping(value="/noauth/employees",method = RequestMethod.POST)
	@ApiOperation(value = "新增员工")
	public GeneralContentResult<AddEmployeeResp> addEmployees(@RequestBody AddEmployeeReq addEmployeeReq){
		log.info("EmployeeController addEmployee addEmployeeReq = {}",addEmployeeReq);
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
	
	@RequestMapping(value = "/noauth/employees", method = RequestMethod.GET)
	@ApiOperation(value = "员工查询")
	public GeneralPagingResult<List<SearchEmployeeResp>> getEmployees(
			@RequestParam(value = "currentPage",required=false,defaultValue="0") Integer currentPage,
			@RequestParam(value = "pageSize",required=false,defaultValue="1000") Integer pageSize,
			@RequestParam(value = "firstName",required=false) String firstName, 
			@RequestParam(value = "lastName",required=false) String lastName){
		log.info("EmployeeController getEmployees");
		GeneralPagingResult<List<SearchEmployeeResp>> result = new GeneralPagingResult<List<SearchEmployeeResp>>();
		try {
			
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
			if(!StringUtils.isEmpty(firstName)){
				queryBuilder.must(QueryBuilders.fuzzyQuery("firstName", firstName));
			}
			if(!StringUtils.isEmpty(lastName)){
				queryBuilder.must(QueryBuilders.fuzzyQuery("lastName", firstName));
			}
			Page<Employee> searchResult = employeeRepository.search(queryBuilder, PageRequest.of(currentPage, pageSize));
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurrentPage(currentPage);
			pageInfo.setPageSize(pageSize);
			pageInfo.setTotalPage(searchResult.getTotalPages());
			pageInfo.setTotalRecords(searchResult.getTotalElements());
			List<SearchEmployeeResp> list = new ArrayList<SearchEmployeeResp>();
			for (Employee employee : searchResult) {
				SearchEmployeeResp searchEmployeeResp = new SearchEmployeeResp();
				BeanUtils.copyProperties(employee, searchEmployeeResp);
				list.add(searchEmployeeResp);
			}
			result.setResultCode(ResultCode.OPERATE_SUCCESS);
			result.setResultContent(list);
			result.setPageInfo(pageInfo);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.setResultCode(ResultCode.SERVER_UNAVALIABLE);
			return result;
		}
	}
	
	@RequestMapping(value = "/noauth/employees/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "员工查询")
	public GeneralContentResult<SearchEmployeeResp> getEmployee(
			@PathVariable("id") String id
			){
		log.info("EmployeeController getEmployee id = {}",id);
		GeneralContentResult<SearchEmployeeResp> result = new GeneralContentResult<SearchEmployeeResp>();
		try {
			Optional<Employee> searchResult = employeeRepository.findById(id);
			if(searchResult.isPresent()){
				SearchEmployeeResp searchEmployeeResp = new SearchEmployeeResp();
				BeanUtils.copyProperties(searchResult.get(), searchEmployeeResp);
				result.setResultContent(searchEmployeeResp);
			}
			result.setResultCode(ResultCode.OPERATE_SUCCESS);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.setResultCode(ResultCode.SERVER_UNAVALIABLE);
			return result;
		}
	}
	
	@RequestMapping(value = "/noauth/employees/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除员工")
	public GeneralResult deleteEmployee(@PathVariable("id") String id){
		log.info("EmployeeController deleteEmployee id = {}",id);
		GeneralResult result = new GeneralResult();
		try {
			employeeRepository.deleteById(id);
			result.setResultCode(ResultCode.OPERATE_SUCCESS);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.setResultCode(ResultCode.SERVER_UNAVALIABLE);
			return result;
		}
	}
	
}
