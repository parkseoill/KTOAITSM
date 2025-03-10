package truebon.com.opm.srm.model;

import java.io.Serializable;

/**
 * 서비스요청 처리를 위한 VO 클래스르를 정의한다
 * @author 김도아
 * @since 2022.07.28
 * @version 1.0
 * @see
 *  
  * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *  수정일               수정자            수정내용
 *  ----------   --------   ---------------------------
 *  2022.07.28       김도아          	 최초 생성
 *
 * </pre>
 */
@SuppressWarnings("serial")
public class SrMaster implements Serializable {

	/** 처리일자 */
    private String dlngDate = "";

    /** 처리예정일자 */
    private String dlngPrargDate = "";

    /** 처리담당자번호 */
    private String dlngChrgrNo = "";
    
    /** 처리내용 */
    private String dlngText = "";
    
    /** 주간보고대상여부 */
    private String weekyReportTargtYn = "";
    
    /** 장애관리번호 */
    private String failrMngtNo = "";

    /** 월간보고대상여부 */
    private String monthyReportTargtYn = "";
    
    /** 요청제목 */
    private String reqTitle = "";
    
    /** 요청일자 */
    private String reqDate = "";
    
    /** 요청담당자번호 */
    private String reqChrgrNo = "";
    
    /** 요청내용 */
    private String reqText = "";
    
    /** 업무시스템코드 */
    private String bizSytmCd = "";
    
    /** 서비스요청진행상태코드 */
    private String srProgStatCd = "";
    
    /** 서비스요청유형분류코드 */
    private String srTypeClasCd = "";
    
    /** 서비스요청관리번호 */
    private String srMngtNo = "";
    
    /** 산출물작성여부 */
    private String prodtWritYn = "";
    
    /** 등록자ID */
    private String regstrId;
    
    /** 등록일 */
    private String rdtt;
    
    /** 수정자ID */
    private String amndrId;
    
    /** 수정일 */
    private String mdtt;

	public String getDlngDate() {
		return dlngDate;
	}

	public void setDlngDate(String dlngDate) {
		this.dlngDate = dlngDate;
	}

	public String getDlngPrargDate() {
		return dlngPrargDate;
	}

	public void setDlngPrargDate(String dlngPrargDate) {
		this.dlngPrargDate = dlngPrargDate;
	}

	public String getDlngChrgrNo() {
		return dlngChrgrNo;
	}

	public void setDlngChrgrNo(String dlngChrgrNo) {
		this.dlngChrgrNo = dlngChrgrNo;
	}

	public String getDlngText() {
		return dlngText;
	}

	public void setDlngText(String dlngText) {
		this.dlngText = dlngText;
	}

	public String getWeekyReportTargtYn() {
		return weekyReportTargtYn;
	}

	public void setWeekyReportTargtYn(String weekyReportTargtYn) {
		this.weekyReportTargtYn = weekyReportTargtYn;
	}

	public String getFailrMngtNo() {
		return failrMngtNo;
	}

	public void setFailrMngtNo(String failrMngtNo) {
		this.failrMngtNo = failrMngtNo;
	}

	public String getMonthyReportTargtYn() {
		return monthyReportTargtYn;
	}

	public void setMonthyReportTargtYn(String monthyReportTargtYn) {
		this.monthyReportTargtYn = monthyReportTargtYn;
	}

	public String getReqTitle() {
		return reqTitle;
	}

	public void setReqTitle(String reqTitle) {
		this.reqTitle = reqTitle;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getReqChrgrNo() {
		return reqChrgrNo;
	}

	public void setReqChrgrNo(String reqChrgrNo) {
		this.reqChrgrNo = reqChrgrNo;
	}

	public String getReqText() {
		return reqText;
	}

	public void setReqText(String reqText) {
		this.reqText = reqText;
	}

	public String getBizSytmCd() {
		return bizSytmCd;
	}

	public void setBizSytmCd(String bizSytmCd) {
		this.bizSytmCd = bizSytmCd;
	}

	public String getSrProgStatCd() {
		return srProgStatCd;
	}

	public void setSrProgStatCd(String srProgStatCd) {
		this.srProgStatCd = srProgStatCd;
	}

	public String getSrTypeClasCd() {
		return srTypeClasCd;
	}

	public void setSrTypeClasCd(String srTypeClasCd) {
		this.srTypeClasCd = srTypeClasCd;
	}

	public String getSrMngtNo() {
		return srMngtNo;
	}

	public void setSrMngtNo(String srMngtNo) {
		this.srMngtNo = srMngtNo;
	}

	public String getProdtWritYn() {
		return prodtWritYn;
	}

	public void setProdtWritYn(String prodtWritYn) {
		this.prodtWritYn = prodtWritYn;
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

	public String getMdtt() {
		return mdtt;
	}

	public void setMdtt(String mdtt) {
		this.mdtt = mdtt;
	}
    
   



}
