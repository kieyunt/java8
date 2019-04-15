package com.ky.spring.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class StudentJdbcTemplate implements StudentDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource ds) {
		this.dataSource = ds;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void create(Integer no, String name, Integer salary) {
		jdbcTemplateObject.update("insert into CHONG values (?, ?, ?)", no, name, salary);
	    System.out.println("Created Record no = " + no + " name = " + name + " salary = "+salary);
	}

	public Student getStudent(Integer no) {
		String SQL = "select * from CHONG where no = ?";
		Student student = jdbcTemplateObject.queryForObject(SQL, new Object[] { no }, new StudentMapper());
		return student;
	}

	public List<Student> listStudents() {
		String SQL = "select * from CHONG order by no ";
		List<Student> students = jdbcTemplateObject.query(SQL, new StudentMapper());
		return students;
	}

	public void delete(Integer no) {
		String SQL = "delete from CHONG where no = ?";
		jdbcTemplateObject.update(SQL, no);
		System.out.println("Deleted Record with no = " + no);
	}

	public void update(Integer no, String name, Integer salary) {
		String SQL = "update CHONG set name = ? , salary = ? where no = ?";
		jdbcTemplateObject.update(SQL, name, salary, no);
		System.out.println("Updated Record with no = " + no);
	}

}
