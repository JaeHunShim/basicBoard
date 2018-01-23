use board;
-- 테이블 생성 
create table tbl_board(
	bno int not null auto_increment,
    title varchar(200) not null,
    content text null,
    writer varchar(50) not null,
    regdate timestamp not null default now(),
    viewcnt int default 0,
    primary key (bno)
);
select * from tbl_board;

-- 테이블이 값넣기 (bno는 auto_increment, regidate=now(),viewcnt default=0)
 
 insert into tbl_board(title,content,writer) values('첫번째 글입니다.','이렇게 내가 열심히 할줄 상상도 못했습니다.','심재훈');
 insert into tbl_board(title,content,writer) values('두번째 글입니다.','이렇게 내가 열심히 할줄 상상도 못했습니다.','이주한');
 
 -- 조건에 맞는 데이터 뽑아오기 
 
 select * from tbl_board where bno=5;
 
 -- 게시물의 전체 목록에 사용하는 sql문 (오름차순으로 불러옴)
 
 select * from tbl_board where bno>0 order by bno;
 
 -- 게시물 수정
 
 update tbl_board set content='어서 빨리 노력하자 노력만이 살길이다' where bno=2;
 
 -- 게시물 삭제
 
 delete from tbl_board where bno=2;
 
 -- 10건만 데이터를 출력하게 하는 sql( 페이징 처리 하기 위해서)
 
 select * from  tbl_board where bno>0 order by bno desc limit 0,20;
 
-- 그 다음 10건 출력  즉 페이지 보여지는 것은 limit에서 10개씩 늘려가면 되지 
select * from tbl_board where bno>0 order by bno desc limit 20,20; 
 
 -- slq문으로 다량의 더비데이터 넣기 
 
 insert into tbl_board(title,content,writer)(select title,content,writer from tbl_board);
 
 -- 더미데이터 카운트 세보기 
 select count(*) from tbl_board;
 
 delete from tbl_board;
 -- auto_increament  초기화
ALTER TABLE tbl_board AUTO_INCREMENT=1;
-- 총 게시물갯수 가지고 오기 
select count(bno) from tbl_board where bno>0;
-- 댓글 갯수에 대한 처리 하기 위해서  댓글 컬럼 추가 
alter table tbl_board add column replycnt int default 0;

update tbl_board set replycnt=replycnt+1 where bno=1022;
-- 보이는 댓글 갯수와 현재 댓글 갯수를 일치 시키기 위해서 변경 	
update tbl_board set replycnt=(select count(rno) from tbl_reply where bno=tbl_board.bno)where bno>0;

 