package com.ky.spring.jdbc;

import java.util.List;

import javax.sql.DataSource;

public interface StudentDAO {

	public void setDataSource(DataSource ds);
	public void create(Integer no, String name, Integer salary);
	public Student getStudent(Integer no);
	public List<Student> listStudents();
	public void delete(Integer no);
	public void update(Integer no, String name, Integer salary);
	
}
