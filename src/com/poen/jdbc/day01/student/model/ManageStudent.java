package com.poen.jdbc.day01.student.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class ManageStudent implements ManageInterface{
	
	private static final String DRIVER_NAME ="oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "POENJDBC";
	private static final String PASSWORD = "POENJDBC";
	
	private List<Student> sList;
	
	public ManageStudent() {
		sList = new ArrayList<Student>();
	}
	@Override
	public Student searchOneByName(String name) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '"+name+"'";
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			// 후처리
			if(rset.next()) {
				String studentName = rset.getString("STUDENT_NAME");
				int firstScore = rset.getInt("FIRST_SCORE");
				int secondScore = rset.getInt("SECOND_SCORE");
				// rset에서 가져온 필드를 여러개를 리턴하지 못하므로
				// 객체 여러개를 담아서 리천해야함
				Student student = new Student(studentName, firstScore, secondScore);
				return student;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		for(int i = 0; i < sList.size(); i++) {
//			if(sList.get(i).getName().equals(name)) {
//				return sList.get(i);
//			}
//		}
		return null;
	}
	@Override
	public int searchIndexByName(String name) {
		for(int i = 0; i < sList.size(); i++) {
			if(sList.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1; // index가 0이면 첫번째 값이기 때문에 -1로 리턴
	}
	public List<Student> getAllStudents() {
		//SELECT * FROM STUDENT_TBL
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		String query = "SELECT * FROM STUDENT_TBL";

		try {
			Class.forName(DRIVER_NAME); // 드라이버 등록
			conn = DriverManager.getConnection(URL,USER,PASSWORD); // 연결 생성
			stmt = conn.createStatement(); // 워크시트 열기
			rset = stmt.executeQuery(query); //쿼리문 실행 후 결과 받기
			// 후처리
			// rset에 있는 필드의 값을 Student의 필드에 하나씩 넣어줌
			// Student 객체는 List에 저장해서 보내줌
			// sList를 생성자에서 한번만 초기화 하는 것이 아니라
			// getArrayStudents() 메소드가 동작할 때마다 초기화해서 값이 누적되지 않도록함.
			sList = new ArrayList<Student>(); 
			while(rset.next()) {
				// rset.getString("STUDENT_NAME");
				// rset.getInt("FIRST_SCORE");
				// rset.getInt("SECOND_SCORE");
				Student student = new Student();
				student.setName(rset.getString("STUDENT_NAME"));
				student.setFirstScore(rset.getInt("FIRST_SCORE"));
				student.setSecondScore(rset.getInt("SECOND_SCORE"));
				sList.add(student);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				stmt.close();
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

				
		return sList;
	}
	@Override
	public int addStudent(Student student) {
		// 정보가 입력된 student 객체를 sList에 저장
//		sList.add(student);
		Connection conn = null;
		Statement stmt = null;
		int result = -1;
		
		try {
			// 1.
			Class.forName(DRIVER_NAME);
			// 2.
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.createStatement();
			String query = "INSERT INTO STUDENT_TBL VALUES('"+student.getName() 
															+"', "+student.getFirstScore()
															+ "," +student.getSecondScore()+")";
			result = stmt.executeUpdate(query);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	@Override
	public int setStudent(Student student) {
//		sList.set(index, student);
		String query = "UPDATE STUDENT_TBL SET FIRST_SCORE = "
						+student.getFirstScore()+", SECOND_SCORE = "
						+student.getSecondScore()+" WHERE STUDENT_NAME = '"+student.getName()+"'";
		Connection conn = null;
		Statement stmt = null;
		int result = -1;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
//			if(result > 0) {
//				System.out.println("학생 정보가 수정 되었습니다.");
//			}else {
//				System.out.println("학생 정보가 수정되지 않았습니다.");				
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}
	@Override
	public void removeStudent(int index) {
		sList.remove(index);
	}
	@Override
	public int removeStudent(String name) {
		// 입력받아 - >Scanner sc = new Scanner(System.in);
		// JDBC를 이용하여 데이터를 ~~ -> 드라이버등록, DBMS 연결, Statement 생성
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_NAME = '"+name+"'";
		Connection conn = null;
		Statement stmt = null;
		int result = -1;
		
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL,USER,PASSWORD);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
//			if(result > 0) {
//				System.out.println("데이터 삭제가 완료되었습니다");
//			}else {
//				System.out.println("데이터 삭제가 완료되지 않았습니다");				
//			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}

