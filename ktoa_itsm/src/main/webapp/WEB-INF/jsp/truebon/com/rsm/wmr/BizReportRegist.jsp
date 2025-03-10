<!DOCTYPE html>
<%--
 /**
  * @Class Name : BizReportRegist.jsp
  * @Description : 업무보고서 등록 화면
  * @Modification Information
  * @
  * @ 수정일               수정자             수정내용
  * @ ----------   --------   ---------------------------
  * @ 2022.08.22   이태신            최초 생성
  *
  *  @author 이태신
  *  @since 2022.08.22
  *  @version 1.0
  *  @see
  *
  */
--%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<c:set var="ImgUrl" value="${pageContext.request.contextPath}/images/egovframework/com/sym/mnu/mpm/"/>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
<title><c:out value="${bizReportVO.getBizReportKindNm()}"/> 등록</title>
<link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/egovframework/com/button.css"/>" rel="stylesheet" type="text/css">
<!-- jQuery -->
<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<script src="<c:url value='/js/egovframework/com/cmm/jquery.js' />"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<!-- 유효성 검사 -->
<script src="<c:url value='/js/egovframework/com/cmm/utl/EgovCmmUtl.js' />"></script>
<!-- validator 클라이언트 -->
<script type="text/javascript" src="<c:url value="/validator.do" />"></script>
<validator:javascript formName="bizReportVO" staticJavascript="false" xhtml="true" cdata="false"/>
<validator:javascript formName="bizDetlReportVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script language="javascript1.2" type="text/javaScript">
$(document).ready(function () {
	
});

/* ********************************************************
 * 입력처리 함수
 ******************************************************** */
function insertBizReport() {
	let sBizSytmCd = "";	// 업무보고서 코드
	let sBizSytmNm = "";	// 업무시스템 명
	let sAcrsltText = "";	// 업무보고서 실적
	let sPlantext = "";		// 업무보고서 계획
	let bSubmit = false;	// 실행 여부
	
	// 업무보고서를 추가한 경우...(실적과 계획이 동시에 빈 값일 수 없음)
	$("[id^='createAreaTitle_']").each(function(index, item) {
		sBizSytmCd = $(item).find("#arrBizSytmCd").val();
		sBizSytmNm = $(item).attr("title");
		
		sAcrsltText = $("[id^='createAreaSub_']").eq(index).find("#arrAcrsltText").val().trim();
		sPlantext = $("[id^='createAreaSub_']").eq(index).find("#arrPlnText").val().trim();
		
		if(sAcrsltText == "" && sPlantext == "") {
			alert("'" + sBizSytmNm + "'" + " 업무보고서 실적과 계획이\n\n동시에 빈 값일 수 없습니다.\n\n업무보고서 작성하거나 삭제 바랍니다.");
			bSubmit = false;
			return false;
		} else {
			bSubmit = true;
		}
	});
	
	// 업무보고서를 추가하지 않은 경우...
	if($("[id^='createAreaTitle_']").length == 0) {
		alert("업무보고서 작성 바랍니다.");
	} else {
		// 등록
		if(bSubmit)	$("#bizReportForm").submit();
	}
	return false;
}

/* ********************************************************
 * 목록조회  함수
 ******************************************************** */
function selectList(){
	location.href = "<c:url value='/rsm/wmr/BizReportList.do' />?bizReportKindCd=<c:out value='${bizReportVO.getBizReportKindCd()}'/>";
}

/* ********************************************************
 * 행 생성  함수
 ******************************************************** */
