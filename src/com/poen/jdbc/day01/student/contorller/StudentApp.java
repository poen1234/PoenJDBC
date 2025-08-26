package com.poen.jdbc.day01.student.contorller;

import java.util.List;

import com.poen.jdbc.day01.student.model.ManageStudent;
import com.poen.jdbc.day01.student.model.Student;
import com.poen.jdbc.day01.student.view.ViewStudent;
public class StudentApp {
	public static void main(String[] args) {
		ManageStudent manage = new ManageStudent();
		ViewStudent view = new ViewStudent();
		Student student;
		String name;
		int index;
		int result;
		finish:
		while(true) {
			int menu = view.printMenu();
			switch(menu) {
			case 1:
				// 정보를 입력받아서 student객체에 저장
				// 후 리턴
				student = view.inputStudent();
				// 정보가 입력된 student객체를
				// sList에 저장
				result = manage.addStudent(student);
				if(result > 0) {
					view.displayMsg("학생정보 추가 완료");
				}else {
					view.displayMsg("학생정보 추가 실패");					
				}
				break;
			case 2:
				// 검색할 이름을 입력받음.
				name = view.inputName();
				// 입력받은 이름으로 검색해옴
				student = manage.searchOneByName(name);
				// 검색한 student 객체를 출력함.
				if(student != null) {
					view.printStudent(student);
				}else {
					view.displayMsg("학생 정보가 존재하지 않습니다.");
				}
				break;
			case 3:
				List<Student> sList = manage.getAllStudents();
				view.printStudents(sList);
				break;
			case 4:
				// 수정할 이름을 입력받음.
				name = view.inputName();
				// 입력받은 이름의 학생 점수 중
				// 수정할 점수를 입력받음
				student = view.modifyStudent(name);
				// 수정하는 메소드가 index가 필요하므로
				// 이름으로 index값을 가져옴
//				index = manage.searchIndexByName(name);
				// index와 student를 넘겨서 해당 위치의
				// 객체를 replace함.
				result = manage.setStudent(student);
				if(result > 0) {
					view.displayMsg("데이터 수정이 완료되었습니다.");					
				}else {
					view.displayMsg("데이터 수정이 완료되지 않았습니다.");					
				}
				break;
			case 5:
				// 삭제할 학생 이름 입력 받음
				name = view.inputName();
				// 입력받은 이름으로 sList에서 삭제하기
				// 삭제하기 위해선 index 필요
				// 이름으로 인덱스를 찾기
//				index = manage.searchIndexByName(name);
				// 인덱스를 전달하여 sList에서 해당
				// 객체 삭제
				result =  manage.removeStudent(name);
				if(result > 0) {
					view.displayMsg("데이터 삭제가 완료되었습니다");
				}else {
					view.displayMsg("데이터 삭제가 완료되지 않았습니다");				
				}
				break;
			case 6: break;
			case 0:
				view.displayMsg("프로그램이 종료되었습니다.");
				break finish;
			default :
				view.displayMsg("1 ~ 6 사이의 수를 입력하세요.");
				break;
			}
		}
	}
}
