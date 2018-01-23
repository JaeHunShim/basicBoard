use board;
-- 파일업로드 테이블 생성
create table tbl_attach(
	fullName varchar(150) not null,
    bno int not null,
    regdate timestamp default now(),
    primary key(fullName)
);
-- board의 bno 를 참조하는 bno 외래키 생성 
alter table tbl_attach add constraint fk_board_attach foreign key(bno) references tbl_board(bno);

-- 업로드 파일 검색

select * from tbl_attach;

-- 상세보기에서 업로드파일 보기

select fullName from tbl_attach where bno=1024 order by regdate;

-- 파일수정(파일 삭제 하고 다시 insert하게 처리)

delete from tbl_attach where bno=1023;
insert into tbl_attach(fileName,bno)values('파일명',1023);

