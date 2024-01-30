package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

	// 필드

	// 생성자
	// 기본 생성자 사용

	// 메소드

	// 메소드 - 일반

	
	// 작가 리스트
	public List<AuthorVo> authorList() {
		
		//리스트만들고
		//db에서 데이터 가져오고
		//리스트에 추가하기 
		//리스트 주소 전달하기 
		
		//리스트 준비
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();
		
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQL문 준비
			String query = "";
			query += " select author_id ";
			query += "        ,author_name ";
			query += "        ,author_desc ";
			query += " from author ";

			//바인딩
			pstmt = conn.prepareStatement(query);
			
			//실행
			rs = pstmt.executeQuery();
			
			//검색결과에서 데이터 꺼내기
			while(rs.next()) {
				
				int id = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");
				
				//Vo로 묶기
				AuthorVo vo = new AuthorVo(id, name, desc);
				
				//리스트에 추가
				authorList.add(vo);
			}
	
			// 4.결과처리
			//리스트 이용해서 출력
			/*
			for(int i=0; i<authorList.size(); i++) {
				int id = authorList.get(i).getAuthorId();
				String name = authorList.get(i).getAuthorName();
				String desc = authorList.get(i).getAuthorDesc();
				
				System.out.println(id + ". " + name + "\t" + desc);
				
			}
			*/
			
			for(AuthorVo authorVo : authorList) {
				int id = authorVo.getAuthorId();
				String name = authorVo.getAuthorName();
				String desc = authorVo.getAuthorDesc();
				
				System.out.println(id + ". " + name + "       " + desc);
			}
			
			System.out.println(authorList.size() + "명의 작가가 등록되어 있습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		return authorList;
	} //authorList()
	
	
	
	
	

	// 작가 수정
	public void authorUpdate(String name,String desc, int no) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " update author ";
			query += " set author_name = ?";
			query += "     ,author_desc = ?";
			query += " where author_id = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			pstmt.setInt(3, no);

			// 실행
			int count = pstmt.executeUpdate();

			System.out.println(count + "건 등록되었습니다");

			// 4.결과처리
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
	}

	// 작가 삭제
	public int authorDelete(int no) {

		int count = -1;
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "delete from author ";
			query += " where author_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			count = pstmt.executeUpdate();

			System.out.println(count + "건이 처리되었습니다.");

			// 4.결과처리
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return count;
		
	}// authorDelete()

	// 작가 등록
	public int authorInsert(String name, String desc) {

		int count = -1;
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += "insert into author";
			query += " values(null, ?, ?)";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + " 건 등록되었습니다.");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return count;
	}// authorInsert()

}
