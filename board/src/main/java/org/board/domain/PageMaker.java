package org.board.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {	//하단 페이징 처리 

	private int totalCount; //총 게시물수 
	private int startPage; // 시작페이지 번호 즉  번호가 10개씩 보이게 한다면 11, 21, 31, 41 이 됨
	private int endPage; // 보이는 페이지의 마지막 페이지번호 즉 , 10,20,30,40,50 
	private boolean prev; //이전페이지  
	private boolean next;	// 다음페이지 
	
	private int displayPageNum=10; //페이지 번호를 10개씩 보이게함 이건  숫자만바꾸면 수정딤.
	
	private Criteria cri;	//하단 페이징 처리 하기 위해서  cirteria 클래스에서 get해오고 set해주기 휘해서 사용
	
	private void calcData() {
		
		endPage=(int)(Math.ceil(cri.getPage()/(double)displayPageNum)*displayPageNum); //현재페이지가 3이라면 끝페이지는 10이어야겠지! 3/10올림처리=1*10=10이나옴
		startPage=(endPage-displayPageNum)+1; // endpage10이고 displayPageNum이 10  빼면 0이고 0+1 하니까 시작페이지는 1이 되지 
		int tempEndPage=(int)(Math.ceil(totalCount/(double)cri.getPerPageNum())); 
		//몇개씩 보여직 할지 계산해서 다시 endPage를 계산하는 부분
		if(endPage>tempEndPage) {
			endPage=tempEndPage;
		}
		prev=startPage==1?false:true; //단순하게 시작페이지가 11이 되면 prev활성화 시키는 거임 
		next=endPage*cri.getPerPageNum()>=totalCount?false:true; //next가 활성화될려면 뒤에 남은 데이터가 있어야 하는데  총 300개의 데이터가 있다면 지금 endPage가 20이고
																//10개씩 보여주고 있다면 계산하면 200이고  총데이터는 300인데 200>300 true가이기때문에 활성화 
	}
	public int getTotalCount() {
		return totalCount;
		
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();  //totalCount가 설정되는 시점에서 실행하게 됨.모든건 totalCount 데이터가 몇개인지에 따라서 변하기때문에  
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	//uri에 page 뿐만 아니라 perPageNum 값을 받아서 uri에 실어서 보내는 방법 
	public String makeQuery(int page) {
		
		UriComponents uriComponents= UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.build();
		return uriComponents.toUriString();
	}
	//검색할때 uri에 사용할 문자열을 생성하는 객체 각각 perPageNum,searchType,keyword를 받아와서 uri에 부려줌 
	public String makeSearch(int page) {
		UriComponents uriComponents= UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.queryParam("searchType", ((SeachCriteria)cri).getSearchType())
				.queryParam("keyword", encoding(((SeachCriteria)cri).getKeyword()))
				.build();
		return uriComponents.toUriString();
	}
	private String encoding(String keyword) {
		
		if(keyword==null || keyword.trim().length()==0) {	//keyword.trim().length():키워드에서 공백문자를 제거한 문자열의 길이가 0이라면 이라는뜻 즉 둘다 데이터가없으면~
			return "";
		}
		try {
			return URLEncoder.encode(keyword,"UTF-8");
		}catch(UnsupportedEncodingException e){
			return "";
		}
	}
}
