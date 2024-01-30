package com.javaex.ex02;

public class bookApp {

	public static void main(String[] args) {

		AuthorDao authorDao = new AuthorDao();
		
		// authorDao.authorInsert("서장훈", "농구선수");

		// authorDao.authorUpdate("이효리", "가수",12 );

		// authorDao.authorDelete(4);

		AuthorVo authorVo = new AuthorVo("황일영", "개발강사");
		authorDao.authorInsert(authorVo);

		/*
		for (AuthorVo authorVo : authorList) {
			System.out.println(
					authorVo.getAuthorId() + ", " + authorVo.getAuthorName() + ", " + authorVo.getAuthorDesc());
		}*/
	}

}
