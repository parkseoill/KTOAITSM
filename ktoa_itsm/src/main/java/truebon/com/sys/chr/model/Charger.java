package truebon.com.sys.chr.model;

import java.io.Serializable;

/**
 * 담당자를 위한 모델 클래스를 정의한다.
 * @author 이태신
 * @since 2022.05.04
 * @version 1.0
 * @see
 *  
  * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *  수정일               수정자            수정내용
 *  ----------   --------   ---------------------------
 *  2022.05.04   이태신           최초 생성
 *
 * </pre>
 */
@SuppressWarnings("serial")
public class Charger implements Serializable {
    
	/** 담당자번호 */
    private String chrgrNo;
    
    /** 담당자명 */
    private String chrgrNm;
    
    /** 담당자유선전화번호 */
    private String chrgrWirelnTelNo;
    
    /** 담당자무선전화번호 */
    private String chrgrWirelsTelNo;
    
    /** 담당자주소 */
    private String chrgrAddr;
    
    /** 담당자이메일주소 */
    private String chrgrEaddr;
    
    /** 직급 */
    private String clspos;
    
    /** 소속회사번호 */
    private String positCmpnyNo;
    
    /** 소속부서번호 */
    private String positDeptNo;
    
    /** 비상연락전화번호 */
    private String emgcyContTelNo;
    
    /** 비상연락관계코드 */
    private String emgcyContReltnCd;
    
    /** 담당자구분코드 */
    private String chrgrDstnctCd;
    
    /** 위탁회사번호 */
    private String cosnCmpnyNo;
    
    /** 위탁부서번호 */
    private String cosnDeptNo;
    
    /** 담당자 역할 코드 */
    private String chrgrRoleCd;

	/** 사용여부 */
    private String useYn;
    
    /** 등록자ID */
    private String regstrId;
    
    /** 등록일 */
    private String rdtt;
    
    /** 수정자ID */
    private String amndrId;
    
    /** 수정일 */
    private String mdtt;
    
    public String getChrgrNo() {
        return this.chrgrNo;
    }
    
    public void setChrgrNo(String chrgrNo) {
        this.chrgrNo = chrgrNo;
    }
    
    public String getChrgrNm() {
        return this.chrgrNm;
    }
    
    public void setChrgrNm(String chrgrNm) {
        this.chrgrNm = chrgrNm;
    }
    
    public String getChrgrWirelnTelNo() {
        return this.chrgrWirelnTelNo;
    }
    
    public void setChrgrWirelnTelNo(String chrgrWirelnTelNo) {
        this.chrgrWirelnTelNo = chrgrWirelnTelNo;
    }
    
    public String getChrgrWirelsTelNo() {
        return this.chrgrWirelsTelNo;
    }
    
    public void setChrgrWirelsTelNo(String chrgrWirelsTelNo) {
        this.chrgrWirelsTelNo = chrgrWirelsTelNo;
    }
    
    public String getChrgrAddr() {
        return this.chrgrAddr;
    }

	public void setChrgrAddr(String chrgrAddr) {
        this.chrgrAddr = chrgrAddr;
    }
	
	public String getChrgrEaddr() {
		return chrgrEaddr;
	}

	public void setChrgrEaddr(String chrgrEaddr) {
		this.chrgrEaddr = chrgrEaddr;
	}
    
    public String getClspos() {
        return this.clspos;
    }
    
    public void setClspos(String clspos) {
        this.clspos = clspos;
    }
    
    public String getPositCmpnyNo() {
        return this.positCmpnyNo;
    }
    
    public void setPositCmpnyNo(String positCmpnyNo) {
        this.positCmpnyNo = positCmpnyNo;
    }
    
    public String getPositDeptNo() {
        return this.positDeptNo;
    }
    
    public void setPositDeptNo(String positDeptNo) {
        this.positDeptNo = positDeptNo;
    }
    
    public String getEmgcyContTelNo() {
		return emgcyContTelNo;
	}

	public void setEmgcyContTelNo(String emgcyContTelNo) {
		this.emgcyContTelNo = emgcyContTelNo;
	}

	public String getEmgcyContReltnCd() {
		return emgcyContReltnCd;
	}

	public void setEmgcyContReltnCd(String emgcyContReltnCd) {
		this.emgcyContReltnCd = emgcyContReltnCd;
	}

	public String getChrgrDstnctCd() {
        return this.chrgrDstnctCd;
    }
    
    public void setChrgrDstnctCd(String chrgrDstnctCd) {
        this.chrgrDstnctCd = chrgrDstnctCd;
    }
    
    public String getCosnCmpnyNo() {
        return this.cosnCmpnyNo;
    }
    
    public void setCosnCmpnyNo(String cosnCmpnyNo) {
        this.cosnCmpnyNo = cosnCmpnyNo;
    }
    
    public String getCosnDeptNo() {
        return this.cosnDeptNo;
    }
    
    public void setCosnDeptNo(String cosnDeptNo) {
        this.cosnDeptNo = cosnDeptNo;
    }
    
    public String getChrgrRoleCd() {
		return chrgrRoleCd;
	}

	public void setChrgrRoleCd(String chrgrRoleCd) {
		this.chrgrRoleCd = chrgrRoleCd;
	}
    
    public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
    
    public String getRegstrId() {
        return this.regstrId;
    }
    
    public void setRegstrId(String regstrId) {
        this.regstrId = regstrId;
    }
    
    public String getRdtt() {
        return this.rdtt;
    }
    
    public void setRdtt(String rdtt) {
        this.rdtt = rdtt;
    }
    
    public String getAmndrId() {
        return this.amndrId;
    }
    
    public void setAmndrId(String amndrId) {
        this.amndrId = amndrId;
    }
    
    public String getMdtt() {
        return this.mdtt;
    }
    
    public void setMdtt(String mdtt) {
        this.mdtt = mdtt;
    }
    
}
