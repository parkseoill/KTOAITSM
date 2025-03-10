package truebon.com.dlm.dla.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.bcel.generic.DALOAD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import truebon.com.dlm.cod.service.ComnDateService;
import truebon.com.dlm.dla.model.Dlalz;
import truebon.com.dlm.dla.model.DlalzVO;
import truebon.com.dlm.dla.service.DlalzService;
import truebon.com.sys.chr.model.ChargerVO;
import truebon.com.sys.chr.service.ChargerService;

@Controller
@SessionAttributes(types=Dlalz.class)
public class DlalzController {

   /* Validator */
   @Autowired
   private DefaultBeanValidator beanValidator;

   /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
   
   /** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    /** 공통코드 Service */
    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;
    
    /** 담당자 Service */
    @Resource(name = "ChargerService")
    private ChargerService chargerService;
   
    /** 근태  Service */
    @Resource(name = "DlalzService")
    private DlalzService dlalzService;
    
    /** 공통일자 Service */
    @Resource(name = "ComnDateService")
    private ComnDateService comnDateService;
    
    /** 로그설정 */
   private static final Logger LOGGER = LoggerFactory.getLogger(DlalzController.class);
    
    /**
    * 근태 목록화면 (pageing)
    * @param searchVO - 조회할 정보가 담긴 DlalzVO
    * @exception Exception
    */
    @RequestMapping(value="/dlm/dla/DlalzMngList.do")
    public String DlalzMngList(@ModelAttribute("searchVO") DlalzVO searchVO, 
          ModelMap model)
            throws Exception {
       
       /** EgovPropertyService.sample */
       searchVO.setPageUnit(31);
       searchVO.setPageSize(propertiesService.getInt("pageSize"));
       
       /** pageing */
       PaginationInfo paginationInfo = new PaginationInfo();
      paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
      paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
      paginationInfo.setPageSize(searchVO.getPageSize());
      
      searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
      searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
      searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
      
      //공통코드(휴가 유형)
      /*
       * ComDefaultCodeVO vo1 = new ComDefaultCodeVO(); vo1.setComnCdId("ISM006");
       * List<?> offdayTypeCdList = cmmUseService.selectCmmCodeDetail(vo1);
       */
      
      //공통코드(근무 유형)
      ComDefaultCodeVO vo2 = new ComDefaultCodeVO();
      vo2.setComnCdId("ISM005");
      List<?> wrkTypeCdList = cmmUseService.selectCmmCodeDetail(vo2);
      
      //공통코드(반일휴가 구분)
      ComDefaultCodeVO vo3 = new ComDefaultCodeVO();
      vo3.setComnCdId("ISM007");
      List<?> hlfdayOffdayDstnctCdList = cmmUseService.selectCmmCodeDetail(vo3);
      
      //성명 목록 불러오기
      ChargerVO vo = new ChargerVO();
      List<?> nameList = chargerService.selectNameList(vo);
      
      // 목록
        List<DlalzVO> dlalzList = dlalzService.selectDlalzList(searchVO);
       
        //model.addAttribute("offdayTypeCdList", offdayTypeCdList);
        model.addAttribute("wrkTypeCdList", wrkTypeCdList);
        model.addAttribute("hlfdayOffdayDstnctCdList", hlfdayOffdayDstnctCdList);
        model.addAttribute("resultList", dlalzList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("nameList", nameList);
        
        int totCnt = dlalzService.selectDlalzListTotCnt(searchVO);
      paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        return "/truebon/com/dlm/dla/DlalzMngList";
    } 

    /**
     * 근태 등록
     * @param JasperVO
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/dlm/dla/insertDlalzMngRegist.do")
    public String insertDlalzMngRegist(@ModelAttribute("searchVO") DlalzVO searchVO,
            @ModelAttribute("dlalzVO") DlalzVO dlalzVO,
            BindingResult bindingResult,
            Model model)
            throws Exception {
       String sLocationUrl = "";
       String resultMsg    = "";
       int existDDate = -1;
       int existCDate = -1;
       existDDate = dlalzService.selectNotExistChargerListTotCnt(searchVO);
       existCDate = comnDateService.checkComnDate(searchVO.getCheckDate());
       
       if(existCDate == 1) {
          if(existDDate != 0) {
             dlalzService.insertDlalz(dlalzVO);
             resultMsg = egovMessageSource.getMessage("success.common.insert");
          }else {
             resultMsg = "모든 사용자에 대한 데이터가 존재하므로 생성이 불가합니다.";
          }
       }else {
          resultMsg = "해당 월은 공통일자에 존재하지 않으므로 생성이 불가합니다.";
       } 
       
       sLocationUrl = "forward:/dlm/dla/DlalzMngList.do";
      /*
       * // 유효성 검사 beanValidator.validate(dlalzVO, bindingResult); if
       * (bindingResult.hasErrors()){ sLocationUrl =
       * "/truebon/com/dlm/dla/DlalzMngRegist"; return sLocationUrl; }else {
       * dlalzService.insertDlalz(dlalzVO); resultMsg =
       * egovMessageSource.getMessage("success.common.insert");
       * 
       * } sLocationUrl = "forward:/dlm/dla/DlalzMngList.do";
       */
          
       model.addAttribute("resultMsg", resultMsg);
        return sLocationUrl;
    }
    
