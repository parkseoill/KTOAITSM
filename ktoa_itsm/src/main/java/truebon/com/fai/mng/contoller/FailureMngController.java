package truebon.com.fai.mng.contoller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import truebon.com.crm.hwm.model.HwMngVO;
import truebon.com.crm.hwm.service.HwMngService;
import truebon.com.crm.swm.model.SwMngVO;
import truebon.com.crm.swm.service.SwMngService;
import truebon.com.opm.biz.model.BizSytmVO;
import truebon.com.opm.biz.service.BizSytmService;
import truebon.com.opm.com.model.ComnCdNewVO;
import truebon.com.opm.com.service.ComnCdNewService;
import truebon.com.fai.mng.model.FailureMngVO;
import truebon.com.fai.mng.service.FailureMngService;

@Controller

public class FailureMngController {

	@Autowired
	private DefaultBeanValidator beanValidator;

	/** 공통코드 Service */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;

	/** 새공통코드 Service */
	@Resource(name = "ComnCdNewService")
	private ComnCdNewService comnCdNewService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** FailureMng Service */
	@Resource(name = "FailureMngService")
	private FailureMngService failureMngService;

	/** HWMng Service */
	@Resource(name = "HwMngService")
	private HwMngService hwMngService;

	/** SW자산 목록관리 Service */
	@Resource(name = "SwMngService")
	private SwMngService swMngService;

	/** 업무시스템 Service */
	@Resource(name = "BizSytmService")
	private BizSytmService bizSytmService;

	/**
	 * 장애 목록화면 (paging)
	 * 
	 * @param searchVO - 조회할 정보가 담긴 FailureMngVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/fai/mng/FailureMngList.do")
	public String FailureMngList(@ModelAttribute("searchVO") FailureMngVO searchVO, ModelMap model) throws Exception {
		
		// 초기값 설정
		LocalDate currentdate = LocalDate.now();
		LocalDate currentyear = LocalDate.of(LocalDate.now().getYear(), 01, 01);
		if (searchVO.getStartDate() == "" && searchVO.getEndDate() == "") {
			searchVO.setEndDate(currentdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			searchVO.setStartDate(currentyear.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		
		// 공통코드NEW(장애구분)
		ComDefaultCodeVO vo1 = new ComDefaultCodeVO();
		vo1.setComnCdId("FLR01");
		List<CmmnDetailCode> failrDstinct = cmmUseService.selectCmmCodeNewDetail(vo1);
		model.addAttribute("failrDstinct", failrDstinct);

		// 공통코드NEW(장애종류)
		ComDefaultCodeVO vo2 = new ComDefaultCodeVO();
		vo2.setComnCdId("FLR02");
		List<CmmnDetailCode> failrKind = cmmUseService.selectCmmCodeNewDetail(vo2);
		model.addAttribute("failrKind", failrKind);

		// 공통코드NEW(장애영향도)
		ComDefaultCodeVO vo3 = new ComDefaultCodeVO();
		vo3.setComnCdId("FLR03");
		List<CmmnDetailCode> failrInfldg = cmmUseService.selectCmmCodeNewDetail(vo3);
		model.addAttribute("failrInfldg", failrInfldg);

		// 공통코드NEW(장애긴급도)
		ComDefaultCodeVO vo4 = new ComDefaultCodeVO();
		vo4.setComnCdId("FLR04");
		List<CmmnDetailCode> failrEmgdgr = cmmUseService.selectCmmCodeNewDetail(vo4);
		model.addAttribute("failrEmgdgr", failrEmgdgr);

		// 공통코드NEW(장애등급)
		ComDefaultCodeVO vo5 = new ComDefaultCodeVO();
		vo5.setComnCdId("FLR05");
		List<CmmnDetailCode> failrGrd = cmmUseService.selectCmmCodeNewDetail(vo5);
		model.addAttribute("failrGrd", failrGrd);

		// 공통코드NEW(장애영향도평가)
		ComDefaultCodeVO vo6 = new ComDefaultCodeVO();
		vo6.setComnCdId("FLR06");
		List<CmmnDetailCode> failrInfldgEvltn = cmmUseService.selectCmmCodeNewDetail(vo6);
		model.addAttribute("failrInfldgEvltn", failrInfldgEvltn);

		// 공통코드NEW(업무시스템)
		BizSytmVO vo7 = new BizSytmVO();
		vo7.setBizSytmCdLvl("2");
		List<?> bizSysmCdList = bizSytmService.selectBizSytmNmList(vo7);
		model.addAttribute("bizSysmCdList", bizSysmCdList);

		/** EgovPropertyService.sample */
		if(searchVO.getPageUnit()!=10) {			
			searchVO.setPageUnit(searchVO.getPageUnit());
		}else {
			searchVO.setPageUnit(10);
		}
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// 페이징
		int totCnt = failureMngService.selectFailureListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		
		// 목록
		List<FailureMngVO> failureList = failureMngService.selectFailureMngList(searchVO);
		model.addAttribute("searchVO", searchVO);

