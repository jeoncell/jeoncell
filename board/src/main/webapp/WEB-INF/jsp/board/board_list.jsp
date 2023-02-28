<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<center>
	<h2>board List</h2>
	<form method="post" action="board" name="search_form">
	<input type="hidden" name="cmd" value="list">
		<select name="item" id="item">
		 	<option value="writer">글쓴이</option>
		 	<option value="subject">제목</option>
		 	<option value="content">내용</option>
		</select>
		<input type="text" name="keyword" id="keyword" size="20">
		<input type="submit" value="검색">
	</form>
	<div style="height:10px"></div>


<!-- // num, writer, subject, email, content, password, reg_date, readcount -->
<table border="1" style="width:1000, align:center">
	<tr align="center">
		<th style="text-align:center">글번호</th>
		<th style="text-align:center">글쓴이</th>
		<th style="text-align:center">제목</th>
		<th style="text-align:center">작성일</th>
		<th style="text-align:center">조회수</th>
	</tr>
<c:choose>
	<c:when test="${list==null}">
		<tr align="center">
			<td colspan="5">글 목록이 존재 하지 않습니다.</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach var="vo" items="${list}" varStatus="st">
				<tr align="center">
					<td style="width:70px">${st.count}</td>
					<td style="width:100px">${vo.writer}</td>
					<td style="width:500px"><a href="board?cmd=view&num=${vo.num}">${vo.subject}</a></td>
					<td style="width:100px">${vo.reg_date}</td>
					<td>${vo.readcount}</td>
				</tr>	
			</c:forEach>	
		</c:otherwise>
</c:choose>
</table>
</center>
<!-- 페이지 관련해서  -->
<div style="height:15px"></div>


<center>
<c:if test="${pageVo.prev }"><a href="board?cmd=list&page=${pageVo.page-1}&item=${pageVo.columnName}&keyword=${pageVo.keyword}">이전페이지</a></c:if>
<c:forEach var="i" begin="${pageVo.beginPage}" end="${pageVo.endPage}">
	<c:choose>
		<c:when test="${i==pageVo.page}">${i}&nbsp;</c:when>
		<c:otherwise><a href="board?cmd=list&page=${i}&item=${pageVo.columnName}&keyword=${pageVo.keyword}">${i}</a>&nbsp;</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test="${pageVo.next }"><a href="board?cmd=list&page=${pageVo.page+1}&item=${pageVo.columnName}&keyword=${pageVo.keyword}">다음페이지</a></c:if>
<!--onclick="location.href='board/board_create.jsp'"  -->
<%-- <c:if test="${sessionScope.vo.admin==1 }"> --%>
	&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" onclick="location.href='board?cmd=insertpage'">글쓰기</button>
<%-- </c:if> --%> 
</center>

<<script type="text/javascript">

	function count(){
		const resultElement = document.getElementById('result');
		let number = resultElement.innerText;
		
		if(type == 'plus') {
			   number = parseInt(number) + 1;
			}else if(type == 'minus') {
			   number = parseInt(number) - 1;
			}
		resultElement.innerText = number;
	
	}

</script>




<%@ include file="../include/footer.jsp" %>