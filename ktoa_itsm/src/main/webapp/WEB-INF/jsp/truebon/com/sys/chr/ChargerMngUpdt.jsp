<!DOCTYPE html>
<%--
 /**
  * @Class Name : ChargerMngUpdt.jsp
  * @Description : 담당자 수정 화면
  * @Modification Information
  * @
  * @ 수정일               수정자             수정내용
  * @ ----------   --------   ---------------------------
  * @ 2022.05.04   이태신            최초 생성
  *
  *  @author 이태신
  *  @since 2022.05.04
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
<title>담당자관리 수정</title>
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
<validator:javascript formName="chargerVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script language="javascript1.2" type="text/javaScript">
<!--
$(document).ready(function () {
	<c:if test="${sessionScope.loginVO.authorCode ne 'ROLE_ADMIN'}">
		var loginUserNm = "${sessionScope.loginVO.name}";
	    if($('#chrgrNm').val() != loginUserNm){
	    	disabled();
	    }
	</c:if>	

	// 소속회사, 부서목록 이동 화면 호출 함수
    $('#popupPositDeptList').click(function (e) {
    	e.preventDefault();
        var pagetitle = $(this).attr("title");
        var page = "<c:url value='/uss/umt/dpt/selectDeptManageListPopup.do?deptGubun=posit'/>";
        var $dialog = $('<div style="overflow:hidden;padding: 0px 0px 0px 0px;"></div>')
        .html('<iframe style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
        .dialog({
        	autoOpen: false,
            modal: true,
            width: 410,
            height: 550,
            title: pagetitle
    	});
    	$dialog.dialog('open');
	});
	
 	// 위탁회사, 부서목록 이동 화면 호출 함수
    $('#popupCosnDeptList').click(function (e) {
    	e.preventDefault();
        var pagetitle = $(this).attr("title");
        var page = "<c:url value='/uss/umt/dpt/selectDeptManageListPopup.do?deptGubun=cosn'/>";
        var $dialog = $('<div style="overflow:hidden;padding: 0px 0px 0px 0px;"></div>')
        .html('<iframe style="border: 0px; " src="' + page + '" width="100%" height="100%"></iframe>')
        .dialog({
        	autoOpen: false,
            modal: true,
            width: 410,
            height: 550,
            title: pagetitle
    	});
    	$dialog.dialog('open');
	});
});

/* ********************************************************
 * 수정처리 함수
 ******************************************************** */
function updateChargerMng() {
	var objForm = document.getElementById("chargerMngForm");
	
	if(confirm("<spring:message code="common.save.msg" />")){
		if(!validateChargerVO(objForm)){
			return;
		}else if(!validEmail($("#chrgrEaddr"))){
			return;
		}else{
			objForm.submit();
		}
	}
}

/* ********************************************************
 * 삭제처리함수
 ******************************************************** */
function deleteChargerMng() {
	var objForm = document.getElementById("chargerMngForm");
	
	if(confirm("<spring:message code="common.delete.msg"/>")){
		objForm.action="<c:url value='/sys/chr/deleteChargerMng.do' />";
		objForm.submit();
	}
}

/* ********************************************************
 * 목록조회  함수
 ******************************************************** */
function selectList(){
	var objForm = document.getElementById("chargerMngForm");
	objForm.action = "<c:url value='/sys/chr/ChargerMngList.do' />";
	objForm.submit();
}

/* ********************************************************
 * 입력박스 비활성화 함수
 ******************************************************** */
function disabled() {
	$("#chrgrNm").attr("disabled", true);
	$("#clspos").attr("disabled", true);
	$("#chrgrWirelnTelNo").attr("disabled", true);
	$("#chrgrWirelsTelNo").attr("disabled", true);
	$("#emgcyContReltnCd").attr("disabled", true);
	$("#emgcyContTelNo").attr("disabled", true);
	$("#chrgrAddr").attr("disabled", true);
	$("#chrgrEaddr").attr("disabled", true);
	$("#chrgrDstnctCd").attr("disabled", true);
	$("#chrgrRoleCd").attr("disabled", true);
}
<c:if test="${!empty resultMsg}">alert("${resultMsg}");</c:if>
//-->
</script>
</head>
<body>

