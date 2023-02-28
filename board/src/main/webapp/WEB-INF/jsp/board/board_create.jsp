<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/additional-methods.js"></script>
<center>
	<h2>글쓰기</h2>
	<!-- 
	name 속성 : post,get 방식으로 전송할때 파라메터의 이름 ?code=11111
	class, id : css,js 에서 많이 사용
	class : 이름이 중복 가능 .code
	id : 중복허옹 안함(페이지내에 유일한 이름) #code
	 -->
<form action="board?cmd=insert" method="post" onsubmit="nullCheck()" enctype="multipart/form-data"
 id="create_frm">
	<table border="1" style = "width:800px">
	<tr>
			<td >작성자</td>
			<td><c:if test="${sessionScope.vo!=null}">
			<input type="text" name="writer" class="writer" id="writer" size="12"
			 value="${vo.userid}" maxlength="10" readonly>
			</c:if></td>
		</tr>
		<tr>
			<td >제목(*)</td>
			<td><input type="text" name="subject" class="subject" id="subject" size="12" maxlength="10"></td>

		</tr>
		
		<%-- <tr>
			<td >이메일</td>s
			<td><input type="text" name="email" class="email" id="email" size="52"
			 value="${vo.userid.email}"  maxlength="50" readonly></td>
		</tr> --%>
		
		<tr>
			<td >비밀번호(*)</td>
			<td><input type="text" name="password" class="password" id="password" size="52" maxlength="50"></td>
		</tr>
		
		<tr>
			<td height="100px">글 내용</td>
			<td>
			<textarea cols="80" rows="20" name="content" maxlength="2000"></textarea>
			</td>
		</tr>
	</table><br/>
	<input type="submit" value="작성 완료" > <input type="reset" value=" 취 소 "> 
</form>
</center>
<script type="text/javascript">

/**

 * upload_01.jsp validation
 * => jquery validation 플러그인 이용

 */

$(document).ready(function(){
	$("#create_frm").validate({		
		//규칙지정
		rules:{
			subject:{ 
				required: true
			},
			content:{
				required : true
			},
			password:{
				required : true
			}/* ,
			file:{
				required : true,
				extension:"png|jpg|gif",
				maxsizetotal:2097152//2MB
			}	 */		
		},

		//개발자가 원하는 에러 메시지 작성
		messages:{
			subject:{ //이름은 필수요소이고 글자는 최소 2~최대4까지만 허용
				required: "(코드 입력)"	
			},
			content:{
				required : "(제목 입력)"	
			},
			password:{
				required : true
			}/* ,
			file:{
				required : "(파일 선택)",	
				extension:"(이미지 파일 입력)",
				maxsizetotal:"(파일 사이즈 초과)"
			} */
		},		
		errorElement: "span",
		errorPlacement: function(error, element) {
			
			if(element.prop("type")==="file"){
				$( element ).closest( "form" )
				.find( "small[id='" + element.attr( "id" ) + "']" )
					.append(error);
			}else{
				$( element ).closest( "form" )
				.find( "label[for='" + element.attr( "id" ) + "']" )
					.append(" ").append(error);
			}
		},

	});	

});


//////////////////////////////////
	function nullCheck(){
		$("#create_frm").validate();
	}
</script>

<%@ include file="../include/footer.jsp"%>