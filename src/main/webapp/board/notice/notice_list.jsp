<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- board/notice/notice_list.jsp 공지사항 게시물 목록-->
<jsp:include page="../../common/header.jsp" />
<section>
	<table>
		<tr>
			<th colspan=4><h1>공지사항</h1></th>
		</tr>
		<c:if test="${member.id eq 'admin'}">
			<tr>
				<th colspan=4><a href="noticeWrite.do">공지글 작성하기</a></th>
			</tr>
		</c:if>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
		<!-- 게시글 목록 noticeList -->
		<c:choose>
			<c:when test="${!empty noticeList}">
				<c:forEach var="n" items="${noticeList}">
					<tr>
						<td>${n.notice_num}</td>
						<td><a href="noticeDetail.do?notice_num=${n.notice_num}">
								[${n.notice_category}] - ${n.notice_title} </a></td>
						<td>${n.notice_author}</td>
						<td>${n.notice_date}</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan=4>등록된 게시물이 없습니다.</td>
				</tr>
			</c:otherwise>
		</c:choose>
		<!-- 페이징 처리 pm -->
		<c:if test="${!empty pm}">
			<tr>
				<td colspan=4><c:if test="${pm.cri.page > 1}">
						<a href="notice.do?page=1">[처음]</a>
						<c:if test="${pm.prev}">
							<a href="notice.do?page=${pm.startPage-1}">[이전]</a>
						</c:if>
					</c:if> <c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
						<c:choose>
							<c:when test="${pm.cri.page eq i}">
								<span style="color: red;">[ ${i} ]</span>
							</c:when>
							<c:otherwise>
								<a href="notice.do?page=${i}">[ ${i} ]</a>
							</c:otherwise>
						</c:choose>
					</c:forEach> <c:if test="${pm.cri.page < pm.maxPage}">
						<c:if test="${pm.next}">
							<a href="notice.do?page=${pm.endPage+1}">[다음]</a>
						</c:if>
						<a href="notice.do?page=${pm.maxPage}">[마지막]</a>
					</c:if></td>
			</tr>
		</c:if>
	</table>
</section>
<jsp:include page="../../common/footer.jsp" />
