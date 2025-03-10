package truebon.com.sys.chr.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

import truebon.com.sys.chr.service.ChargerService;
import truebon.com.sys.chr.model.ChargerVO;
import truebon.com.sys.chr.model.Charger;

/**
 * @Class Name : ChargerMngController.java
 * @Description : Charger Controller class
 * @Modification Information
 *
 * @author 이태신
 * @since 2022.05.04
 * @version 1.0
 * @see
 *  
 *  Copyright (C)  All right reserved.
 */

@Controller
@SessionAttributes(types=Charger.class)
public class ChargerController {
	
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
	
    /**
	 * 담당자 목록화면 (pageing)
	 * @param searchVO - 조회할 정보가 담긴 ChargerVO
	 * @exception Exception
	 */
    @RequestMapping(value="/sys/chr/ChargerMngList.do")
    public String ChargerMngList(@ModelAttribute("searchVO") ChargerVO searchVO, 
    		ModelMap model, HttpServletRequest request)
            throws Exception {
    	
    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));
    	
    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<String> arrChrgrDstnctCd = new ArrayList<String>();
		if(searchVO.getSearchCondition() == "") {
			arrChrgrDstnctCd.add("1");
			searchVO.setSearchCode("1");
		}
		if(searchVO.getSearchCode()!=null && searchVO.getSearchCode()!="") {
			String str = searchVO.getSearchCode();
			String[] splitStr = str.split(",");
			for(int i=0; i<splitStr.length; i++){
				arrChrgrDstnctCd.add(splitStr[i]);
			}
			 searchVO.setArrChrgrDstnctCd(arrChrgrDstnctCd);
			 model.addAttribute("searchCodeList", arrChrgrDstnctCd);
		}
		
		// 목록
        List<ChargerVO> chargerList = chargerService.selectChargerList(searchVO);
        model.addAttribute("resultList", chargerList);
        model.addAttribute("searchVO", searchVO);
        
        int totCnt = chargerService.selectChargerListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
        
        return "/truebon/com/sys/chr/ChargerMngList";
    } 
    
    /**
 	 * 팝업 담당자 목록화면 (pageing)
 	 * @param searchVO - 조회할 정보가 담긴 ChargerVO
 	 * @exception Exception
 	 */
     @RequestMapping(value="/sys/chr/ChargerMngListPopup.do")
     public String ChargerMngListPopup(@ModelAttribute("searchVO") ChargerVO searchVO, 
     		ModelMap model)
             throws Exception {
     	
     	/** EgovPropertyService.sample */
     	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
     	searchVO.setPageSize(propertiesService.getInt("pageSize"));
     	
     	/** pageing */
     	PaginationInfo paginationInfo = new PaginationInfo();
 		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
 		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
 		paginationInfo.setPageSize(searchVO.getPageSize());
 		
 		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
 		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
 		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
 		
 		// 목록
         List<ChargerVO> chargerList = chargerService.selectChargerList(searchVO);
         model.addAttribute("resultList", chargerList);
         model.addAttribute("searchVO", searchVO);
         
         int totCnt = chargerService.selectChargerListTotCnt(searchVO);
 		paginationInfo.setTotalRecordCount(totCnt);
         model.addAttribute("paginationInfo", paginationInfo);
         
         return "/truebon/com/sys/chr/ChargerMngListPopup";
     } 
     
     /**
  	 * 팝업 담당자 목록 화면2 (방문자)
  	 * @param searchVO - 조회할 정보가 담긴 ChargerVO
  	 * @exception Exception
  	 */
      @RequestMapping(value="/dlm/vis/VisitMngListPopup.do")
      public String VisitMngListPopup(@ModelAttribute("searchVO") ChargerVO searchVO, 
      		ModelMap model)
              throws Exception {
      	
      	/** EgovPropertyService.sample */
      	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
      	searchVO.setPageSize(propertiesService.getInt("pageSize"));
      	
      	/** pageing */
      	PaginationInfo paginationInfo = new PaginationInfo();
  		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
  		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
  		paginationInfo.setPageSize(searchVO.getPageSize());
  		
  		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
  		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
  		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
  		
  		// 목록
        List<ChargerVO> chargerList = chargerService.selectChargerVisitList(searchVO);
        model.addAttribute("resultList", chargerList);
        model.addAttribute("searchVO", searchVO);

        int totCnt = chargerService.ChargerListPopTotCnt(searchVO);
  		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);
          
        return "/truebon/com/dlm/vis/VisitMngListPopup";
	}

	/**
	 * 팝업 담당자 목록 화면3 (서비스요청관리)
	 * 
	 * @param searchVO - 조회할 정보가 담긴 ChargerVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/opm/srm/SrMasterMngListPopup.do")
	public String SrMasterMngListPopup(@ModelAttribute("searchVO") ChargerVO searchVO, ModelMap model) throws Exception {

		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// 목록
		List<ChargerVO> chargerList = chargerService.selectChargerSrList(searchVO);
		model.addAttribute("resultList", chargerList);
		model.addAttribute("searchVO", searchVO);

		int totCnt = chargerService.ChargerSrListPopTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "/truebon/com/opm/srm/SrMasterMngListPopup";
	}

	/**
	 * 담당자 등록화면
	 * 
	 * @param chargerVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sys/chr/ChargerMngRegist.do")
	public String ChargerMngRegist(@ModelAttribute("chargerVO") ChargerVO chargerVO, Model model) throws Exception {

		// 공통코드(담당자 구분)
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setComnCdId("ISM001");
		List<?> chrgrDstnctCdList = cmmUseService.selectCmmCodeDetail(vo);

		// 공통코드(담당자 역할)
		vo.setComnCdId("ISM008");
		List<?> chrgrRoleCdList = cmmUseService.selectCmmCodeDetail(vo);
		
		// 공통코드(비상연락 관계)
		vo.setComnCdId("ISM014");
		List<?> emgcyContReltnCdList = cmmUseService.selectCmmCodeDetail(vo);

		model.addAttribute("chrgrDstnctCdList", chrgrDstnctCdList);
		model.addAttribute("chrgrRoleCdList", chrgrRoleCdList);
		model.addAttribute("emgcyContReltnCdList", emgcyContReltnCdList);
		model.addAttribute("chargerVO", chargerVO);
		return "/truebon/com/sys/chr/ChargerMngRegist";
	}

	/**
	 * 담당자 등록
	 * 
	 * @param chargerVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sys/chr/inseartChargerMngRegist.do")
	public String inseartChargerMngRegist(@ModelAttribute("chargerVO") ChargerVO chargerVO,
	        BindingResult bindingResult,
            Model model)
            throws Exception {
    	String sLocationUrl = "";
    	String resultMsg    = "";
    	
    	// 유효성 검사
    	beanValidator.validate(chargerVO, bindingResult);
    	if (bindingResult.hasErrors()){
			sLocationUrl = "/truebon/com/sys/chr/ChargerMngRegist";
			return sLocationUrl;
		}else {
			chargerService.insertCharger(chargerVO);
	    	resultMsg = egovMessageSource.getMessage("success.common.insert");
	        sLocationUrl = "forward:/sys/chr/ChargerMngList.do";
		}
    	
    	model.addAttribute("resultMsg", resultMsg);
        return sLocationUrl;
    }
    
    /**
     * 담당자 수정화면
     * @param searchVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/sys/chr/ChargerMngUpdt.do")
    public String ChargerMngUpdt(
            @ModelAttribute("searchVO") ChargerVO searchVO,
            Model model)
            throws Exception {
    	
    	//공통코드(담당자 구분)
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setComnCdId("ISM001");
		List<?> chrgrDstnctCdList = cmmUseService.selectCmmCodeDetail(vo);
		
		//공통코드(담당자 역할)
		vo.setComnCdId("ISM008");
		List<?> chrgrRoleCdList = cmmUseService.selectCmmCodeDetail(vo);
		
		// 공통코드(비상연락 관계)
		vo.setComnCdId("ISM014");
		List<?> emgcyContReltnCdList = cmmUseService.selectCmmCodeDetail(vo);
		
		// 상세정보 조회
    	ChargerVO resultVO = chargerService.selectCharger(searchVO);
    	
		model.addAttribute("chrgrDstnctCdList", chrgrDstnctCdList);
		model.addAttribute("chrgrRoleCdList", chrgrRoleCdList);
		model.addAttribute("emgcyContReltnCdList", emgcyContReltnCdList);
        model.addAttribute("chargerVO", resultVO);
        
        return "/truebon/com/sys/chr/ChargerMngUpdt";
    }

    /**
     * 담당자 수정
     * @param chargerVO
     * @param searchVO
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping("/sys/chr/updateChargerMngUpdt.do")
    public String updateChargerMngUpdt(
            @ModelAttribute("chargerVO") ChargerVO chargerVO,
            BindingResult bindingResult,
    		ModelMap model)
            throws Exception {
    	String sLocationUrl = "";
    	String resultMsg    = "";
    	
    	// 유효성 검사
    	beanValidator.validate(chargerVO, bindingResult);
    	if (bindingResult.hasErrors()){
			sLocationUrl = "/truebon/com/sys/chr/ChargerMngUpdt";
			return sLocationUrl;
		}else {
			if("1".equals(chargerVO.getSearchCode())) {
				chargerService.updateCharger(chargerVO);
				sLocationUrl = "forward:/sys/chr/KtoaChrMngList.do";
			}else if("2".equals(chargerVO.getSearchCode())) {
				chargerService.updateCharger(chargerVO);
				sLocationUrl = "forward:/sys/chr/CosnChrMngList.do";
			}else if("3".equals(chargerVO.getSearchCode())) {
				chargerService.updateCharger(chargerVO);
				sLocationUrl = "forward:/sys/chr/EtcChrMngList.do";
			}else {
				chargerService.updateCharger(chargerVO);
				sLocationUrl = "forward:/sys/chr/ChargerMngList.do";
			}
			resultMsg = egovMessageSource.getMessage("success.common.update");
		}
    	
    	model.addAttribute("resultMsg", resultMsg);
        return sLocationUrl;
    }
    
    /**
     * 담당자 삭제
     * @param chargerVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/sys/chr/deleteChargerMng.do")
    public String deleteChargerMng(
            @ModelAttribute("chargerVO") ChargerVO chargerVO,
            ModelMap model)
            throws Exception {
    	String resultMsg    = "";
        
    	chargerService.deleteCharger(chargerVO);
    	resultMsg = egovMessageSource.getMessage("success.common.delete");
        
    	model.addAttribute("resultMsg", resultMsg);
        return "forward:/sys/chr/ChargerMngList.do";
    }
    
    /**
     * 프로그램목록 멀티 삭제한다.
     * @param searchVO
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping("/sys/chr/deleteChargerMngList.do")
    public String deleteChargerMngList(
            @ModelAttribute("searchVO") ChargerVO searchVO,
            ModelMap model)
            throws Exception {
    	
    	// 결과 메시지
        String resultMsg    = "";
        String [] delChrgrNo = searchVO.getCheckedValueForDel().split(",");
        
		if (delChrgrNo == null || (delChrgrNo.length ==0)){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete");
		}else{
			chargerService.deleteChargerMngList(searchVO);
			resultMsg = egovMessageSource.getMessage("success.common.delete");
		}
		model.addAttribute("resultMsg", resultMsg);
		
        return "forward:/sys/chr/ChargerMngList.do";
    }

}
