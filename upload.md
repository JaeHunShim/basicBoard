게시판 업로드 적용시키기
===
* ### 데이터 베이스에 업로드 파일에 대한 기록 처리
  * form 데이터와 파일데이터를 구분해서 정송하는 방식 이용, 즉 파일은 별도로 업로드하고 데이텅베이스에 기록될 데이터는 나중에 전송하는 방식을 사용할 예정
---
* ### 게시물 등록준비
  * 사용자가 첨부팡일을 올릴경우 드래그-드랍으로 파일을 서버에 업로드 시키는 방식으로 할예정
  * 게시물 등록버튼을 이용해서 게시물을 등록하게함
---
* ### 파일 업로드된 파일명 처리 방법
  1. 서버에 업로드된 파일이름을 getFileInfo를 통해서 데이터 정보를 추출한한다.
  2. 추출한 정보로 javascript 객체를 생성
  3. handlebars 적용
  4. html적용
  5. 마지막으로 view에 적용한다.
---
* ### 상세조회 페이지에서 업로드한 파일 보는 방식
  * 글을 조회했을때 데이터베이스에서 조회한걸 가지고 Ajax를 이용해서 현재 게시물의 첨부파일을 별도로 가지고오는 방법사용
---
* ### Source & View(controller와 util파일은 springStudy와 동일 )

  * #### Mapper
    ![mapper](./img/업로드매퍼.png)
---
* ### 오류와 해결 방법
  * hadlebar 을 못잡아줄때  생기는 오류
    * You must pass a string or Handlebars AST to Handlebars.compile. You passed undefined
  * 해결방법
    * var template=Handlebars.compile($("#templateAttach").html());
    이부분을 제일 밑으로 잡아준다.