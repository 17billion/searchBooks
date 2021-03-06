<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>책 검색 - books search</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="main-top-layout">
		<div class="container">
			<%@ include file="/include/nav.jsp"%>
			<h1>책 검색</h1>
			<div class="row">
				<div class="col-12">
					<form id="search_form" name="search_form" onsubmit="return submitSearch();">
						<input type="hidden" name="page" value="1">
						<div class="form-row">
							<div class="col">
								<div class="form-group">
									<label for="select-target">검색 필드</label>
									<select class="form-control" name="target">
										<c:forEach var="t" items="${EnumTarget }">
											<option value="${t.code }">${t }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label for="input-searchword">검색어</label>
									<input id="input-searchword" class="form-control" name="searchWord" placeholder="검색어를 입력하세요.">
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label>&nbsp;</label>
									<div><button class="btn btn-secondary" type="submit">검색</button></div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
			<%@ include file="/include/ranking.jsp"%>
		</div>

	</div>
	<div class="container">
		<hr>
		<h2>책 리스트</h2>
		<div class="row">
			<div class="col-12">
				<div id="books">
					<ul class="list-group">
					</ul>
					<div id="resultMessage" class="alert" role="alert" style="display: none;">검색된 책이 없습니다.</div>
				</div>
			</div>
		</div>
		<hr>
	</div>
	<script type="text/javascript">
		function submitSearch(page) {
			var pg = page ? page : 1;
			var frm = document.search_form;
			frm.page.value = pg;
			if (frm.searchWord.value == "") {
				alert("검색어를 입력하세요. ");
			} else {
				$
						.ajax({
							url : "/search/searchBooks",
							data : $(frm).serialize(),
							beforeSend: function(){
								$(frm).find("button[type=submit]").prop("disabled", true);
								$(frm).find("button[type=submit]").text("Searching for ...");
								console.log($(frm).find("button[type=submit]"));
							},
							success : function(res) {
								if (res.meta.total_count < 1) {
									$("#books > ul").html("");
									$("#books > #resultMessage").show();
								} else {
									$("#books > #resultMessage").hide();
									var html = "";
									$(res.documents)
											.each(
													function(idx) {
														var authors = "", trans = "", thumbnail = "", isbn = "";
														$(this.authors)
																.each(
																		function() {
																			authors += (this + " ")
																		});
														$(this.translators)
																.each(
																		function() {
																			trans += (this + " ")
																		});
														if (this.thumbnail) {
															thumbnail = "<img src='"+this.thumbnail+"' width='100'>";
														}
														var arr_isbn = this.isbn.split(" ");
														if(arr_isbn.length>1){
															isbn = arr_isbn[1];
														}else{
															isbn = arr_isbn[0];
														}

														html += "<li class='list-group-item'>";
														html += "<dl><dt><a href='./detail?isbn="
																+ isbn
																+ "'>"
																+ this.title
																+ " | ("
																+ this.publisher
																+ ")</a>"
																+"</dt>";
														html += "<dd><div class='left'>"
																+ thumbnail
																+ "</div><div class='right'>저자: "
																+ authors
																+ "</div></dd></dl></li>";
													});
									if (!res.meta.is_end) {
										html += "<li><button class='btn btn-primary btn-lg btn-block' onclick='submitSearch("
												+ (pg + 1)
												+ "); $(this).parent().remove();'>더보기 </button></li>";
									}
									if (pg > 1) {
										$("#books > ul").append(html);
									} else {
										$("#books > ul").html(html);
									}
								}
								console.log(res);
							},
							error : function(res) {
								console.log(res);
								alert(res);
							},
							complete : function() {
								$(frm).find("button[type=submit]").prop("disabled", false);
								$(frm).find("button[type=submit]").text("검색");
							}
						});
			}

			return false;
		}


		$(document).ready(
				function() {
					$("#books > .list-group").on("mouseenter",
							".list-group-item", function() {
								$(this).find("dd").show();
							});
					$("#books > .list-group").on("mouseleave",
							".list-group-item", function() {
								$(this).find("dd").hide();
							});
					
				});
	</script>
</body>
</html>