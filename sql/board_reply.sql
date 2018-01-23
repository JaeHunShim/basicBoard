use board;
-- 댓글 테이블 작성
create table tbl_reply(
	rno int not null auto_increment,
    bno int not null default 0,
    replytext varchar(1000) not null,
    replyer varchar(50) not null,
    regdate timestamp not null default now(),
    updatedate timestamp not null default now(),
    primary key (rno)
);

-- 테이블 검색
select * from tbl_reply;
-- 테이블 데이터 삭제
delete from tbl_reply;

-- 댓글 테이블에 제약조건으로 외래키 주기 

alter table tbl_reply add constraint fk_board foreign key(bno)
references tbl_board(bno);
-- 댓글 테이블 auto_increament 초기화 
alter table tbl_reply auto_increment=1;
-- 댓글 작성
insert into tbl_reply(bno,replytext,replyer)values('1','첫 댓글입니다.','jaehuniya');
insert into tbl_reply(bno,replytext,replyer)values('2','두번째 댓글입니다.','jaehuniya');
-- 댓글 수정
update tbl_reply set replytext='첫댓글 수정입니다.';
-- 조건에 맞는 댓글 검색
select rno,bno,replytext,replyer,regdate,updatedate from tbl_reply where rno=1 order by rno desc ,regdate desc;
-- 댓글 삭제
delete from tbl_reply where rno=2;
-- 댓글 페이징 처리 
select * from tbl_reply where bno=2 order by rno desc limit 0,10;
