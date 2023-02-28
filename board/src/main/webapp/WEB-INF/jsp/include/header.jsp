<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/board/css/bootstrap.min.css">
<script type ="text/javascript" src="/board/js/common.js" ></script>
<script type ="text/javascript" src="/board/js/jquery-3.6.3.min.js" ></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>

  <nav class="navbar navbar-default">
  	<div class="navbar-header">
  		<button type="button" class="navbar-toggle collapsed"
  			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
  			aria-expanded="false">
  			<span class="icon-bar"></span>
  			<span class="icon-bar"></span>
  			<span class="icon-bar"></span>
  		</button>
  		<a class="navbar-brand" href="/board?cmd=list">JSP 게시판 웹 사이트</a>
  	</div>
  	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
  		<ul class="nav navbar-nav">
  			<li><a href="index.html">메인</a></li>
  			<li><a href="/board?cmd=list">1번 게시판</a></li>
  		</ul>
  		<ul class="nav navbar-nav navbar-right">
  			<li class="dropdown">
  				<a href="#" class="dropdown-toggle"
  					data-toggle="dropdown" role="button" aria-haspopup="true"
  					aria-expanded="false">메뉴<span class="caret"></span></a>
  				<ul class="dropdown-menu">
  				<c:choose>
  				<c:when test="${sessionScope.vo==null}">
  					<li class="active"><a href="user?cmd=loginpage">로그인</a></li>
  					<li ><a href="user?cmd=joinpage">회원가입</a></li>
  					</c:when>
  					<c:otherwise>
  					<li class="active"><a href="user?cmd=logout">로그아웃</a></li>
  					</c:otherwise>
				</c:choose>
  				</ul>
  			</li>
  		</ul>
  	</div>
  </nav>