
use pension;

-- 유저 테이블 생성 
create table user(
	user_id varchar(50) not null, 
    user_password varchar(50) not null,
    user_name varchar(50) not null,
    user_address varchar(100) not null,
    user_email varchar(100) not null,
    user_phone varchar(50) not null,
    regdate timestamp not null default now(),
    primary key(user_id)
);

select * from user;

-- 아이디 체크 
select count(*) from user where user_id ="jeahuniya";