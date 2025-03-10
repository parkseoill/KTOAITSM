package truebon.com.opm.srm.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import truebon.com.opm.biz.model.BizSytmVO;
import truebon.com.opm.biz.service.BizSytmService;
import truebon.com.opm.com.model.ComnCdNewVO;
import truebon.com.opm.com.service.ComnCdNewService;
import truebon.com.opm.srm.model.SrMaster;
import truebon.com.opm.srm.model.SrMasterVO;
import truebon.com.opm.srm.service.SrMasterService;
import truebon.com.sys.chr.model.ChargerVO;
import truebon.com.sys.chr.service.ChargerService;

/**
 * @Class Name : SrMasterMngController.java
 * @Description : SrMaster Controller class
 * @Modification Information
 *
 * @author 김도아
 * @since 2022.07.28
 * @version 1.0
 * @see
 * 
 *      Copyright (C) All right reserved.
 */

@Controller
@SessionAttributes(types = SrMaster.class)
public class SrMasterController {

	/* Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** 공통코드 Service */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/** 새공통코드 Service */
	@Resource(name = "ComnCdNewService")
	private ComnCdNewService comnCdNewService;
	
	/** 업무시스템 Service */
	@Resource(name = "BizSytmService")
	private BizSytmService bizSytmService;

	/** 서비스요청관리 Service */
	@Resource(name = "SrMasterService")
	private SrMasterService srMasterService;
	
    /** 담당자 Service */
    @Resource(name = "ChargerService")
    private ChargerService chargerService;
    
    /** Message ID Generation */
    @Resource(name="egovSrMasterIdGnrService")    
    private EgovIdGnrService egovSrMasterIdGnrService;
    
