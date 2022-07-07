<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- board/notice/notice_detail.jsp 상세보기 -->
<jsp:include page="../../common/header.jsp" />
<section>
	<table>
		<tr>
			<td colspan="2">
				<h2>공지</h2>
			</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${notice.notice_author}</td>
		</tr>
		<tr>
			<td>카테고리</td>
			<td>${notice.notice_category}</td>
		</tr>
		<tr>
			<td>제목</td>
			<td>${notice.notice_title}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea readonly cols="20" rows="10">${notice.notice_content}</textarea>
			</td>
		</tr>
		<tr>
			<td>작성시간</td>
			<td>${notice.notice_date}</td>
		</tr>
		<c:if test="${member.id eq 'admin'}">
			<tr>
				<td colspan="2"><input type="button"
					onclick="location.href='noticeUpdate.do?notice_num=${notice.notice_num}';"
					value="수정" /> | <input type="button"
					onclick="location.href='noticeDelete.do?notice_num=${notice.notice_num}';"
					value="삭제" /></td>
			</tr>
		</c:if>
	</table>
</section>
<jsp:include page="../../common/footer.jsp" />








