<integer와 int형 의 차이 >
.int는 프리비티브타입으로  산술연산이 가능하지만 null로 초기화할수가 없다
. integer 는 wrapper 클래스로서 객체이다 null값을 처리 할수 있다. 그래서 sql과 연동할경우에 처리가 용이하다 
<비지니스 로직할때 순서>

능숙한 개발자라면 Controller를 먼저 작성하는게 더 효율적이지만 초보라면 파악할수 있게 화면 먼저 하는것이 좋을수가 있다.
<VO객체>

테이블의 구조를 객체화 시키기 위한 클래스

<xml mapper에 ![CDATA 쓴이유>

예를들어서 sql문안에 bno>0 이라는게 있다면
">" 문자 자체를 태그로 읽을수 있다. 그래서 이건 그냥 String형태이다 라고 알려주기 위해서

<dao의 파라미터와 매퍼와 리턴에 관한정리>

우선  mapper의 resultType의 경우는 말그대로 쿼리문을 실행후 결과값을 말하는데 이경우는 다시 리턴해서 보내줘야 할때 써먹는다 
ㅇㅖ를 들어서 select  의 경우는Dao 검색달라고 mapper에게 보내고  mapper는 검색한 결과를 다시 dao에게 다시 리턴해주어야 하기때문에 
반대로 insert의 경우는 데이터를 처리하는것에서 끝나면 되기대문에 리턴타입이 없어서 된다는 거지 !
DAO에서 보내는 파라미터는 그 형태가 하나일경우, 2개이상이나 여러개일수가 있는데 2개이상은 map 이나 vo 형태의 객체 일수가 있는데 mybatis는 map인 경우는 키값으로 조회를 하고 vo객체일 경우는 set 이나 get으로 인식해서 알맞는 값을 dao에 보내준다 

<mapper-config>

<typeAliases>
		<package name="org.board.domain"/>	
	</typeAliases>

이렇게 해주면 매퍼에 resultType 에 풀 패키지명을 지정해 주지 않아도 된다. 

<serive>(비지니스로직- 고객의 요구사항이 반영되는 부분)

<controller>
.컨트롤러 구현시 고민해야 하는 것들
공통적인 URI 경로와 각 기능별 URI
각 URI에 대한 호출방식(get,post)
결과 처리와 redirect 방식의 페이지 정
예외페이지