		for (int i = 0; i < failureList.size(); i++) {
			if (failureList.get(i).getFailrCmpltDt().isEmpty()) {
				failureList.get(i).setFailrPeriodMngmntYn("");
			} else {
				if (Integer.parseInt(failureList.get(i).getFailrMngmntPeriod()) >= Integer
						.parseInt(failureList.get(i).getFailrTime())) {
					failureList.get(i).setFailrPeriodMngmntYn("Y");
				} else {
					failureList.get(i).setFailrPeriodMngmntYn("N");
				}
			}
		}

		model.addAttribute("failureList", failureList);
		model.addAttribute("paginationInfo", paginationInfo);

		return "/truebon/com/fai/mng/FailureMngList";
	}

	/**
	 * 장애를 멀티 삭제한다.
	 * 
	 * @param failureMngVO
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fai/mng/deleteFailureList.do")
	public String deleteFailureList(@ModelAttribute("failureMngVO") FailureMngVO failureMngVO, ModelMap model)
			throws Exception {
		return "forward:/fai/mng/FailureMngList.do";
	}

	/**
	 * 장애관리 수정화면 (paging)
	 * 
	 * @param FailureMngVO - 조회할 정보가 담긴 searchVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/fai/mng/FailureMngDetail.do")
	public String FailureMngDetail(@ModelAttribute("searchVO") FailureMngVO searchVO, ModelMap model) 
			throws Exception {

		// 공통코드NEW(장애구분)
		ComDefaultCodeVO vo1 = new ComDefaultCodeVO();
		vo1.setComnCdId("FLR01");
		List<CmmnDetailCode> failrDstnct = cmmUseService.selectCmmCodeNewDetail(vo1);
		model.addAttribute("failrDstnct", failrDstnct);

		// 공통코드NEW(장애종류)
		ComDefaultCodeVO vo2 = new ComDefaultCodeVO();
		vo2.setComnCdId("FLR02");
		List<CmmnDetailCode> failrKind = cmmUseService.selectCmmCodeNewDetail(vo2);
		model.addAttribute("failrKind", failrKind);

		// 공통코드NEW(장애영향도)
		ComDefaultCodeVO vo3 = new ComDefaultCodeVO();
		vo3.setComnCdId("FLR03");
		List<CmmnDetailCode> failrInfldg = cmmUseService.selectCmmCodeNewDetail(vo3);
		model.addAttribute("failrInfldg", failrInfldg);

		// 공통코드NEW(장애긴급도)
		ComDefaultCodeVO vo4 = new ComDefaultCodeVO();
		vo4.setComnCdId("FLR04");
		List<CmmnDetailCode> failrEmgdgr = cmmUseService.selectCmmCodeNewDetail(vo4);
		model.addAttribute("failrEmgdgr", failrEmgdgr);

		// 공통코드NEW(장애등급)
		ComDefaultCodeVO vo5 = new ComDefaultCodeVO();
		vo5.setComnCdId("FLR05");
		List<CmmnDetailCode> failrGrd = cmmUseService.selectCmmCodeNewDetail(vo5);
		model.addAttribute("failrGrd", failrGrd);

		// 공통코드NEW(장애영향도평가)
		ComDefaultCodeVO vo6 = new ComDefaultCodeVO();
		vo6.setComnCdId("FLR06");
		List<CmmnDetailCode> failrInfldgEvltn = cmmUseService.selectCmmCodeNewDetail(vo6);
		model.addAttribute("failrInfldgEvltn", failrInfldgEvltn);

		// 공통코드NEW(업무시스템)
		BizSytmVO vo7 = new BizSytmVO();
		vo7.setBizSytmCdLvl("2");
		List<?> bizSysmCdList = bizSytmService.selectBizSytmNmList(vo7);
		model.addAttribute("bizSysmCdList", bizSysmCdList);

		FailureMngVO result = failureMngService.selectFailureMng(searchVO);
		model.addAttribute("result", result);

		return "/truebon/com/fai/mng/FailureMngUpdt";
	}

	/**
	 * 장애관리 수정
	 * 
	 * @param failureMngVO
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fai/mng/FailureMngUpdt.do")
	public String FailureMngUpdt(@ModelAttribute("failureMngVO") FailureMngVO failureMngVO, ModelMap model,
			BindingResult bindingResult) throws Exception {
		String sLocationUrl = "";
		String resultMsg = "";

		// 유효성 검사
		beanValidator.validate(failureMngVO, bindingResult);
		if (bindingResult.hasErrors()) {
			sLocationUrl = "/truebon/com/fai/mng/FailureMngUpdt";
			return sLocationUrl;
		} else {
			if (failureMngVO.getHwAssetId().contains("SW")) {
				failureMngVO.setSwAssetId(failureMngVO.getHwAssetId());
				failureMngVO.setHwAssetId("");
			}
			failureMngService.updateFailureMng(failureMngVO);
			resultMsg = egovMessageSource.getMessage("success.common.update");
			sLocationUrl = "forward:/fai/mng/FailureMngList.do";
		}
		model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
	}

	/**
	 * 장애관리 등록화면 (paging)
	 * 
	 * @param FailureMngVO - 조회할 정보가 담긴 FailureMngVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/fai/mng/FailureMngReigstDetail.do")
	public String FailureMngReigstDetail(@ModelAttribute("searchVO") FailureMngVO searchVO, ModelMap model)
			throws Exception {

		// 공통코드NEW(장애구분)
		ComDefaultCodeVO vo1 = new ComDefaultCodeVO();
		vo1.setComnCdId("FLR01");
		List<CmmnDetailCode> failrDstnct = cmmUseService.selectCmmCodeNewDetail(vo1);
		model.addAttribute("failrDstnct", failrDstnct);

		// 공통코드NEW(장애종류)
		ComDefaultCodeVO vo2 = new ComDefaultCodeVO();
		vo2.setComnCdId("FLR02");
		List<CmmnDetailCode> failrKind = cmmUseService.selectCmmCodeNewDetail(vo2);
		model.addAttribute("failrKind", failrKind);

		// 공통코드NEW(장애영향도)
		ComDefaultCodeVO vo3 = new ComDefaultCodeVO();
		vo3.setComnCdId("FLR03");
		List<CmmnDetailCode> failrInfldg = cmmUseService.selectCmmCodeNewDetail(vo3);
		model.addAttribute("failrInfldg", failrInfldg);

		// 공통코드NEW(장애긴급도)
		ComDefaultCodeVO vo4 = new ComDefaultCodeVO();
		vo4.setComnCdId("FLR04");
		List<CmmnDetailCode> failrEmgdgr = cmmUseService.selectCmmCodeNewDetail(vo4);
		model.addAttribute("failrEmgdgr", failrEmgdgr);

		// 공통코드NEW(장애등급)
		ComDefaultCodeVO vo5 = new ComDefaultCodeVO();
		vo5.setComnCdId("FLR05");
		List<CmmnDetailCode> failrGrd = cmmUseService.selectCmmCodeNewDetail(vo5);
		model.addAttribute("failrGrd", failrGrd);

		// 공통코드NEW(장애영향도평가)
		ComDefaultCodeVO vo6 = new ComDefaultCodeVO();
		vo6.setComnCdId("FLR06");
		List<CmmnDetailCode> failrInfldgEvltn = cmmUseService.selectCmmCodeNewDetail(vo6);
		model.addAttribute("failrInfldgEvltn", failrInfldgEvltn);

		// 공통코드NEW(업무시스템)
		BizSytmVO vo7 = new BizSytmVO();
		vo7.setBizSytmCdLvl("2");
		List<?> bizSysmCdList = bizSytmService.selectBizSytmNmList(vo7);
		model.addAttribute("bizSysmCdList", bizSysmCdList);

		model.addAttribute("pageIndex", searchVO.getPageIndex());

		return "/truebon/com/fai/mng/FailureMngRegist";
	}

	/**
	 * 장애관리 등록
	 * 
	 * @param failureMngVO
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fai/mng/FailureMngReigst.do")
	public String FailureMngReigst(@ModelAttribute("failureMngVO") FailureMngVO failureMngVO,
			BindingResult bindingResult, ModelMap model) throws Exception {

		String sLocationUrl = "";
		String resultMsg = "";

		// 유효성 검사
		beanValidator.validate(failureMngVO, bindingResult);
		if (bindingResult.hasErrors()) {
			sLocationUrl = "/truebon/com/fai/mng/FailureMngRegist";
			model.addAttribute(failureMngVO.getFailrMngmntPeriod(), "failrMngmntPeriod");
			return sLocationUrl;
		} else {
			System.out.println("변환전 기간"+failureMngVO.getFailrMngmntPeriod());
			
			// 조치 기한 분으로 변환
			String failrMngmntPeriod = failureMngVO.getFailrMngmntPeriod();
			
			int day = Integer.parseInt(failrMngmntPeriod.substring(0,1));
			int time = Integer.parseInt(failrMngmntPeriod.substring(3,5));
			int min = Integer.parseInt(failrMngmntPeriod.substring(8,10));

			int failrMngmntPeriodCal = day * 1440 + time * 60 + min ;
			failureMngVO.setFailrMngmntPeriod(failrMngmntPeriodCal+"");
			
			System.out.println("기간"+failureMngVO.getFailrMngmntPeriod());
		
			
			if (failureMngVO.getHwAssetId().contains("SW")) {
				failureMngVO.setSwAssetId(failureMngVO.getHwAssetId());
				failureMngVO.setHwAssetId("");
			}
			failureMngVO.setFailrCmpltDt(failureMngVO.getFailrCmpltDt().substring(0, 10)
		   +failureMngVO.getFailrCmpltDt().substring(failureMngVO.getFailrCmpltDt().length()-6, failureMngVO.getFailrCmpltDt().length()));
			failureMngVO.setFailrCogntDt(failureMngVO.getFailrCogntDt().substring(0, 10)
		   +failureMngVO.getFailrCogntDt().substring(failureMngVO.getFailrCogntDt().length()-6, failureMngVO.getFailrCogntDt().length()));
			failureMngVO.setFailrOccurDt(failureMngVO.getFailrOccurDt().substring(0, 10)
		   +failureMngVO.getFailrOccurDt().substring(failureMngVO.getFailrOccurDt().length()-6, failureMngVO.getFailrOccurDt().length()));
			failureMngService.registFailureMng(failureMngVO);
			resultMsg = egovMessageSource.getMessage("success.common.insert");
			sLocationUrl = "forward:/fai/mng/FailureMngList.do";
		}
		model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
	}

	/**
	 * HW 팝업 목록 화면
	 * 
	 * @param searchVO - 조회할 정보가 담긴 HwMngVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/fai/mng/hwAssetPopup.do")
	public String HwMngHwPopup(@ModelAttribute("searchVO") HwMngVO searchVO, ModelMap model) 
			throws Exception {
		
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** paging */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// HW목록

		List<HwMngVO> hwList = hwMngService.selectHwPopUpMngList(searchVO);
		model.addAttribute("resultList", hwList);
		model.addAttribute("searchVO", searchVO);

		int totCnt = hwMngService.selectHwPopUpMngListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "/truebon/com/fai/mng/FailureMngHwPopup";

	}

	/**
	 * SW 팝업 목록 화면
	 * 
	 * @param searchVO - 조회할 정보가 담긴 HwMngVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/fai/mng/swAssetPopup.do")
	public String SwMngSwPopup(@ModelAttribute("searchVO") SwMngVO searchVO, ModelMap model) 
			throws Exception {
		
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** paging */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		ComnCdNewVO vo = new ComnCdNewVO();
		vo.setUpprNewComnCd("ITA10");
		List<?> swTypeList = comnCdNewService.selectComnCdNewList(vo);
		model.addAttribute("swTypeList", swTypeList);

		List<SwMngVO> swAssetList = swMngService.selectSwAssetList(searchVO);
		model.addAttribute("resultList", swAssetList);
		model.addAttribute("searchVO", searchVO);

		int totCnt = swMngService.selectSwAssetListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "/truebon/com/fai/mng/FailureMngSwPopup";

	}

	/**
	* 장애관리 가이드 팝업 화면
	* 
	* @param searchVO - 조회할 정보가 담긴 FailureMngVO
	* @exception Exception
	*/
	@RequestMapping(value = "/fai/mng/failurMngtGuidePopup.do")
	public String failurMngtGuidePopup(ModelMap model) throws Exception {
	
		List<Map<String, Object>> mngmntPeriodList = new ArrayList<Map<String, Object>>();
		mngmntPeriodList = failureMngService.selectMngmntPeriodGuide();
		model.addAttribute("mngmntPeriodList", mngmntPeriodList);
	  
	return "/truebon/com/fai/mng/FailurMngtGuidePopup";
	
	}

	/**
	 * 장애 등급 (영향도, 긴급도 선택에 따른 조회)
	 * 
	 * @param failureMngVO
	 * @param comnCdNewVO
	 * @param bindingResult
	 * @param searchVO
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/fai/mng/FailrGrd.do")
	@ResponseBody
	public ModelAndView FailrGrd(@ModelAttribute("FailureMngVO") FailureMngVO failureMngVO, BindingResult bindingResult,
			Model model, @RequestParam("failrInfldg") String failrInfldg,
			@RequestParam("failrEmgdgr") String failrEmgdgr, @RequestParam("bizSytm") String bizSytm) throws Exception {

		ModelAndView mav = new ModelAndView("jsonView");

		failureMngVO.setFailrInfldg(failrInfldg);
		failureMngVO.setFailrEmgdgr(failrEmgdgr);
		failureMngVO.setBizSytm(bizSytm);

		FailureMngVO failrGrd = failureMngService.selectFailureGrd(failureMngVO);
		mav.addObject("failrGrd", failrGrd);

		return mav;
	}

	/**
	 * failureMng Excel
	 * 
	 * @param failureMngVO - 다운로드할 정보가 담긴 failureMngVO
	 * @exception Exception
	 */
	@RequestMapping("/fai/mng/downloadExcel.do")
	public ModelAndView downloadExcel(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("failureMngVO") FailureMngVO failureMngVO) throws Exception {

		/** EgovPropertyService.sample */
		failureMngVO.setPageUnit(propertiesService.getInt("pageUnit"));
		failureMngVO.setPageSize(propertiesService.getInt("pageSize"));

		/** paging */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(failureMngVO.getPageIndex());
		paginationInfo.setPageSize(failureMngVO.getPageSize());

		failureMngVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		failureMngVO.setLastIndex(paginationInfo.getLastRecordIndex());

		ModelAndView mav = new ModelAndView("excelView");
		Map<String, Object> dataMap = new HashMap<String, Object>();

		int tot = failureMngService.selectFailureListTotCnt(failureMngVO);
		failureMngVO.setRecordCountPerPage(tot);
		List<?> failureEgovMapList = failureMngService.selectFailureEgovMap(failureMngVO);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'_'HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String dateStr = formatter.format(date);

		String filename = "Failure_" + dateStr;
		String[] columnTitle = { "장애관리ID", "장애구분", "장애종류", "업무시스템", "HW자산ID", "HW자산명", "HW자산용도", "SW자산ID", "SW자산명", "SW자산용도", "장애영향도",
				"장애긴급도", "장애등급", "장애조치기한", "서비스이상여부", "장애영향도평가", "MNP사업자장애여부", "장애제목", "장애발생일시", "장애인지일시", "장애완료일시",
				"장애시간", "장애현상", "장애원인", "장애조치사항", "장애개선사항", "조치자명" };
		String[] columnName = { "failrMngtId", "failrDstnct", "failrKind", "bizSytm", "hwAssetId", "hwAssetNm", "hwAssetPurp",
				"swAssetId", "swAssetNm", "swAssetPurp", "failrInfldg", "failrEmgdgr", "failrGrd", "failrMngmntPeriod", "svcAbnoYn",
				"failrInfldgEvltn", "mnpBizmanFailrYn", "failrTitle", "failrOccurDt", "failrCogntDt", "failrCmpltDt",
				"failrTime", "failrHpng", "failrCause", "failrMngmntMater", "failrBtrmntMater", "mangrNm" };

		dataMap.put("columnTitle", columnTitle);
		dataMap.put("columnName", columnName);
		dataMap.put("sheetNm", "failure");
		dataMap.put("list", failureEgovMapList);

		mav.addObject("dataMap", dataMap);
		mav.addObject("filename", filename);

		return mav;
	}

}
