<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- notice_update.jsp 공지 수정 -->
<jsp:include page="../../common/header.jsp" />
<section>
	<form action="noticeUpdateSubmit.do" method="post">
		<input type="hidden" name="notice_num" value="${notice.notice_num}" />
		<input type="hidden" name="notice_author" value="${member.name}" />
		<table>
			<tr>
				<th colspan=2>
					<h2>공지글 수정</h2>
				</th>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${member.name}</td>
			</tr>
			<tr>
				<td>카테고리</td>
				<td><select name="notice_category">
						<option value="${notice.notice_category}" selected>${notice.notice_category}</option>
						<option value="공지">공지</option>
						<option value="알림">알림</option>
						<option value="이벤트">이벤트</option>
						<option value="당첨">당첨</option>
				</select></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="notice_title"
					value="${notice.notice_title}" required /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="notice_content" rows="10" cols="30">${notice.notice_content}</textarea>
				</td>
			</tr>
			<tr>
				<td colspan=2><input type="submit" value="수정완료" /> <input
					type="reset" value="다시쓰기" /></td>
			</tr>
		</table>
	</form>
</section>
<jsp:include page="../../common/footer.jsp" />