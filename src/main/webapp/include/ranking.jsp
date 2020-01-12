<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class='viewRanking'>
  <hr>
  <h2>검색 순위 (횟수)</h2>
</div>

<script type="text/javascript">
  $(document).ready(function(){
    $.ajax({
      type : "GET", //전송방식을 지정한다 (POST,GET)
      url : "/search/viewRanking",//호출 URL을 설정한다. GET방식일경우 뒤에 파라티터를 붙여서 사용해도된다.
      dataType : "json",//호출한 페이지의 형식이다. xml,json,html,text등의 여러 방식을 사용할 수 있다.
      error : function(){
        alert("접속오류");
      },
      success : function(data){if(data == ''){$(".viewRanking").append("목록이 비어있습니다. 검색 후 새로고침 부탁드립니다.<br>");
      }
        $.each(data , function(idx, val) {
          $(".viewRanking").append((idx+1) + ". " + val.search_word + " (" + val.count + ") <br>");
        });
      }

    });
  });
</script>