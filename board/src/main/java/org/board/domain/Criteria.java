package org.board.domain;

public class Criteria { //검색기준
	
	private int page; //현재 페이지 번호(하단에 나오는 페이지 번호)
	private int perPageNum; //페이지당 보여질 데이터의 갯수 
	
	public Criteria() {
		this.page=1;
		this.perPageNum=10;
	}
	public void setPage(int page) {
		
		if(page<=0) {
			this.page=1;
			return;
		}
		this.page=page;
	}
	public void setPerPageNum(int perPageNum) {
		
		if(perPageNum<=0 || perPageNum>200) { //보여질 데이터를 100보다 작게 해놓았음  사용자에 맞추어서 
			this.perPageNum=10;
			return;
		}
		this.perPageNum=perPageNum;
	}
	
	
	public int getPageStart() {// 몇번째 페이지에서는 몇번째 번호부터 게시물이 시작번호인지 정함 
		//만약 페이지 번호가 1이라면 start페이지는 0이되서  limit 0,10이 되겠지  page가 2라면 10,10이 되겠지 
		return (this.page-1)*perPageNum; 
	}
	public int getPage() {
		return page;
	}
	public int getPerPageNum() {
		return perPageNum;
	}
	
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}
	
}
