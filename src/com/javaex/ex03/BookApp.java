package com.javaex.ex03;


public class BookApp {

	public static void main(String[] args) {

		BookDao bookDao = new BookDao();
		AuthorDao authorDao = new AuthorDao();
		
		// 작가5명 등록
		AuthorVo authorVo = new AuthorVo("강경애", "황해남도 송화");
		authorDao.authorInsert(authorVo);
		
		AuthorVo authorVo = new AuthorVo("헤밍웨이", "일리노이");
		authorDao.authorInsert(authorVo);
		
		authorDao.authorInsert("강성주", "미상");
		
		authorDao.authorInsert("홍명희", "충청북도 괴산");
		
		authorDao.authorInsert("김소월", "평안북도 구성");
		

		// 작가 출력
		
		

		// 작가2명 삭제
		authorDao.authorDelete(2);
		authorDao.authorDelete(13);
		

		// 작가1명 수정
		authorDao.authorUpdate("존 하비", "영국",10 );
		
		// 작가 출력
		

		// 책 5권등록

		
		// 책 수정

		// 책 삭제

		// 책 1권출력

		// 책만 다 출력

		// 책+작가 다출력

	}

}