function addRow(pBizSytmCd, pBizSytmNm) {
	let insertTr = "";
	let bAuto = false;
	
	let sBizSytmCd = "";
	let sBizSytmNm = "";
	
	if(pBizSytmCd == "" || typeof pBizSytmCd == "undefined") { // 수동 행 추가
		sBizSytmCd = $("#bizSytmCd").val();
		sBizSytmNm = $("#bizSytmCd option:checked").text();
	} else { // SR 불러오기 실행 시 자동 추가
		sBizSytmCd = pBizSytmCd;
		sBizSytmNm = pBizSytmNm;
		bAuto = true;
	}
	
	// 존재 여부 확인
	if($("#createAreaTitle_" + sBizSytmCd).length > 0){
		if(!bAuto) { // 수동 행 추가 시 알림
			alert("'" + sBizSytmNm + "'" + " 업무가 이미 존재 합니다.");
		}
		return false;
	}
	
	insertTr += "<tr id='createAreaTitle_" + sBizSytmCd + "' title='" + sBizSytmNm + "'>";
	insertTr += "	<th class='repoTitle sub' colspan='2'>" + sBizSytmNm + "<input type='hidden' id='arrBizSytmCd' name='arrBizSytmCd' value='" + sBizSytmCd + "' />";
	insertTr += "		<span style='position:absolute; right:8px;'><a href='#LINK' onclick='delRow(this);' style='color:red;'>삭제</a></span>";
	insertTr += "	</th>";
	insertTr += "</tr>";
	insertTr += "<tr id='createAreaSub_" + sBizSytmCd + "'>";
	insertTr += "	<td>";
	insertTr += "		<textarea id='arrAcrsltText' name='arrAcrsltText' class='repo_txt' placeholder='▶ 실적 제목 입력\r\n- 실적 내용 입력' style='height:200px'></textarea>";
	insertTr += "	</td>";
	insertTr += "	<td>";
	insertTr += "		<textarea id='arrPlnText' name='arrPlnText' class='repo_txt' placeholder='▶ 계획 제목 입력\r\n- 계획 내용 입력' style='height:200px'></textarea>";
	insertTr += "	</td>";
	insertTr += "</tr>";
	
	// 업무시스템이 선택되지 않은 경우
	if(sBizSytmCd==""){
		alert("업무시스템을 선택해 주세요.");
	} else {
		$("#message").hide();
		$("#createArea").append(insertTr);
	}
}

/* ********************************************************
 * 행 삭제  함수
 ******************************************************** */
function delRow(obj) {
	let sBizSytmCd = $(obj).parent().siblings("#arrBizSytmCd").val();
	$("#createAreaTitle_" + sBizSytmCd).remove();
	$("#createAreaSub_" + sBizSytmCd).remove();
	
	// 마지막 행이 삭제 되었다면 메시지 행 출력.
	if($("[id^='createAreaTitle_']").length == 0) {
		$("#message").show();
	}
}

/* ********************************************************
 * 서비스 요청 목록 팝업  함수
 ******************************************************** */
