<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- notice_success.jsp - 요청 처리 성공 -->
<jsp:include page="../../common/header.jsp" />
<section>
	<h1>요청 처리 성공-${message}</h1> <br/>
	<a href="notice.do">공지 메인</a>
</section>
<jsp:include page="../../common/footer.jsp" />