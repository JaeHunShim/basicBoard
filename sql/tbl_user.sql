use board;

-- 회원 테이블 생성 
create table tbl_user(
	uid varchar(50) not null,
    upw varchar(50) not null,
    uname varchar(100) not null,
    upoint int not null default 0,
    primary key(uid)
);

-- 연습용으로 insert 

insert into tbl_user(uid,upw,uname)values("jaehuniya","wognsl83","심재훈01");
insert into tbl_user(uid,upw,uname)values("jaehuniya1","wognsl830","심재훈02");
insert into tbl_user(uid,upw,uname)values("jaehuniya2","wognsl831","심재훈03");
insert into tbl_user(uid,upw,uname)values("jaehuniya3","wognsl832","심재훈04");
insert into tbl_user(uid,upw,uname)values("jaehuniya4","wognsl833","심재훈05");
insert into tbl_user(uid,upw,uname)values("jaehuniya5","wognsl834","심재훈06");

-- 유저테이블 보기

select * from tbl_user;

-- 세션정보를 담을 column 추가

alter table tbl_user add column sessionkey varchar(50) not null default 'none';

 -- 서버에서 다시 한번 유효시간안에 다시 접속했는지 판단하는데 사용하는 컬럼
alter table tbl_user add column sessionlimit timestamp;