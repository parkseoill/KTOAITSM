package truebon.com.opm.fix.model;
/**
 * 점검관리 처리를 위한 VO 클래스를 정의한다
 * @author 김승현
 * @since 2023.08.07
 * @version 1.0
 * @see
 *  
  * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *  수정일               수정자            수정내용
 *  ----------   --------   ---------------------------
 *  2023.08.07   김승현           최초 생성
 *
 * </pre>
 */


public class FixedInspMngVO {
	
	/** 검색조건 */
	private String searchCondition = "";
	   
	/** 검색Keyword */
	private String searchKeyword = "";
	   
	/** 검색사용여부 */
	private String searchUseYn = "";
	   
	/** 현재페이지 */
	private int pageIndex = 1;
	   
	/** 페이지갯수 */
	private int pageUnit = 10;
   
    /** 페이지사이즈 */
    private int pageSize = 10;
   
    /** firstIndex */
    private int firstIndex = 1;
   
    /** lastIndex */
    private int lastIndex = 1;
   
    /** recordCountPerPage */
    private int recordCountPerPage = 10;
   
    /** 멀티 삭제용(콤마로 이루어진 값) */
    private String checkedValueForDel = "";
    
    /** 체크 개수 */
    private String checkedCount = "";
	   
    /** 검색1 (장비구분) */
    private String searchKeyword1 = "";
   
    /** 검색2 (점검여부) */
    private String searchKeyword2 = "";
   
    /** 검색3 (업무시스템) */
    private String searchKeyword3 = "";
    
    /** 현황 조회년 */
    private String selectedDate = "";
    
    /** 자산ID */
	private  String assetId = "";
	
	/** 장비구분 */
	private  String assetDstnct = "";
	
	/** 점검협력사 */
	private  String fixedInspCmpny = "";
	
	/** 점검주기 */
	private  String maintceInspCycle = "";
	
	/** 점검여부 */
	private  String fixedInspYn = "";
	
	/** 점검일자 */
	private  String fixedInspDate = "";
	
	/** 제품명 */
	private  String prodNm = "";
	
	/** 제품용도 */
	private  String prodPurp = "";
	
	/** 업무시스템 */
	private  String bizSytmNm = "";
	
	/** 정기점검계획월 */
	private  String fixedInspPlnMon = "";
	
	/** 비고내용*/
	private  String rmarkText = "";
	
    /** 검색 회사 */
    private String searchCmpny = "";
    
    /** 선택 월 */
    private String selectMonth = "";
    
    /** 선택 분기 */
    private String selectQuarter = "";
    
    /** 선택 반기 */
    private String selectHalf = "";
    
    /** 생성 월 */
    private String insertMonth = "";
	
	/** 점검협력사 */
	private  String cmpnyNm = "";
	
	/** 점검협력번호 */
	private  String cmpnyNo = "";
	
	/** 계획 */
	private  String plnCnt= "";
	
	/** 실적 */
	private  String resCnt = "";
	
    /** 등록자ID */
    private String regstrId;
   
    /** 등록일 */
    private String rdtt;
   
    /** 수정자ID */
    private String amndrId;
    
    /** 수정자명 */
    private String amndrNm;
   
    /** 수정일 */
    private String mdtt;

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSearchUseYn() {
		return searchUseYn;
	}

	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public String getCheckedValueForDel() {
		return checkedValueForDel;
	}

	public void setCheckedValueForDel(String checkedValueForDel) {
		this.checkedValueForDel = checkedValueForDel;
	}

	public String getCheckedCount() {
		return checkedCount;
	}

	public void setCheckedCount(String checkedCount) {
		this.checkedCount = checkedCount;
	}

	public String getSearchKeyword1() {
		return searchKeyword1;
	}

	public void setSearchKeyword1(String searchKeyword1) {
		this.searchKeyword1 = searchKeyword1;
	}

	public String getSearchKeyword2() {
		return searchKeyword2;
	}

	public void setSearchKeyword2(String searchKeyword2) {
		this.searchKeyword2 = searchKeyword2;
	}

	public String getSearchKeyword3() {
		return searchKeyword3;
	}

	public void setSearchKeyword3(String searchKeyword3) {
		this.searchKeyword3 = searchKeyword3;
	}

	public String getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetDstnct() {
		return assetDstnct;
	}

	public void setAssetDstnct(String assetDstnct) {
		this.assetDstnct = assetDstnct;
	}

	public String getFixedInspCmpny() {
		return fixedInspCmpny;
	}

	public void setFixedInspCmpny(String fixedInspCmpny) {
		this.fixedInspCmpny = fixedInspCmpny;
	}

	public String getMaintceInspCycle() {
		return maintceInspCycle;
	}

	public void setMaintceInspCycle(String maintceInspCycle) {
		this.maintceInspCycle = maintceInspCycle;
	}

	public String getFixedInspYn() {
		return fixedInspYn;
	}

	public void setFixedInspYn(String fixedInspYn) {
		this.fixedInspYn = fixedInspYn;
	}

