package com.javaex.ex03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

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
	private void getConnection() { // 안에서만 쓰는 메소드 --> private
									// 메소드가 꼭 public은 아님

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
	public List<BookVo> bookList() {

		// 리스트 준비
		List<BookVo> bookList = new ArrayList<BookVo>();

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select book_id, ";
			query += "        title, ";
			query += "        pubs, ";
			query += "        pub_date, ";
			query += "        author_id ";
			query += " from book ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 검색결과에서 데이터 꺼내기
			while (rs.next()) {

				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");

				// Vo로 묶기
				BookVo vo = new BookVo(bookId, title, pubs, pubDate, authorId);

				// 리스트에 추가
				bookList.add(vo);
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

			for (int i = 0; i < bookList.size(); i++) {
				int bookId = bookList.get(i).getBookId();
				String title = bookList.get(i).getTitle();
				String pubs = bookList.get(i).getPubs();
				String pubDate = bookList.get(i).getPubDate();
				int authorId = bookList.get(i).getAuthorId();

				System.out.println(bookId + ". " + title + "\t" + pubs + "\t" + pubDate + "\t" + authorId);

			}

			System.out.println(bookList.size() + "개의 책이 등록되어 있습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return bookList;
	} // bookList()

	// 작가 수정 1
	public void authorUpdate(String title, String pubs, String date, int aId, int bId) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " update book ";
			query += " set title = ?";
			query += "     ,pubs = ?";
			query += "     ,pub_date = ?";
			query += "     ,author_id = ? ";
			query += " where book_id = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, pubs);
			pstmt.setString(3, date);
			pstmt.setInt(4, aId);
			pstmt.setInt(5, bId);

			// 실행
			count = pstmt.executeUpdate();

			System.out.println(count + "건 등록되었습니다");

			// 4.결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

	}

	// 작가 수정 2
	public void authorUpdate(BookVo bookVo) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " update book ";
			query += " set title = ?";
			query += "     ,pubs = ?";
			query += "     ,pub_date = ?";
			query += "     ,author_id = ? ";
			query += " where book_id = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());
			pstmt.setInt(5, bookVo.getBookId());

			// 실행
			count = pstmt.executeUpdate();

			System.out.println(count + "건 등록되었습니다");

			// 4.결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

	}

	// 작가 삭제
	public int authorDelete(int bId) {

		int count = -1;

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "delete from book ";
			query += " where book_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bId);

			// 실행
			count = pstmt.executeUpdate();

			System.out.println(count + "건이 처리되었습니다.");

			// 4.결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;

	}

	// 작가 등록 1
	public int authorInsert(BookVo bookVo) {

		int count = -1; // 0이나 1은 있어가지구 안되도 확인할 수가 없어서
						// -1건을 등록, 삭제했다고 할수없으니까

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += "insert into book";
			query += " values(null, ?, ?, ?, ?)";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + " 건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

	// 작가 등록 2
	public int authorInsert(String title, String pubs, String date, int aId) {

		int count = -1;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += "insert into book";
			query += " values(null, ?, ?, ?, ?)";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, pubs);
			pstmt.setString(3, date);
			pstmt.setInt(4, aId);
			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + " 건 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

	// bookSelect() <-- 책 데이터만 가져오기
	List<BookVo> bookList = new ArrayList<BookVo>();

	public void bookSelect() {
		this.getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL준비
			String query = "";
			query += " select book_id, ";
			query += "        title, ";
			query += "        pubs, ";
			query += "        pub_date, ";
			query += "        author_id ";
			query += " from book ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			rs.next();

			// 검색결과에서 데이터 꺼내기
			while (rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");

				// Vo로 묶기
				BookVo vo = new BookVo(bookId, title, pubs, pubDate, authorId);

				bookList.add(vo);
			}

			// 4.결과처리
			// 리스트 이용해서 출력
			for (int i = 0; i < bookList.size(); i++) {
				int bookId = bookList.get(i).getBookId();
				String title = bookList.get(i).getTitle();
				String pubs = bookList.get(i).getPubs();
				String pubDate = bookList.get(i).getPubDate();
				int authorId = bookList.get(i).getAuthorId();

				System.out.println(bookId + ". " + title + "\t" + pubs + "\t" + pubDate + "\t" + authorId);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	}

	// bookSelectAll() <-- 책+작가 데이터 다 가져오기
	public void bookSelectAll() {

		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL준비
			String query = "";
			query += " select book_id, ";
			query += "        title, ";
			query += "        pubs, ";
			query += "        pub_date, ";
			query += "        b.author_id, ";
			query += "        author_name, ";
			query += "        author_desc ";
			query += " from book b ";
			query += " left join author a ";
			query += " on b.author_id = a.author_id ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			rs.next();

			// 4.결과처리
			while (rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("b.author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");

				/*
				 * 번호로 해도 가능 int id = rs.getInt(1); String name = rs.getString(2); String desc =
				 * rs.getString(3);
				 */

				System.out.println(bookId + ". " + title + ", " + pubs + ", " + pubDate + ", " + authorId + ", "
						+ authorName + ", " + authorDesc);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
	}

	// bookSelectOne(int bookId) <-- 2번책+작가 데이터 가져오기
	public void bookSelectOne() {

		this.getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL준비
			String query = "";
			query += " select book_id, ";
			query += "        title, ";
			query += "        pubs, ";
			query += "        pub_date, ";
			query += "        b.author_id, ";
			query += "        author_name, ";
			query += "        author_desc ";
			query += " from book b ";
			query += " left join author a ";
			query += " on b.author_id = a.author_id ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			rs.next();

			// 검색결과에서 데이터 꺼내기

			rs.next();
			rs.next();

			int bookId = rs.getInt("book_id");
			String title = rs.getString("title");
			String pubs = rs.getString("pubs");
			String pubDate = rs.getString("pub_date");
			int authorId = rs.getInt("b.author_id");
			String authorName = rs.getString("author_name");
			String authorDesc = rs.getString("author_desc");

			System.out.println(bookId + ". " + title + ", " + pubs + ", " + pubDate + ", " + authorId + ", "
					+ authorName + ", " + authorDesc);

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
	}

}
