<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<link rel="stylesheet" href="/board/star-rating/css/star-rating.css"
	media="all" type="text/css" />
<link rel="stylesheet"
	href="/board/star-rating/themes/krajee-svg/theme.css" media="all"
	type="text/css" />
<script src="/board/star-rating/js/star-rating.js"
	type="text/javascript"></script>
<script src="/board/star-rating/themes/krajee-svg/theme.js"
	type="text/javascript"></script>
<center>
	<h2>${v.subject}</h2>
	<form method="post" action="board?cmd=update" name="update_frm"
		enctype="multipart/form-data">
		<input type="hidden" name="num" value="${v.num}">
		<table border="1" style="width: 900px">
			<tr>
				<td>작성자</td>
				<td class="org_input">&nbsp;${v.writer}
				&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
				&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
				&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
					│조회 수 │ &nbsp;&nbsp;&nbsp;${v.readcount}  │</td>
				<td class="mod_input" style="display: none"><input type="text"
					size="80" name="writer" id="writer" maxlength="10" value="${v.writer}">
				</td>
				
			</tr>
			<tr>
				<td>제목</td>
				<td class="org_input">&nbsp;${v.subject}</td>
				<td class="mod_input" style="display: none"><input type="text"
					size="80" name="subject" id="subject" maxlength="10" value="${v.subject}">
				</td>
			</tr>
			<tr>
				<td height="100px">글내용</td>
				<td class="org_input"><textarea cols="80" rows="20" readonly>&nbsp;${v.content}</textarea>
				</td>
				<td class="mod_input" style="display: none"><textarea cols="80"
						rows="20" name="content" id="content">${v.content}</textarea></td>
			</tr>
			<%-- <tr>
				<td>이메일</td>
				<td class="org_input">&nbsp;${v.email}</td>
				<td class="mod_input" style="display: none"><input type="text"
					size="80" name="email" id="email" maxlength="10" value="${v.email}">
				</td>
			</tr> --%>
			<tr>
				<td>비밀번호</td>
				<td class="org_input">&nbsp;${v.password}</td>
				<td class="mod_input" style="display: none"><input type="text"
					size="80" name="password" id="password" maxlength="10" value="${v.password}">
				</td>
				
			</tr>
			
		</table>
		<br>
		<div class="btns1" style="display: block">
		<!-- <button type="button" class="btn btn-success" -->
			<button type="button" 
				onclick="javascript:location.href='board'">글목록 보기</button>
			<%-- <c:if test="${sessionScope.vo.admin==1}"> --%>
			<button type="button"  onclick="modifyboard()">글수정</button>
			<%-- </c:if> --%>
			<button type="button"  onclick="checkDelete()">글삭제</button>
		</div>
		<div class="btns2" style="display: none">
			<button type="button"  onclick="saveboard();">저장하기</button>
			<button type="button"  onclick="modifyCancel()">수정취소</button>
		</div>
	</form>
	
	</center>
	<div id="pageNum" style="display:none">0</div>
	<br> <label for="input-1" class="control-label">도서 평점</label> <input
		id="input-1" name="input-1" class="rating rating-loading" data-min="0"
		data-max="5" value="${avg}" data-step="0.1" readonly> <br>

	
	<c:if test="${sessionScope.vo!=null}">
		<br>
 	&nbsp;&nbsp;&nbsp;<label for="score" class="control-label">별점주기</label>
		<table>
			<tr>
				<td width="30"></td>
				<td width="250"><input id="score" name="score"
					class="rating rating-loading" data-min="0" data-max="5"
					data-step="1"></td>
				<td>평가글 남기기 : <input type="text" id="cmt" name="cmt" size="50"
					maxlength="100">

					<button type="button" id="saveBtn" onclick="saveStar()">저장하기</button>
					<!-- AJEX를 이용한 비동기 통신 -->
				</td>
			</tr>
		</table>
		
	</c:if>
	<div id="pageNum" style="display:none">0</div>
	<!-- 별점 리스트 출력 -->
	<div id="cmtList"></div>
	<center>
	<button type="button" id="btn_cmtMore" style="display: none"
		onclick="getStar()">더보기</button>
</center>

