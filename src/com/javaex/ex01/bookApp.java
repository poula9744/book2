package com.javaex.ex01;

import java.util.List;

public class bookApp {

	public static void main(String[] args) {
		
		AuthorDao authorDao = new AuthorDao();
		
		authorDao.authorUpdate("기안84", "웹툰작가",12 );
		
		
		
		//authorDao.authorDelete(2);
		
		List<AuthorVo> authorList = authorDao.authorList();
		
		
		
		
	}

}
