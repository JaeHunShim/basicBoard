package org.board.domain;

public class SeachCriteria extends Criteria{
	
	private String searchType; //검색의 종류 (제목, 내용, 작성자 등등)
	private String keyword;	//검색 키워드 (검색창에 쓰는는 부분)
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