	public String getFixedInspDate() {
		return fixedInspDate;
	}

	public void setFixedInspDate(String fixedInspDate) {
		this.fixedInspDate = fixedInspDate;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getProdPurp() {
		return prodPurp;
	}

	public void setProdPurp(String prodPurp) {
		this.prodPurp = prodPurp;
	}

	public String getBizSytmNm() {
		return bizSytmNm;
	}

	public void setBizSytmNm(String bizSytmNm) {
		this.bizSytmNm = bizSytmNm;
	}

	public String getFixedInspPlnMon() {
		return fixedInspPlnMon;
	}

	public void setFixedInspPlnMon(String fixedInspPlnMon) {
		this.fixedInspPlnMon = fixedInspPlnMon;
	}

	public String getRmarkText() {
		return rmarkText;
	}

	public void setRmarkText(String rmarkText) {
		this.rmarkText = rmarkText;
	}

	public String getSearchCmpny() {
		return searchCmpny;
	}

	public void setSearchCmpny(String searchCmpny) {
		this.searchCmpny = searchCmpny;
	}

	public String getSelectMonth() {
		return selectMonth;
	}

	public void setSelectMonth(String selectMonth) {
		this.selectMonth = selectMonth;
	}

	public String getSelectQuarter() {
		return selectQuarter;
	}

	public void setSelectQuarter(String selectQuarter) {
		this.selectQuarter = selectQuarter;
	}

	public String getSelectHalf() {
		return selectHalf;
	}

	public void setSelectHalf(String selectHalf) {
		this.selectHalf = selectHalf;
	}

	public String getInsertMonth() {
		return insertMonth;
	}

	public void setInsertMonth(String insertMonth) {
		this.insertMonth = insertMonth;
	}

	public String getCmpnyNm() {
		return cmpnyNm;
	}

	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}

	public String getCmpnyNo() {
		return cmpnyNo;
	}

	public void setCmpnyNo(String cmpnyNo) {
		this.cmpnyNo = cmpnyNo;
	}

	public String getPlnCnt() {
		return plnCnt;
	}

	public void setPlnCnt(String plnCnt) {
		this.plnCnt = plnCnt;
	}

	public String getResCnt() {
		return resCnt;
	}

	public void setResCnt(String resCnt) {
		this.resCnt = resCnt;
	}

	public String getRegstrId() {
		return regstrId;
	}

	public void setRegstrId(String regstrId) {
		this.regstrId = regstrId;
	}

	public String getRdtt() {
		return rdtt;
	}

	public void setRdtt(String rdtt) {
		this.rdtt = rdtt;
	}

	public String getAmndrId() {
		return amndrId;
	}

	public void setAmndrId(String amndrId) {
		this.amndrId = amndrId;
	}

	public String getAmndrNm() {
		return amndrNm;
	}

	public void setAmndrNm(String amndrNm) {
		this.amndrNm = amndrNm;
	}

	public String getMdtt() {
		return mdtt;
	}

	public void setMdtt(String mdtt) {
		this.mdtt = mdtt;
	}

	@Override
	public String toString() {
		return "FixedInspMngVO [searchCondition=" + searchCondition + ", searchKeyword=" + searchKeyword
				+ ", searchUseYn=" + searchUseYn + ", pageIndex=" + pageIndex + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", firstIndex=" + firstIndex + ", lastIndex=" + lastIndex + ", recordCountPerPage="
				+ recordCountPerPage + ", checkedValueForDel=" + checkedValueForDel + ", checkedCount=" + checkedCount
				+ ", searchKeyword1=" + searchKeyword1 + ", searchKeyword2=" + searchKeyword2 + ", searchKeyword3="
				+ searchKeyword3 + ", selectedDate=" + selectedDate + ", assetId=" + assetId + ", assetDstnct="
				+ assetDstnct + ", fixedInspCmpny=" + fixedInspCmpny + ", maintceInspCycle=" + maintceInspCycle
				+ ", fixedInspYn=" + fixedInspYn + ", fixedInspDate=" + fixedInspDate + ", prodNm=" + prodNm
				+ ", prodPurp=" + prodPurp + ", bizSytmNm=" + bizSytmNm + ", fixedInspPlnMon=" + fixedInspPlnMon
				+ ", rmarkText=" + rmarkText + ", searchCmpny=" + searchCmpny + ", selectMonth=" + selectMonth
				+ ", selectQuarter=" + selectQuarter + ", selectHalf=" + selectHalf + ", insertMonth=" + insertMonth
				+ ", cmpnyNm=" + cmpnyNm + ", cmpnyNo=" + cmpnyNo + ", plnCnt=" + plnCnt + ", resCnt=" + resCnt
				+ ", regstrId=" + regstrId + ", rdtt=" + rdtt + ", amndrId=" + amndrId + ", amndrNm=" + amndrNm
				+ ", mdtt=" + mdtt + "]";
	}
    
	
}
