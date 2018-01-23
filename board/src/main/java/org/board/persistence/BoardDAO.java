package org.board.persistence;

import java.util.List;

import org.board.domain.BoardVO;
import org.board.domain.Criteria;

public interface BoardDAO {
	// 게시판 데이터 삽입
	public void create(BoardVO vo) throws Exception;
	// 해당 게시물 내용가지고 오기
	public BoardVO read(Integer bno) throws Exception;
	// 해당 게시물 수정
	public void update(BoardVO vo) throws Exception;
	// 해당 게시물 삭제
	public void delete(Integer bno) throws Exception;
	// 메인 게시판 목록 
	public List<BoardVO>listAll() throws Exception;
	//페이징 처리관련 기능
	public List<BoardVO>listPage(int page) throws Exception;
	//BoardDAO에  리스트를 출력하는 부분
	public List<BoardVO> listCriteria(Criteria cri) throws Exception; 
	//전체 개시물 즉 totalCount가지고오는 dao
	public int countPaging(Criteria cri) throws Exception;
	//댓글 갯수 증가시킴
	public void updateReplyCnt(Integer bno, int amount) throws Exception;
	//조회수 증가 시키기 
	public void updateViewCnt(Integer bno) throws Exception;
	//파일 업로드 등ㄺ
	public void addAttach(String fullName) throws Exception;
	//파일 업로드한 정보 가지고오기 (상세보기에서 보기 위해서)
	public List<String>getAttach(Integer bno) throws Exception;
	//파일 수정 1(데이터베이스 내에서 삭제)
	public void deleteAttach(Integer bno) throws Exception;
	//파일 삭제한후에 다시 등록 
	public void replaceAttach(String fileName, Integer bno) throws Exception;
	
}