<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript><!-- 자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다. -->

<form:form commandName="chargerVO" id="chargerMngForm" name="chargerMngForm" method="post" action="${pageContext.request.contextPath}/sys/chr/updateChargerMngUpdt.do">
	<!-- 검색조건 유지 -->
    <input type="hidden" name="searchCondition" value="<c:out value='${searchVO.searchCondition}'/>"/>
    <input type="hidden" name="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>"/>
    <input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}' default='1' />"/>
    <input type="hidden" name="searchCode" value="<c:out value='${searchVO.searchCode}'/>"/>    
    <!-- 담당자 번호 -->
    <input type="hidden" name="chrgrNo" value="<c:out value='${searchVO.chrgrNo}'/>"/>

<div class="wTableFrm">
	<!-- 타이틀 -->
	<h2>담당자 수정</h2>

	<!-- 등록폼 -->
	<table class="wTable">
		<colgroup>
			<col style="width:15%" />
			<col style="width:35%" />
			<col style="width:15%" />
			<col style="" />
		</colgroup>
		<tr>
			<th>담당자명 <span class="pilsu">*</span></th>
			<td class="left">
			    <form:input path="chrgrNm" id="chrgrNm" maxlength="10" title="담당자명" cssStyle="width:100px" readonly="true" class="readOnlyClass" />
      			<div><form:errors path="chrgrNm" cssClass="error" /></div>
			</td>
			<th>직급</th>
			<td class="left">
			    <form:input path="clspos" id="clspos" maxlength="10" title="직급" cssStyle="width:100px" />
      			<div><form:errors path="clspos" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>유선전화번호</th>
			<td class="left">
			    <form:input path="chrgrWirelnTelNo" id="chrgrWirelnTelNo" maxlength="15" title="유선전화번호" cssStyle="width:100px" />
      			<div><form:errors path="chrgrWirelnTelNo" cssClass="error" /></div>
			</td>
			<th>무선전화번호</th>
			<td class="left">
			    <form:input path="chrgrWirelsTelNo" id="chrgrWirelsTelNo" maxlength="15" title="무선전화번호" cssStyle="width:100px" />
				<div><form:errors path="chrgrWirelsTelNo" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>비상연락관계</th>
			<td class="left">
			    <form:select path="emgcyContReltnCd" id="emgcyContReltnCd" title="비상연락관계" cssClass="txt">
					<form:option value="" label="--선택하세요--" />
					<form:options items="${emgcyContReltnCdList}" itemValue="comnCd" itemLabel="comnCdNm" />
				</form:select>
      			<div><form:errors path="emgcyContReltnCd" cssClass="error" /></div>
			</td>
			<th>비상연락전화</br>번호</th>
			<td class="left">
			    <form:input path="emgcyContTelNo" id="emgcyContTelNo" maxlength="15" title="비상연락전화번호" cssStyle="width:100px" />
				<div><form:errors path="emgcyContTelNo" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>담당자 주소</th>
			<td class="left">
			    <form:input path="chrgrAddr" id="chrgrAddr" maxlength="250" title="파일명" cssStyle="width:100%" />
			    <div><form:errors path="chrgrAddr" cssClass="error" /></div>
			</td>
			<th>담당자 이메일</br>주소</th>
			<td class="left">
			    <form:input path="chrgrEaddr" id="chrgrEaddr" maxlength="250" title="담당자 이메일 주소" cssStyle="width:150px" />
			    <div><form:errors path="chrgrEaddr" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>담당자 구분 <span class="pilsu">*</span></th>
			<td class="left">
				<form:select path="chrgrDstnctCd" id="chrgrDstnctCd" title="담당자 구분" cssClass="txt">
					<form:option value="" label="--선택하세요--" />
					<form:options items="${chrgrDstnctCdList}" itemValue="comnCd" itemLabel="comnCdNm" />
				</form:select>
			    <div><form:errors path="chrgrDstnctCd" cssClass="error" /></div>
			</td>
			<th>담당자 역할</th>
			<td class="left">
			    <form:select path="chrgrRoleCd" id="chrgrRoleCd" title="담당자 구분" cssClass="txt">
					<form:option value="" label="--선택하세요--" />
					<form:options items="${chrgrRoleCdList}" itemValue="comnCd" itemLabel="comnCdNm" />
				</form:select>
			    <div><form:errors path="chrgrRoleCd" cssClass="error" /></div>
			</td>
		</tr>
		<tr>
			<th>소속회사명 <span class="pilsu">*</span></th>
			<td class="left">
			    <form:input path="positCmpnyNm" title="소속회사명" readonly="true" class="readOnlyClass" cssStyle="width:150px"/>
	      		<div><form:errors path="positCmpnyNm" cssClass="error" /></div>
	      		<input type="hidden" name="positCmpnyNo" value="<c:out value="${chargerVO.positCmpnyNo}"/>">
			</td>
			<th>소속부서명 <span class="pilsu">*</span></th>
			<td class="left">
			    <form:input path="positDeptNm" title="소속부서명" readonly="true" class="readOnlyClass" cssStyle="width:150px"/>
	      			<c:if test="${sessionScope.loginVO.authorCode eq 'ROLE_ADMIN' || sessionScope.loginVO.name == chargerVO.chrgrNm}">
	      				<a id="popupPositDeptList" href="#LINK" target="_blank" title="부서 선택">
						<img src="<c:url value='/images/egovframework/com/cmm/icon/search2.gif' />" alt='' />(부서 선택)</a>
					</c:if>	
				<div><form:errors path="positDeptNm" cssClass="error" /></div>
				<input type="hidden" name="positDeptNo" value="<c:out value="${chargerVO.positDeptNo}"/>">
			</td>
		</tr>
		<tr>
			<th>위탁회사명</th>
			<td class="left">
			    <form:input path="cosnCmpnyNm" title="위탁회사명" readonly="true" class="readOnlyClass" cssStyle="width:150px"/>
	      		<div><form:errors path="cosnCmpnyNm" cssClass="error" /></div>
	      		<input type="hidden" name="cosnCmpnyNo" value="<c:out value="${chargerVO.cosnCmpnyNo}"/>">
			</td>
			<th>위탁부서명</th>
			<td class="left">
			    <form:input path="cosnDeptNm" title="위탁부서명" readonly="true" class="readOnlyClass" cssStyle="width:150px"/>
	      			<c:if test="${sessionScope.loginVO.authorCode eq 'ROLE_ADMIN' || sessionScope.loginVO.name == chargerVO.chrgrNm}">
	      				<a id="popupCosnDeptList" href="#LINK" target="_blank" title="부서 선택">
						<img src="<c:url value='/images/egovframework/com/cmm/icon/search2.gif' />" alt='' />(부서 선택)</a>
					</c:if>	
				<div><form:errors path="cosnDeptNm" cssClass="error" /></div>
	      		<input type="hidden" name="cosnDeptNo" value="<c:out value="${chargerVO.cosnDeptNo}"/>">
			</td>
		</tr>
	</table>

	<!-- 하단 버튼 -->
	<div class="btn">
		<!-- 관리자만 출력 -->
		<c:if test="${sessionScope.loginVO.authorCode eq 'ROLE_ADMIN' || sessionScope.loginVO.name == chargerVO.chrgrNm}">
			<input class="s_submit" type="submit" value='<spring:message code="button.update" />' onclick="updateChargerMng(); return false;" /><!-- 수정 -->
		</c:if>
		<c:if test="${sessionScope.loginVO.authorCode eq 'ROLE_ADMIN'}">	
			<span class="btn_s"><a href="#LINK" onclick="deleteChargerMng(); return false;"><spring:message code="button.delete" /></a></span><!-- 삭제 -->
		</c:if>
		<span class="btn_s"><a href="#LINK'/>" onclick="selectList(); return false;"><spring:message code="button.list"/></a></span><!-- 목록 -->
	</div>
	<div style="clear:both;"></div>
</div>

</form:form>

</body>
</html>