    /**
     * 근태 수정화면
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/dlm/dla/DlalzMngUpdt.do")
    public String DlalzMngUpdt(
            @ModelAttribute("searchVO") DlalzVO searchVO,
            Model model)
            throws Exception {
       
       //공통코드(근태 유형)
      ComDefaultCodeVO vo = new ComDefaultCodeVO();
      vo.setComnCdId("ISM004");
      List<?> dlalzDstnctCdList = cmmUseService.selectCmmCodeDetail(vo);
       
       //공통코드(휴가 유형)
      ComDefaultCodeVO vo1 = new ComDefaultCodeVO();
      vo1.setComnCdId("ISM006");
      List<?> offdayTypeCdList = cmmUseService.selectCmmCodeDetail(vo1);
      
      //공통코드(근무 유형)
      ComDefaultCodeVO vo2 = new ComDefaultCodeVO();
      vo2.setComnCdId("ISM005");
      List<?> wrkTypeCdList = cmmUseService.selectCmmCodeDetail(vo2);
      
      //공통코드(반일휴가 구분)
      ComDefaultCodeVO vo3 = new ComDefaultCodeVO();
      vo3.setComnCdId("ISM007");
      List<?> hlfdayOffdayDstnctCdList = cmmUseService.selectCmmCodeDetail(vo3);
       
      //공통코드(근무시간 구분)
      ComDefaultCodeVO vo4 = new ComDefaultCodeVO();
      vo4.setComnCdId("ISM009");
      List<?> wrkTimeCdList = cmmUseService.selectCmmCodeDetail(vo4);
      
      //공통코드(휴일 근무 사유 구분)
      ComDefaultCodeVO vo5 = new ComDefaultCodeVO();
      vo5.setComnCdId("ISM017");
      List<?> offdayWrkRsnCdList = cmmUseService.selectCmmCodeDetail(vo5);
      
      
       // 상세정보 조회
       DlalzVO resultVO = dlalzService.selectDlalz(searchVO);
        model.addAttribute("dlalzVO", resultVO);
        model.addAttribute("dlalzDstnctCdList", dlalzDstnctCdList);
        model.addAttribute("offdayTypeCdList", offdayTypeCdList);
        model.addAttribute("wrkTypeCdList", wrkTypeCdList);
        model.addAttribute("wrkTimeCdList", wrkTimeCdList);
        model.addAttribute("hlfdayOffdayDstnctCdList", hlfdayOffdayDstnctCdList);
        model.addAttribute("offdayWrkRsnCdList", offdayWrkRsnCdList);
        return "/truebon/com/dlm/dla/DlalzMngUpdt";
    }
    
    /**
     * 담당자 수정
     * @param dlalzVO
     * @param searchVO
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping("/dlm/dla/updateDlalzMngUpdt.do")
    public String updateDlalzMngUpdt(
            @ModelAttribute("dlalzVO") DlalzVO dlalzVO,
            BindingResult bindingResult,
          ModelMap model)
            throws Exception {
       String sLocationUrl = "";
       String resultMsg    = "";
       
       
         // 유효성 검사
         beanValidator.validate(dlalzVO, bindingResult);
         if (bindingResult.hasErrors()){
         sLocationUrl = "/truebon/com/dlm/dla/DlalzMngUpdt";
         return sLocationUrl;
      }else {
         dlalzService.updateDlalz(dlalzVO);
         resultMsg = egovMessageSource.getMessage("success.common.update");
         sLocationUrl = "forward:/dlm/dla/DlalzMngList.do";
      }
      
         model.addAttribute("resultMsg", resultMsg);
         return "forward:/dlm/dla/DlalzMngList.do";
      /*
       * 
       * // 유효성 검사 beanValidator.validate(dlalzVO, bindingResult); if
       * (bindingResult.hasErrors()){ sLocationUrl =
       * "/truebon/com/dlm/dla/DlalzMngUpdt"; return sLocationUrl; }else
       * if(!dlalzVO.getOffdayWrkRsnCk().equals("") &&
       * !dlalzVO.getOffdayWrkRsnCd().equals("0") &&
       * !dlalzVO.getOffdayWrkRsnCk().equals("0")){
       * dlalzService.updateReOffday(dlalzVO); dlalzService.updateDlalz(dlalzVO);
       * 
       * resultMsg = egovMessageSource.getMessage("success.common.update");
       * sLocationUrl = "forward:/dlm/dla/DlalzMngList.do"; }else
       * if(dlalzVO.getOffdayWrkRsnCd().equals("0")) {
       * dlalzService.deleteReOffday(dlalzVO); dlalzService.updateDlalz(dlalzVO);
       * }else { dlalzService.insertReOffday(dlalzVO);
       * dlalzService.updateDlalz(dlalzVO);
       * 
       * resultMsg = egovMessageSource.getMessage("success.common.update");
       * sLocationUrl = "forward:/dlm/dla/DlalzMngList.do"; }
       * 
       * 
       * model.addAttribute("resultMsg", resultMsg); return
       * "forward:/dlm/dla/DlalzMngList.do";
       */
    }
    
    /**
     * 출퇴근 기록부 보고서
     * @param dlalzVO
     * @param searchVO
     * @param status
     * @return
     * @throws Exception
     */
   @RequestMapping("/dlm/dla/RepoDlalzMngList.do")
   public String RepoDlalzMngList(@ModelAttribute("dlalzVO") DlalzVO dlalzVO,
         ModelMap model)
         throws Exception {
      
      return "/truebon/com/dlm/dla/RepoDlalzMngList";
   }
    
}