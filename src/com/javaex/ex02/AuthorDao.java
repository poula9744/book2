package com.javaex.ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String id = "book";
	private String pw = "book";
	
	// 생성자
	// 기본 생성자 사용

	// 메소드

	// 메소드 - 일반
	private void getConnection() { //안에서만 쓰는 메소드 --> private 
								   //메소드가 꼭 public은 아님 

		// 0. import java.sql.*;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	
	private void close() {
		
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

	
	
	/////////////////////////////////////////////////////////
	// 작가 리스트
	public List<AuthorVo> authorList() {

		// 리스트 준비
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select author_id ";
			query += "        ,author_name ";
			query += "        ,author_desc ";
			query += " from author ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 검색결과에서 데이터 꺼내기
			while (rs.next()) {

				int id = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");

				// Vo로 묶기
				AuthorVo vo = new AuthorVo(id, name, desc);

				// 리스트에 추가
				authorList.add(vo);
			}

			// 4.결과처리
			// 리스트 이용해서 출력
			/*
			 * for(int i=0; i<authorList.size(); i++) { int id =
			 * authorList.get(i).getAuthorId(); String name =
			 * authorList.get(i).getAuthorName(); String desc =
			 * authorList.get(i).getAuthorDesc();
			 * 
			 * System.out.println(id + ". " + name + "\t" + desc);
			 * 
			 * }
			 */

			for (AuthorVo authorVo : authorList) {
				int id = authorVo.getAuthorId();
				String name = authorVo.getAuthorName();
				String desc = authorVo.getAuthorDesc();

				System.out.println(id + ". " + name + "       " + desc);
			}

			System.out.println(authorList.size() + "명의 작가가 등록되어 있습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
			
		this.close();

		return authorList;
	} // authorList()

	// 작가 수정
	public void authorUpdate(String name, String desc, int no) {

		int count = -1;
		
		this.getConnection();
		
		try {
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
			count = pstmt.executeUpdate();

			System.out.println(count + "건 등록되었습니다");

			// 4.결과처리
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();
		
	}

	// 작가 삭제
	public int authorDelete(int no) {

		int count = -1;

		this.getConnection();
		
		try {
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
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		}
			
		this.close();
		return count;

	}// authorDelete()

	// 작가 등록
	public int authorInsert(String name, String desc) {

		int count = -1; //0이나 1은 있어가지구 안되도 확인할 수가 없어서
						//-1건을 등록, 삭제했다고 할수없으니까

		this.getConnection();

		try {

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

		}  catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		return count;
	}
		// 작가등록2
		public int authorInsert(AuthorVo authorVo) {

			int count = -1; 
			
			this.getConnection();

			try {

				// 3. SQL문 준비 / 바인딩 / 실행
				// -SQL문 준비
				String query = "";
				query += " insert into author ";
				query += " values(null, ?, ? ) ";

				// -바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, authorVo.getAuthorName());
				pstmt.setString(2, authorVo.getAuthorDesc());

				// -실행
				count = pstmt.executeUpdate();

				// 4.결과처리
				System.out.println(count + "건 등록 되었습니다.");

			} catch (SQLException e) {
				System.out.println("error:" + e);
				
			} 

			this.close();
			

			return count;
			
	
	}

}
