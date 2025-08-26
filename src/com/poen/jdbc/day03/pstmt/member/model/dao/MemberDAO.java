package com.poen.jdbc.day03.pstmt.member.model.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.poen.jdbc.day03.pstmt.member.model.vo.Member;

public class MemberDAO {
	
	public List<Member> selectList(Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> mList = null;
		String query = "SELECT * FROM MEMBER_TBL ORDER BY MEMBER_ID ASC";
		
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery(query);
			rset = pstmt.executeQuery();
			mList = new ArrayList<Member>();
			// 후처리
			while(rset.next()) {
				Member member = this.rsetToMember(rset);
				mList.add(member);
			}
		return mList;
	}
	
	public Member selectOneById(String memberId, Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null;
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?"; // 위치홀더,값이 들어갈 위치
		
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, memberId);
//			rset = stmt.executeQuery(query);
		rset = pstmt.executeQuery();
		//후처리(rset -> Member || List<Member>)
		if(rset.next()) {
			member = new Member();
			member.setMemberId(rset.getString("MEMBER_ID"));
			member.setMemberPwd(rset.getString("MEMBER_PWD"));
			member.setMemberName(rset.getString("MEMBER_NAME"));
			member.setGender(rset.getString("GENDER").charAt(0));
			member.setAge(rset.getInt("AGE"));
			member.setEmail(rset.getString("EMAIL"));
			member.setPhone(rset.getString("PHONE"));
			member.setAddress(rset.getString("ADDRESS"));
			member.setHobby(rset.getString("HOBBY"));
			member.setEnroolDate(rset.getDate("ENROLL_DATE"));
		}
			rset.close();
			pstmt.close();
			conn.close();
			return member;
	}
	public int insertMember(Member member, Connection conn) throws SQLException {
		Statement stmt = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO MEMBER_TBL VALUES(?,?,?,?,?,?,?,?,?,DEFAULT)";
		int result = 0;
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, member.getMemberId());
		pstmt.setString(2, member.getMemberPwd());
		pstmt.setString(3, member.getMemberName());
//			pstmt.setString(4, member.getGender()+"");
		pstmt.setString(4, String.valueOf(member.getGender()));
		pstmt.setInt(5, member.getAge());
		pstmt.setString(6, member.getEmail());
		pstmt.setString(7, member.getPhone());
		pstmt.setString(8, member.getAddress());
		pstmt.setString(9, member.getHobby());
		result = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		return result;
	}
	
	public int updateMember(Member member, Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		String query = "UPDATE MEMBER_TBL SET MEMBER_PWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ?  WHERE MEMBER_ID = ?";
		int result = 0;
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, member.getMemberPwd());
		pstmt.setString(2, member.getEmail());
		pstmt.setString(3, member.getPhone());
		pstmt.setString(4, member.getAddress());
		pstmt.setString(5, member.getHobby());
		pstmt.setString(6, member.getMemberId());
		result = pstmt.executeUpdate();
		conn.close();
		pstmt.close();
		return result;
	}
	
	public int deleteMember(String memberId, Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = ?";
		int result = 0;
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, memberId);
		result = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		return result;
}
	
	public Member rsetToMember(ResultSet rset) throws SQLException {
		String memberId = rset.getString("MEMBER_ID");
		String memberPwd = rset.getString("MEMBER_PWD");
		String memberName = rset.getString("MEMBER_NAME");
		char gender = rset.getString("GENDER").charAt(0);
		int age = rset.getInt("AGE");
		String email = rset.getString("EMAIL");
		String phone = rset.getString("PHONE");
		String address = rset.getString("ADDRESS");
		String hobby = rset.getString("HOBBY");
		java.sql.Date enrollDate = rset.getDate("ENROLL_DATE");
		Member member = new Member(memberId, memberPwd, memberName
						, gender, age, email, phone, address, hobby, enrollDate);
		return member;	
	}
}
