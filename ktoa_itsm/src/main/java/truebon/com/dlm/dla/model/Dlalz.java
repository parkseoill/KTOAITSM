package truebon.com.dlm.dla.model;

import java.io.Serializable;

/**
 * 근태를 위한 모델 클래스를 정의한다
 * @author 김도아
 * @since 2022.05.23
 * @version 1.0
 * @see
 *  
  * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *  수정일               수정자            수정내용
 *  ----------   --------   ---------------------------
 *  2022.05.23   김도아           최초 생성
 *  2022.05.31   이유리           수정
 * </pre>
 */

@SuppressWarnings("serial")
public class Dlalz implements Serializable{
   
   /** 근태일자 */
    private String dlalzDate;
    /** 담당자번호 */
    private String chrgrNo;
    /** 근태구분코드 */
    private String dlalzDstnctCd;
    /** 근무유형구분 */
    private String wrkTypeCd;
    /** 근무시간코드 */
    private String wrkTimeCd;
   /** 휴무유형코드 */
    private String offdayTypeCd;
    /** 휴일 근무 사유 코드 */
    private String offdayWrkRsnCd;
    /** 휴일 근무 사유 확인 */
    private String offdayWrkRsnCk;
    /** 반일휴무유형코드 */
    private String hlfdayOffdayDstnctCd;
    /** 출근시간 */
    private String atendTime;
    /** 출근시간 날짜 */
    private String atendTimeD;
    /** 출근시간 시간 */
    private String atendTimeT;
    /** 퇴근시간 */
    private String leofcTime;
    /** 퇴근시간 날짜*/
    private String leofcTimeD;
    /** 퇴근시간 시간 */
    private String leofcTimeT;
    /** 보안당직여부 */
    private String securBonduYn;
    /** 비고내용 */
    private String rmarkText;
    /** 등록자ID */
    private String regstrId;
    /** 작성일 */
    private String rdtt;
    /** 수정자ID */
    private String amndrId;
    /** 수정일 */
    private String mdtt;
    /** 담당자명 */
    private String chrgrNm;
    /** 검사할 날짜 */
    private String checkDate;
    /** 담당자역할코드 */
    private String chrgrRoleCd;
    /** 회사명 */
    private String cmpnyNm;
    /** 조회할 년월 날짜 */
    private String selectedDate = "";
   
   public String getDlalzDate() {
      return dlalzDate;
   }
   public void setDlalzDate(String dlalzDate) {
      this.dlalzDate = dlalzDate;
   }
   public String getChrgrNo() {
      return chrgrNo;
   }
   public void setChrgrNo(String chrgrNo) {
      this.chrgrNo = chrgrNo;
   }
   public String getHlfdayOffdayDstnctCd() {
      return hlfdayOffdayDstnctCd;
   }
   public void setHlfdayOffdayDstnctCd(String hlfdayOffdayDstnctCd) {
      this.hlfdayOffdayDstnctCd = hlfdayOffdayDstnctCd;
   }
   public String getDlalzDstnctCd() {
      return dlalzDstnctCd;
   }
   public void setDlalzDstnctCd(String dlalzDstnctCd) {
      this.dlalzDstnctCd = dlalzDstnctCd;
   }
   public String getWrkTypeCd() {
      return wrkTypeCd;
   }
   public void setWrkTypeCd(String wrkTypeCd) {
      this.wrkTypeCd = wrkTypeCd;
   }
   public String getWrkTimeCd() {
      return wrkTimeCd;
   }
   public void setWrkTimeCd(String wrkTimeCd) {
      this.wrkTimeCd = wrkTimeCd;
   }
   public String getOffdayTypeCd() {
      return offdayTypeCd;
   }
   public void setOffdayTypeCd(String offdayTypeCd) {
      this.offdayTypeCd = offdayTypeCd;
   }
   public String getAtendTime() {
      return atendTime;
   }
   public void setAtendTime(String atendTime) {
      this.atendTime = atendTime;
   }
   public String getLeofcTime() {
      return leofcTime;
   }
   public void setLeofcTime(String leofcTime) {
      this.leofcTime = leofcTime;
   }
   public String getSecurBonduYn() {
      return securBonduYn;
   }
   public void setSecurBonduYn(String securBonduYn) {
      this.securBonduYn = securBonduYn;
   }
   public String getRmarkText() {
      return rmarkText;
   }
   public void setRmarkText(String rmarkText) {
      this.rmarkText = rmarkText;
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
   public String getChrgrNm() {
      return chrgrNm;
   }
   public void setChrgrNm(String chrgrNm) {
      this.chrgrNm = chrgrNm;
   }
   public String getCheckDate() {
      return checkDate;
   }
   public void setCheckDate(String checkDate) {
      this.checkDate = checkDate;
   }
   public String getChrgrRoleCd() {
      return chrgrRoleCd;
   }
   public void setChrgrRoleCd(String chrgrRoleCd) {
      this.chrgrRoleCd = chrgrRoleCd;
   }
   public String getCmpnyNm() {
      return cmpnyNm;
   }
   public void setCmpnyNm(String cmpnyNm) {
      this.cmpnyNm = cmpnyNm;
   }
   public String getAtendTimeD() {
      return atendTimeD;
   }
   public void setAtendTimeD(String atendTimeD) {
      this.atendTimeD = atendTimeD;
   }
   public String getAtendTimeT() {
      return atendTimeT;
   }
   public void setAtendTimeT(String atendTimeT) {
      this.atendTimeT = atendTimeT;
   }
   public String getLeofcTimeD() {
      return leofcTimeD;
   }
   public void setLeofcTimeD(String leofcTimeD) {
      this.leofcTimeD = leofcTimeD;
   }
   public String getLeofcTimeT() {
      return leofcTimeT;
   }
   public void setLeofcTimeT(String leofcTimeT) {
      this.leofcTimeT = leofcTimeT;
   }
   public String getSelectedDate() {
      return selectedDate;
   }
   public void setSelectedDate(String selectedDate) {
      this.selectedDate = selectedDate;
   }
   public String getOffdayWrkRsnCd() {
      return offdayWrkRsnCd;
   }
   public void setOffdayWrkRsnCd(String offdayWrkRsnCd) {
      this.offdayWrkRsnCd = offdayWrkRsnCd;
   }
   public String getOffdayWrkRsnCk() {
      return offdayWrkRsnCk;
   }
   public void setOffdayWrkRsnCk(String offdayWrkRsnCk) {
      this.offdayWrkRsnCk = offdayWrkRsnCk;
   }
   
   
}