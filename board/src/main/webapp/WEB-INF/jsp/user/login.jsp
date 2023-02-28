<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
  
  
  <div class="container">
  	<div class="col-lg-4"></div>
  	<div class="col-lg-4">
  		<div class="jumbotron" style="padding-top: 20px;">
  				<h3 style="text-align: center">로그인 화면</h3>
  				<div style="height:20px"></div>
  				<form action="user" method="post" name="login_frm" onsubmit="return loginCheck()">
  				<input type="hidden" name="cmd" value="login">
  				<div class="form-group">
  					<input type="text" class="form-control" placeholder="아이디" name="userid" id="userid" maxlength="20">
  				</div>
  				<div class="form-group">
  					<input type="password" class="form-control" placeholder="비밀번호" name="userPwd" id="userPwd" maxlength="20">
  				</div>
  				<div style="height:20px"></div>
  				<div class="form-group text-center">
  				
  				<button type="submit" class="btn btn-primary form-control">로그인</button>
				</div>	
  			</form>
  		</div>
  	</div>
  	<div class="col-lg-4"></div>
  </div>
  <!--  -->
  
  
  
  
<script type="text/javascript">
function loginCheck(){
	let userid=document.getElementById("userid");
	if(userid.value.trim()==""){
		alert("아이디를 입력해주세요.")
		userid.focus();
		return false;
	}
	userid.value=userid.value.trim();//서블릿에서 공백제거할 필요 없음.
	
	let password=document.getElementById("userPwd");
	if(password.value==""){
		alert("비밀번호를 입력해주세요.")
		password.focus();
		return false;
	}
		document.login_frm.submit;
}

</script>
  
  
  

  <%@ include file="../include/footer.jsp" %>
