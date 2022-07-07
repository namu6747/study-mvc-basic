<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp"/>
<section>
	<table border="1">
		<tr>
			<th colspan="6"><h1>회원목록</h1></th>
		</tr>
		<tr>
			<th>회원번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>나이</th>
			<th>성별</th>
			<th>탈퇴여부</th>
		</tr>
		<!-- memberList -->
		<c:forEach var="m" items="${memberList}">
			<tr>
				<td>${m.num}</td>
				<td>${m.id}</td>
				<td>${m.name}</td>
				<td>${m.age}</td>
				<td>${m.gender eq 'male' ? '남성' : '여성'}</td>
				<td>${m.joinYN}</td>
			</tr>
		</c:forEach>
		<c:if test="${!empty pm}">
			<tr>
				<th colspan="6">
					<c:if test="${pm.cri.page ne 1}">
						<a href="managementPage.mgc?page=1">[처음]</a>
					</c:if>
					<c:if test="${pm.prev}">
						<a href="managementPage.mgc?page=${pm.startPage-1}">[이전]</a>
					</c:if>
					<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
						<a href="managementPage.mgc?page=${i}">[${i}]</a>
					</c:forEach>
					<c:if test="${pm.next}">
						<a href="managementPage.mgc?page=${pm.endPage + 1}">[다음]</a>
					</c:if>
					<c:if test="${pm.cri.page ne pm.maxPage}">
						<a href="managementPage.mgc?page=${pm.maxPage}">[마지막]</a>
					</c:if>
				</th>
			</tr>
		</c:if>
	</table>	
</section>
<jsp:include page="../common/footer.jsp"/>