    /** 로그설정 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SrMasterController.class);
    

	/**
	 * 서비스요청관리 목록화면 (pageing)
	 * 
	 * @param searchVO - 조회할 정보가 담긴 srMasterVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/opm/srm/SrMasterMngList.do")
	public String SrMasterMngList(@ModelAttribute("searchVO") SrMasterVO searchVO, ModelMap model) throws Exception {

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
		List<SrMasterVO> srMasterList = srMasterService.selectSrMasterList(searchVO);
		model.addAttribute("resultList", srMasterList);
		model.addAttribute("searchVO", searchVO);
		
		//담당자 리스트
		ChargerVO vo = new ChargerVO();
		List<?> nameList = chargerService.selectNameList(vo);
		
		// 업무시스템목록
		BizSytmVO vo4 = new BizSytmVO();
		vo4.setBizSytmCdLvl("2");
		List<?> bizSytmList = bizSytmService.selectBizSytmNmList(vo4);
		
		//요청부서목록
		ChargerVO vo2 = new ChargerVO();
		List<?> deptList = chargerService.selectSrDeptList(vo2);

		model.addAttribute("bizSytmList", bizSytmList);
		model.addAttribute("nameList", nameList);
		model.addAttribute("deptList", deptList);
		
		int totCnt = srMasterService.selectSrMasterListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "/truebon/com/opm/srm/SrMasterMngList";
	}

	
	/**
	 * 서비스요청관리 등록화면
	 * 
	 * @param srMasterVO
	 * @param model
	 * @param comnCdNewService
	 * @param bizSytmService
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/opm/srm/SrMasterMngRegist.do")
	public String SrMasterMngRegist(@ModelAttribute("srMasterVO") SrMasterVO srMasterVO, 
			Model model) throws Exception {
		
		// 요청 대분류
		ComnCdNewVO vo1 = new ComnCdNewVO();
		vo1.setUpprNewComnCd("SRT");
		List<?> srTypeList = comnCdNewService.selectComnCdNewList(vo1);

		// 업무시스템목록
		BizSytmVO vo4 = new BizSytmVO();
		vo4.setBizSytmCdLvl("2");
		List<?> bizSytmList = bizSytmService.selectBizSytmNmList(vo4);

		// 서비스진행상태
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setComnCdId("ISM013");
		List<?> srProgStatCdList = cmmUseService.selectCmmCodeDetail(vo);
		
		
		model.addAttribute("srTypeList", srTypeList);
		model.addAttribute("srProgStatCdList", srProgStatCdList);
		model.addAttribute("bizSytmList", bizSytmList);
		model.addAttribute("srMasterVO", srMasterVO);
		return "/truebon/com/opm/srm/SrMasterMngRegist";
	}



	/**
	 * 서비스요청관리 등록
	 * 
	 * @param srMasterVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/opm/srm/insertSrMasterMngRegist.do")
	public String insertSrMasterMngRegist(@ModelAttribute("") SrMasterVO srMasterVO, BindingResult bindingResult,
			Model model) throws Exception {
		String sLocationUrl = "";
		String resultMsg = "";

		// 유효성 검사
		beanValidator.validate(srMasterVO, bindingResult);
		if (bindingResult.hasErrors()) {
			sLocationUrl = "/truebon/com/opm/srm/SrMasterMngRegist";
			return sLocationUrl;
		} else {
			// 서비스요청관리번호
			srMasterVO.setSrMngtNo(egovSrMasterIdGnrService.getNextStringId());
			srMasterVO.setSrMngtNo(srMasterVO.getSrMngtNo());
			
			srMasterService.insertSrMaster(srMasterVO);
			resultMsg = egovMessageSource.getMessage("success.common.insert");
			sLocationUrl = "forward:/opm/srm/SrMasterMngList.do";
		}

		model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
	}
	
	/**
	 * 요청 분류
	 * 
	 * @param srMasterVO
	 * @param comnCdNewVO
	 * @param bindingResult
	 * @param searchVO
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/opm/srm/SrMasterType.do")
	@ResponseBody
	public ModelAndView SrMasterType(@ModelAttribute("ComnCdNewVO") SrMasterVO srMasterVO, ComnCdNewVO comnCdNewVO,
			BindingResult bindingResult, Model model ,
			@RequestParam("newComnCd") String newComnCd) throws Exception {

		ModelAndView mav = new ModelAndView("jsonView");
		
		ComnCdNewVO vo = new ComnCdNewVO();
		vo.setUpprNewComnCd(newComnCd);
		List<?> srTypeClasCdList = comnCdNewService.selectComnCdNewList(vo);
		
		
		mav.addObject("srTypeClasCdList", srTypeClasCdList);
    	
    	return mav;
	}
	

	/**
	 * 서비스요청관리 수정화면
	 * 
	 * @param searchVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/opm/srm/SrMasterMngUpdt.do")
	public String SrMasterMngUpdt(@ModelAttribute("searchVO") SrMasterVO searchVO, Model model) throws Exception {
		
		
		// 요청 대분류
		ComnCdNewVO vo1 = new ComnCdNewVO();
		vo1.setUpprNewComnCd("SRT");
		List<?> srTypeList = comnCdNewService.selectComnCdNewList(vo1);

		// 업무시스템목록
		BizSytmVO vo4 = new BizSytmVO();
		vo4.setBizSytmCdLvl("2");
		List<?> bizSytmList = bizSytmService.selectBizSytmNmList(vo4);

		// 서비스진행상태
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setComnCdId("ISM013");
		List<?> srProgStatCdList = cmmUseService.selectCmmCodeDetail(vo);
		

		model.addAttribute("srTypeList", srTypeList);
		model.addAttribute("bizSytmList", bizSytmList);
		model.addAttribute("srProgStatCdList", srProgStatCdList);
		// 상세정보 조회
		SrMasterVO resultVO = srMasterService.selectSrMaster(searchVO);
		model.addAttribute("srMasterVO", resultVO);

		return "/truebon/com/opm/srm/SrMasterMngUpdt";
	}

	/**
	 * 서비스요청관리 수정
	 * 
	 * @param srMasterVO
	 * @param searchVO
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/opm/srm/updateSrMasterMngUpdt.do")
	public String updateSrMasterMngUpdt(@ModelAttribute("srMasterVO") SrMasterVO srMasterVO, BindingResult bindingResult,
			ModelMap model) throws Exception {
		String sLocationUrl = "";
		String resultMsg = "";

		// 유효성 검사
		beanValidator.validate(srMasterVO, bindingResult);
		if (bindingResult.hasErrors()) {
			sLocationUrl = "/truebon/com/opm/srm/SrMasterMngUpdt";
			return sLocationUrl;
		} else {
			srMasterService.updateSrMaster(srMasterVO);
			resultMsg = egovMessageSource.getMessage("success.common.update");
			sLocationUrl = "forward:/opm/srm/SrMasterMngList.do";
		}

		model.addAttribute("resultMsg", resultMsg);
		return "forward:/opm/srm/SrMasterMngList.do";
	}

	/**
	 * 서비스요청관리 목록 팝업 화면
	 * 
	 * @param searchVO - 조회할 정보가 담긴 srMasterVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/opm/srm/SrMasterMngListPopup2.do")
	public String SrMasterMngListPopup2(@ModelAttribute("searchVO") SrMasterVO searchVO, ModelMap model) throws Exception {
		// 사용자 정보 설정
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		searchVO.setSearchName(user.getChrgrNo());
		
		// 시작일이 없다면 최초 팝업을 띄운 것으로 시작일과 종료일을 자동 등록
		if(searchVO.getStartDate().isEmpty()) {
			LocalDateTime currDateTime = LocalDateTime.now();
			// 현재 날짜를 30일전으로 설정
			LocalDateTime chgDateTime = currDateTime.minusDays(30);
			String formatedStartDateTime = chgDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			// 현재 날짜를 종료일로 설정
			String formatedEndDateTime = currDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
			searchVO.setStartDate(formatedStartDateTime);
			searchVO.setEndDate(formatedEndDateTime);
		}
		
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
		List<SrMasterVO> srMasterList = srMasterService.selectSrMasterList(searchVO);
		model.addAttribute("resultList", srMasterList);
		model.addAttribute("searchVO", searchVO);
		
		int totCnt = srMasterService.selectSrMasterListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "/truebon/com/opm/srm/SrMasterMngListPopup2";
	}
	
	/**
     * 서비스 요청 텍스트 등록
     * @param SrMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/opm/srm/insertSrMngt.do")
    public ModelAndView insertSrMngt(
            @ModelAttribute("srMasterVO") SrMasterVO srMasterVO,
            Model model)
            throws Exception {
    	List<SrMasterVO> srMasterList = null;
    	
    	ModelAndView mav = new ModelAndView("jsonView");
    	
    	try {
    		// 페이징을 사용하지 않기 위해 기본 값 세팅
    		srMasterVO.setRecordCountPerPage(0);
    		srMasterList = srMasterService.selectSrMasterList(srMasterVO);
    		
    		// 주간(01), 월간(02)코드로 사용 여부 체크
    		if("01".equals(srMasterVO.getBizReportKindCd())) {
    			srMasterVO.setWeekyReportTargtYn("Y");
    		} else if("02".equals(srMasterVO.getBizReportKindCd())) {
    			srMasterVO.setMonthyReportTargtYn("Y");
    		}
    		srMasterService.updateSrMasterTargt(srMasterVO);
	    	
    	} catch (Exception e) {
			LOGGER.error(e.getMessage());
    	}
    	
    	mav.addObject("resultList", srMasterList);
    	
    	return mav;
    }
    
    /**
     * 서비스 요청 주간, 월간 여부 저장
     * @param SrMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/opm/srm/chkWeekMonthSave.do")
    public ModelAndView chkWeekMonthSave(
            @ModelAttribute("srMasterVO") SrMasterVO srMasterVO,
            Model model)
            throws Exception {
    	String resultMsg    = "";
    	
    	ModelAndView mav = new ModelAndView("jsonView");
    	
    	try {
    		// 주간, 월간 여부 저장
    		srMasterService.updateSrMasterTargt(srMasterVO);
    		if(!srMasterVO.getWeekyReportTargtYn().isEmpty()) {
    			resultMsg = "주간보고 대상 여부 저장을 완료하였습니다.";	
    		} else {
    			resultMsg = "월간보고 대상 여부 저장을 완료하였습니다.";
    		}
    		
    	} catch (Exception e) {
			LOGGER.error(e.getMessage());
			resultMsg = "보고 대상 여부 저장이 실패하였습니다.";
    	}
    	
    	mav.addObject("resultMsg", resultMsg);
    	
    	return mav;
    }
    
    /**
     * 서비스 요청 삭제
     * @param srMasterVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/opm/srm/deleteSrMaster.do")
    public String deleteSrMaster(
            @ModelAttribute("srMasterVO") SrMasterVO srMasterVO,
            ModelMap model)
            throws Exception {
    	String resultMsg    = "";
        
    	srMasterService.deleteSrMaster(srMasterVO);
    	resultMsg = egovMessageSource.getMessage("success.common.delete");
        
    	model.addAttribute("resultMsg", resultMsg);
        return "forward:/opm/srm/SrMasterMngList.do";
    }

	/**
     * 서비스 요청 멀티 삭제한다.
     * @param srMasterVO
     * @param status
     * @return
     * @throws Exception
     */
    @RequestMapping("/opm/srm/deleteSrMasterList.do")
    public String deleteSrMasterList(
            @ModelAttribute("srMasterVO") SrMasterVO srMasterVO,
            ModelMap model)
            throws Exception {
    	
    	// 결과 메시지
        String resultMsg    = "";
        String [] delSr = srMasterVO.getCheckedValueForDel().split(",");
        
		if (delSr == null || (delSr.length == 0)){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete");
		}else{
			srMasterService.deleteSrMasterList(srMasterVO);
			resultMsg = egovMessageSource.getMessage("success.common.delete");
		}
		model.addAttribute("resultMsg", resultMsg);
		
        return "forward:/opm/srm/SrMasterMngList.do";
    }
    
