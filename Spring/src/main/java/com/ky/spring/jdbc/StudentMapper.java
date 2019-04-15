package com.ky.spring.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StudentMapper implements RowMapper<Student> {

	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		Student student = new Student();
		student.setNo(rs.getInt("no"));
		student.setName(rs.getString("name"));
		student.setSalary(rs.getInt("salary"));
		return student;
	}

}
