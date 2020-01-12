<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>검색 히스토리 - books search</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="main-top-layout">
		<div class="container">
			<%@ include file="/include/nav.jsp"%>
			<%@ include file="/include/ranking.jsp"%>
		</div>

		</div>
	</div>
	<div class="container mt-3">
		<div class="row">
			<div class="col list-bookmark">
				<h3>검색 히스토리 (최신 순)</h3>
				<form action="./searchHistory" id="searchForm" name="searchForm" method="get">
					<input type="hidden" name="page" value="${searchHistoryPage.number}">
					<input type="hidden" name="size" value="${searchHistoryPage.size }">
					<div class="form-row">
					</div>
				</form>
				<ul class="list-group">
					<c:if test="${empty searchHistoryPage.content }">
						<li>목록이 비어 있습니다.</li>
					</c:if>
					<c:forEach var="b" items="${searchHistoryPage.content }">
						<li class="list-group-item"><dl>
								<dt>
									검색어: ${b.search_word }
								</dt>
								<dd>
									<span class="blockquote-footer text-right"><fmt:formatDate value="${b.regdate }" pattern="yyyy. MM. dd HH:mm:ss"/></span>
								</dd>
							</dl></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="paging-layout center-block"></div>
	</div>


	<script type="text/javascript" src="/js/jquery.bootpag.min.js"></script>
	<script type="text/javascript">
	
		$(document).ready(function() {
			<c:if test="${not empty searchHistoryPage.content }">
				$('.paging-layout').bootpag({
				    total: ${searchHistoryPage.totalPages},
				    page: ${searchHistoryPage.number+1},
				    maxVisible: 10,
				    leaps: true,
				    firstLastUse: true,
				    first: '←',
				    last: '→',
				    wrapClass: 'pagination',
				    activeClass: 'active',
				    disabledClass: 'disabled',
				    nextClass: 'next',
				    prevClass: 'prev',
				    lastClass: 'last',
				    firstClass: 'first'
				}).on("page", function(event, num){
					console.log(num);
					var frm = document.searchForm
					frm.page.value = num-1;
					frm.submit();
				});
			</c:if>
		});
	</script>
</body>
</html>