    /**
	 * SrMaster Excel 
	 * @param SrMasterVO - 다운로드할 정보가 담긴 SrMasterVO
	 * @exception Exception
	 */
	@RequestMapping("/opm/srm/downloadExcel.do")
	public ModelAndView downloadExcel(HttpServletResponse response
			, HttpServletRequest request
			, @ModelAttribute("srMasterVO") SrMasterVO srMasterVO)
			throws Exception {

			/** EgovPropertyService.sample */
			srMasterVO.setPageUnit(propertiesService.getInt("pageUnit"));
			srMasterVO.setPageSize(propertiesService.getInt("pageSize"));
		
			/** paging */
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(srMasterVO.getPageIndex());
			paginationInfo.setPageSize(srMasterVO.getPageSize());
	
			srMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			srMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
		
			ModelAndView mav = new ModelAndView("excelView");
		    Map<String, Object> dataMap = new HashMap<String, Object>();
		    
		    int tot = srMasterService.selectSrMasterListTotCnt(srMasterVO);
		    srMasterVO.setRecordCountPerPage(tot);
		    List<?> srMasterEgovMapList = srMasterService.selectSrMasterEgovMap(srMasterVO);
		    
		    SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd'_'HH:mm:ss");
		    Date date = new Date(System.currentTimeMillis());
		    String dateStr = formatter.format(date);
		  
		    String filename = "SrMaster_" + dateStr;
		    String[] columnTitle = {"요청관리번호", "업무시스템명", "요청중분류", "요청제목", "요청부서", "요청자", "요청일", "담당자","처리일","진행상태"};
		    String[] columnName = {"srMngtNo", "bizSytmNm", "srTypeClasNm", "reqTitle", "positDeptNm", "reqChrgrNm", "reqDate", "dlngChrgrNm", "dlngDate", "srProgStat"};
		  
		    dataMap.put("columnTitle", columnTitle);
		    dataMap.put("columnName", columnName);
		    dataMap.put("sheetNm", "srMaster");    
		    dataMap.put("list", srMasterEgovMapList);
		    
		    mav.addObject("dataMap", dataMap);
		    mav.addObject("filename", filename);
		    
		    return mav;
	}
}