<script type="text/javascript">
	/* =========================================================================
	 Ready func : 페이지가 로딩되면서 해야 할 일: 도서번호에 해당되는 1 page 가져와서 cmtList 넣어주는
	 ========================================================================= */
	 $(document).ready(function() {
		getStar()
	}); 

	/* =========================================================================
	 댓글 가져오기 : 도서번호와 페이지번호를 넘겨주고(전송) , 출력할 리스트와 다음페이지의 여부(송신)
	 ========================================================================= */
	 function getStar() {
		var url = "board?cmd=cmtlist";
		var param = {
			"num" : "${v.num}",
			"page" : $("#pageNum").html() * 1 + 1
		};
		//증가한 페이지를
		$("#pageNum").html($("#pageNum").html()*1+1);
		doAjaxHtml(url, param, getStarAfter);
	}
	 

	/* =========================================================================
	 댓글 가져오기 After : 송신된 데이터를 처리 (댓글 출력, 더보기 버튼 처리)
	 ========================================================================= */
	function getStarAfter(view) {//view : 서버에서 넘어온 데이터
		if (view == "err") {
			// 표시할 자료 없음
		} else {
			console.log(view);
			view = view.replace(/'/gi, '"')
			console.log(view);
			var list = view.split("!@");//배열과 같다.
			var pageInfo = $.parseJSON(list[0]);//pageInfo json 객체
			//list[0] --> {"next":"true"} 또는 {"next":"false"}
			//btn_cmtMore
			if (pageInfo.next == "true")
				$("#btn_cmtMore").css("display", "block");
			else
				$("#btn_cmtMore").css("display", "none");
			console.log(pageInfo.next);
			//표시할 html 태그 조립
			var html = "<table>";
			for (i = 1; i < list.length; i++) {
				html += listMaker($.parseJSON(list[i]));
			}
			html += "</table>";
			$("#cmtList").append(html);

			var $input = $('input.rating');
			if ($input.length) {
				$input.removeClass('rating-loading').addClass('rating-loading')
						.rating();
			}
		}
	}
	/* =========================================================================
	 tr 태그를 조립하는 함수
	 ========================================================================= */
	function listMaker(json) {

		var str = '<tr>';
		str += '<td width="30"></td><td width="250">';
		str += '<input style="text-align:left" id="score" name="score" class="rating rating-loading" data-min="0" data-max="5" data-step="1" value='+json.score+' data-readonly="true"></td>';
		str += '<td>' + json.userid + '님: ' + json.cmt + '</td></tr>';
		return str;

	}

	/* =========================================================================
	 별점과 댓글 저장하기
	 ========================================================================= */
	function saveStar() {
		var url = "board?cmd=cmt";
		var param = {
			"userid" : "${sessionScope.vo.userid}",
			"num" : "${v.num}", // EL-중괄호
			"score" : $("#score").val(),// 제이쿼리-소괄호
			"cmt" : $("#cmt").val()
		}
		console.log(param);
		doAjaxHtml(url, param, saveStarAfter);
	}

	/* =========================================================================
	 별점과 댓글 저장하기 After
	 ========================================================================= */
	function saveStarAfter() {
		console.log("saveStarAfter");
		//페이지 저장 input 0
		$("#pageNum").html("0");

		//출력된 cmt 리스트 없애기
		$("#cmtList").html("");

		//getStar 호출
		getStar();

	}

	function saveboard() {
		//필수항목체크
		var data = document.getElementById("password");//객체 가져오기
		//data.value=data.value.trim();//공백제거
		if (data.value.trim() == "") {
			alert("비밀번호를 입력하세요.");
			data.focus();
			return;
		}
		var data = document.getElementById("subject");//객체 가져오기
		if (data.value.trim() == "") {
			alert("제목을 입력하세요.");
			data.focus();
			return;
		}
		var data = document.getElementById("content");//객체 가져오기
		if (data.value.trim() == "") {
			alert("내용을 입력하세요.");
			data.focus();
			return;
		}

		document.update_frm.submit();
	}
	function modifyboard() {
		//alert("aaa");
		//원래의 td 출력용 display none			
		$(".org_input").css("display", "none");

		//입력용 td display block
		$(".mod_input").css("display", "block");
		//버튼그룹 dispaly 설정
		$(".btns1").css("display", "none");
		$(".btns2").css("display", "block");
	}
	function modifyCancel() {
		$(".org_input").css("display", "block");
		$(".mod_input").css("display", "none");

		$(".btns1").css("display", "block");
		$(".btns2").css("display", "none");
	}

	function checkDelete() {
		if (confirm("글을 삭제 하시겠습니까?")) {
			//삭제수행
			location.href = "board?cmd=del&num=" + ${v.num};
		}
	}
</script>
<%@ include file="../include/footer.jsp"%>