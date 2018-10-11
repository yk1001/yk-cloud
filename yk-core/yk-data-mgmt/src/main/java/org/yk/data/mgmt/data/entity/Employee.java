package org.yk.data.mgmt.data.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Document(indexName = "megacorp",type = "employee", shards = 1,replicas = 0, refreshInterval = "-1")
@Data
public class Employee implements Serializable{
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private Integer age;
	private String about;
}