function addSrInfo() {
	let sBizSytmCd = $("#bizSytmCd").val();
	let sBizSytmNm = $("#bizSytmCd option:checked").text();
	let pagetitle = "";
    let page = "<c:url value='/opm/srm/SrMasterMngListPopup2.do?searchSytmCd='/>" + sBizSytmCd;
    
    // 타이틀명 변경
    if(sBizSytmCd == "") {
    	pagetitle = "SR 불러오기 (업무시스템 전체)";
    } else {
    	pagetitle = "SR 불러오기 (" + sBizSytmNm + ")";
    }
    
    let $dialog = $('<div style="overflow:hidden;padding: 0px 0px 0px 0px;"></div>')
    .html('<iframe style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
    .dialog({
    	autoOpen: false,
        modal: true,
        width: 700,
        height: 580,
        title: pagetitle
	});
	$dialog.dialog('open');
}

// 서비스 요청 등록
function insertSrMngt(pSrMngtNo){
	var arrSrMngtNo = pSrMngtNo.split(",");
	
	$.ajax({
		url :"<c:url value='/opm/srm/insertSrMngt.do'/>"
        ,type: "POST"
        ,data : {
        	"arrSrMngtNo":JSON.stringify(arrSrMngtNo),
        	"bizReportKindCd":$("#bizReportKindCd").val()
        }
        ,dataType: 'json'  	   
        ,success : function(data){
        	// 기존 정보
        	let strTextArea = "";
        	// 신규 실적 추가 정보
        	let insertTextInfo = "";
        	// 진행 상태 코드(대기(1), 진행(2), 완료(3))
        	let srProgStatCd = "";
        	
        	for(let i = 0; data.resultList.length > i; i++) {
        		// 행 추가(추가된 행이 없을 경우 추가 됨)
        		addRow(data.resultList[i].bizSytmCd, data.resultList[i].bizSytmNm);
        		
        		// 진행 상태 코드(대기(1), 진행(2), 완료(3)) 저장
        		srProgStatCd = data.resultList[i].srProgStatCd;
        		// 초기화
        		insertTextInfo = "";
        		strTextArea = $("#createAreaSub_"+data.resultList[i].bizSytmCd).find("#arrAcrsltText").val();
       			if(strTextArea != "") {
           			insertTextInfo = strTextArea + "\r\n\r\n";
           		}
        		// 업무시스템 제목 추가
        		insertTextInfo += "▶ " + data.resultList[i].reqTitle + " (" + data.resultList[i].srProgStat + ")" + "\r\n";
        		// 업무시스템 내용 추가
        		insertTextInfo += data.resultList[i].reqText;
        		// 실적 최종 추가
        		$("#createAreaSub_"+data.resultList[i].bizSytmCd).find("#arrAcrsltText").val(insertTextInfo);
        		
        		// 진행 상태 코드rk 대기(1), 진행(2) 이라면 계획에 값 추가
        		if(srProgStatCd == "1" || srProgStatCd == "2") {
        			// 초기화
            		insertTextInfo = "";
        			strTextArea = $("#createAreaSub_"+data.resultList[i].bizSytmCd).find("#arrPlnText").val();
           			if(strTextArea != "") {
               			insertTextInfo = strTextArea + "\r\n\r\n";
               		}
            		// 업무시스템 제목 추가
            		insertTextInfo += "▶ " + data.resultList[i].reqTitle + " (" + data.resultList[i].srProgStat + ")" + "\r\n";
            		// 업무시스템 내용 추가
            		insertTextInfo += data.resultList[i].reqText;
            		// 계획 최종 추가
            		$("#createAreaSub_"+data.resultList[i].bizSytmCd).find("#arrPlnText").val(insertTextInfo);
        		}
        	}
		}
	    ,error: function(){
	    	alert("<spring:message code="comCopBlog.articleBlogList.validate.occurError" />");//에러가 발생했습니다.
	    }
	});
}

<c:if test="${!empty resultMsg}">alert("${resultMsg}");</c:if>
</script>
</head>
<body>

<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript><!-- 자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다. -->

<form:form commandName="bizReportVO" id="bizReportForm" name="bizReportForm" method="post" action="${pageContext.request.contextPath}/rsm/wmr/inseartBizReportRegist.do">
<form:hidden path="bizReportKindCd" /><!-- 업무보고서종류코드(주간보고서(01), 월간보고서(02)) -->
<form:hidden path="bizReportNm" /><!-- 업무보고서명 -->
<form:hidden path="creatChrgrNo" /><!-- 생성담당자번호 -->

<div class="wTableFrm">
	<!-- 타이틀 -->
	<h2><c:out value="${bizReportVO.getBizReportKindNm()}"/> 등록</h2>

	<!-- 업무보고서 기본 정보 -->
	<table class="wTable">
		<colgroup>
			<col style="width:15%" />
			<col style="width:35%" />
			<col style="width:15%" />
			<col style="" />
		</colgroup>
		<tr>
			<th><c:out value="${bizReportVO.getBizReportKindNm()}"/> 명</th>
			<td class="left" colspan="3">
			    <c:out value="${bizReportVO.getBizReportNm()}"/>
			    <div><form:errors path="bizReportNm" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>업무 보고서 종류</th>
			<td class="left">
      			<c:out value="${bizReportVO.getBizReportKindNm()}"/>
			</td>
			<th>작성자 명</th>
			<td class="left">
      			<c:out value="${bizReportVO.getCreatChrgrNm()}"/>
			</td>
		</tr>
	</table>
	
	<!-- 하단 버튼 -->
	<div class="btn">
		<form:select path="bizSytmCd" title="담당자 구분" cssClass="select2">
			<form:option value="" label="--업무시스템--" />
			<form:options items="${bizSytmList}" itemValue="bizSytmCd" itemLabel="bizSytmNm" />
		</form:select>
		<span class="btn_s2"><a href="#LINK" onclick="addRow(); return false;">행 추가</a></span><!-- 행 추가 -->
		<span class="btn_s2"><a href="#LINK" onclick="addSrInfo(); return false;">SR 불러오기</a></span><!-- SR 불러오기 -->
		&nbsp;|&nbsp; 
		<input class="s_submit" type="submit" value='<spring:message code="button.create" />' onclick="insertBizReport(); return false;" /><!-- 등록 -->
		<span class="btn_s"><a href="#LINK" onclick="selectList(); return false;"><spring:message code="button.list"/></a></span><!-- 목록 -->
	</div>
	<div style="clear:both;"></div>
	
	<!-- 등록폼 상위 타이틀 -->
	<table class="wTable">
		<colgroup>
			<col style="width:50%" />
			<col style="width:50%" />
		</colgroup>
		<tr>
			<th class="repoTitle">실적</th>
			<th style="text-align:center;  padding:6px 8px; background:#DEDEDE;">계획</th>
		</tr>
	</table>
		
	<!-- 등록폼 -->
	<table class="wTable">
		<colgroup>
			<col style="width:50%" />
			<col style="width:50%" />
		</colgroup>
		<tbody id="createArea">
		<%-- 데이터를 없을때 화면에 메세지를 출력해준다 --%>
		<c:if test="${fn:length(BizDetlReportList) == 0}">
			<tr id="message">
				<td colspan="2">
	      			실적과 계획을 추가해 주세요.
				</td>
			</tr>
		</c:if>
		<c:if test="${BizDetlReportList != '' && BizDetlReportList ne null}">
		<c:forEach var="result" items="${BizDetlReportList}" varStatus="status">
		<c:if test="${fn:length(result.plnText) != 0}">
		<tr id="createAreaTitle_<c:out value="${result.bizSytmCd}"/>" title="<c:out value="${result.bizSytmNm}"/>">
			<th class="repoTitle sub" colspan="2"><c:out value="${result.bizSytmNm}"/><input type="hidden" id="arrBizSytmCd" name="arrBizSytmCd" value="<c:out value="${result.bizSytmCd}"/>" />
				<!-- 작성자만 실행 가능 -->
				<c:if test="${sessionScope.loginVO.chrgrNo eq bizReportVO.creatChrgrNo}">
				<span style="position:absolute; right:8px;"><a href="#LINK" onclick="delRow(this);" style="color:red;">삭제</a></span>
				</c:if>
			</th>
		</tr>
		<tr id="createAreaSub_<c:out value="${result.bizSytmCd}"/>">
			<td>
				<textarea id="arrAcrsltText" name="arrAcrsltText" class="repo_txt" placeholder="▶ 실적 제목 입력&#13;&#10;- 실적 내용 입력" style="height:200px"></textarea>
			</td>
			<td>
				<textarea id="arrPlnText" name="arrPlnText" class="repo_txt" placeholder="▶ 계획 제목 입력&#13;&#10;- 계획 내용 입력" style="height:200px"><c:out value="${result.plnText}"/></textarea>
			</td>
		</tr>
		</c:if>
		</c:forEach>
		</c:if>
		</tbody>
	</table>
</div>

</form:form>

</body>
</html>