package truebon.com.rsm.wmr.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import truebon.com.sys.chr.model.ChargerVO;
import truebon.com.sys.chr.service.ChargerService;
import truebon.com.opm.biz.model.BizSytmVO;
import truebon.com.opm.biz.service.BizSytmService;
import truebon.com.rsm.wmr.model.BizDetlReportVO;
import truebon.com.rsm.wmr.model.BizReportVO;
import truebon.com.rsm.wmr.service.BizDetlReportService;
import truebon.com.rsm.wmr.service.BizReportService;
import truebon.com.rsm.wmr.service.impl.BizReportServiceImpl;

/**
 * @Class Name : BizReportController.java
 * @Description : BizReport Controller class
 * @Modification Information
 *
 * @author 이태신
 * @since 2022.08.22
 * @version 1.0
 * @see
 * 
 *      Copyright (C) All right reserved.
 */

@Controller
public class BizReportController {

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

	/** 담당자 Service */
	@Resource(name = "ChargerService")
	private ChargerService chargerService;

	/** 업무시스템 Service */
	@Resource(name = "BizSytmService")
	private BizSytmService bizSytmService;

	/** 업무보고서 Service */
	@Resource(name = "BizReportService")
	private BizReportService bizReportService;

	/** 업무상세보고서 Service */
	@Resource(name = "BizDetlReportService")
	private BizDetlReportService bizDetlReportService;

	/** 로그설정 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BizReportServiceImpl.class);

	/** 프로젝트명 => globals.properties */
	private final String ProjectName = EgovProperties.getProperty("Globals.ProjectName");

	/**
	 * 업무보고서 목록화면 (pageing)
	 * 
	 * @param searchVO - 조회할 정보가 담긴 BizReportVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsm/wmr/BizReportList.do")
	public String BizReportList(@ModelAttribute("searchVO") BizReportVO searchVO, ModelMap model) throws Exception {
		String bizReportKindCd = searchVO.getBizReportKindCd() == null ? "01" : searchVO.getBizReportKindCd();
		String bizReportKindNm = "주간보고서";

		// 공통코드(업무보고서 종류)
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setComnCdId("ISM015");
		List<CmmnDetailCode> bizReportKindCdList = cmmUseService.selectCmmCodeDetail(vo);

		// DB 저장된 업무보고서 종류 명칭
		if (!bizReportKindCdList.isEmpty()) {
			for (CmmnDetailCode cmmnDetailCode : bizReportKindCdList) {
				if (cmmnDetailCode.getComnCd().equals(bizReportKindCd)) {
					bizReportKindNm = cmmnDetailCode.getComnCdNm();
				}
			}
		}
		searchVO.setBizReportKindCd(bizReportKindCd);
		searchVO.setBizReportKindNm(bizReportKindNm);

		// 성명 목록 불러오기
		ChargerVO chargerVO = new ChargerVO();
		List<ChargerVO> nameList = chargerService.selectNameList(chargerVO);

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
		List<BizReportVO> bizReportList = bizReportService.selectBizReportList(searchVO);
		model.addAttribute("resultList", bizReportList);
		model.addAttribute("searchVO", searchVO);

		int totCnt = bizReportService.selectBizReportListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		model.addAttribute("nameList", nameList);

		return "/truebon/com/rsm/wmr/BizReportList";
	}

	/**
	 * 업무보고서 등록화면
	 * 
	 * @param bizReportVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rsm/wmr/BizReportRegist.do")
	public String BizReportRegist(@ModelAttribute("bizReportVO") BizReportVO bizReportVO, Model model,
			@ModelAttribute("bizDetlReportVO") BizDetlReportVO bizDetlReportVO) throws Exception {
		String bizReportNm = "";
		CmmnDetailCode cmmnDetailCode = new CmmnDetailCode();

		LocalDateTime currDateTime = LocalDateTime.now();
		String formatedCurrDateTime = currDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String strYear = formatedCurrDateTime.substring(0, 4);
		String bizReportKindCd = bizReportVO.getBizReportKindCd() == null ? "01" : bizReportVO.getBizReportKindCd();
		String bizReportKindNm = "주간보고서";

		// 로그인 정보
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		// 공통코드(공통 프로젝트 명)
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setComnCdId("ISM016");
		List<?> projectNameList = cmmUseService.selectCmmCodeDetail(vo);

		// 공통코드(업무보고서 종류)
		vo.setComnCdId("ISM015");
		List<CmmnDetailCode> bizReportKindCdList = cmmUseService.selectCmmCodeDetail(vo);

		// DB 저장된 업무보고서 종류 명칭
		if (!bizReportKindCdList.isEmpty()) {
			for (CmmnDetailCode cmmnDetailCode2 : bizReportKindCdList) {
				if (cmmnDetailCode2.getComnCd().equals(bizReportKindCd)) {
					bizReportKindNm = cmmnDetailCode2.getComnCdNm();
				}
			}
		}
		bizReportVO.setBizReportKindNm(bizReportKindNm);

		// DB에 공통 프로젝트명이 있다면 최종 등록된 것으로 사용. 없다면 globals.properties 설정된 프로젝트명으로 사용.
		if (!projectNameList.isEmpty()) {
			cmmnDetailCode = (CmmnDetailCode) projectNameList.get(projectNameList.size() - 1);
			bizReportNm = cmmnDetailCode.getComnCdNm() + " " + strYear + " " + bizReportKindNm + " ("
					+ formatedCurrDateTime + ", " + user.getName() + ")";
		} else {
			bizReportNm = ProjectName + " " + strYear + " " + bizReportKindNm + " (" + formatedCurrDateTime + ", "
					+ user.getName() + ")";
		}

		// 보고서 제목 등록
		bizReportVO.setBizReportNm(bizReportNm);
		// 담당자(작성자) 코드
		bizReportVO.setCreatChrgrNo(user.getChrgrNo());
		// 담당자(작성자) 명
		bizReportVO.setCreatChrgrNm(user.getName());
		// 담당자(작성자) 코드
		bizDetlReportVO.setBizDlngChrgrNo(user.getChrgrNo());
		// 주간/월간 구분명
		bizDetlReportVO.setBizReportKindCd(bizReportKindCd);
		// 업무보고서 최근 정보
		if (bizDetlReportService.RegBizDetlReport(bizDetlReportVO) != null) {
			bizDetlReportVO = bizDetlReportService.RegBizDetlReport(bizDetlReportVO);
			// 업무상세보고서 목록 정보
			bizDetlReportVO.setSearchCondition("1");
			List<BizDetlReportVO> BizDetlReportList = bizDetlReportService.selectBizDetlReportList(bizDetlReportVO);
			model.addAttribute("BizDetlReportList", BizDetlReportList);
		}

		// 업무시스템목록
		BizSytmVO bizSytmVO = new BizSytmVO();
		bizSytmVO.setBizSytmCdLvl("2");
		List<BizSytmVO> bizSytmList = bizSytmService.selectBizSytmNmList(bizSytmVO);

		model.addAttribute("bizSytmList", bizSytmList);

		return "/truebon/com/rsm/wmr/BizReportRegist";
	}

	/**
	 * 업무보고서 등록
	 * 
	 * @param bizReportVO
	 * @param bindingResult
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rsm/wmr/inseartBizReportRegist.do")
	public String inseartChargerMngRegist(@ModelAttribute("bizReportVO") BizReportVO bizReportVO,
			BindingResult bindingResult, Model model) throws Exception {
		String sLocationUrl = "";
		String resultMsg = "";
		String resultCd = "";

		// 유효성 검사
		beanValidator.validate(bizReportVO, bindingResult);
		if (bindingResult.hasErrors()) {
			sLocationUrl = "/truebon/com/rsm/wmr/BizReportRegist";
			return sLocationUrl;
		} else {
			try {
				resultCd = bizReportService.insertBizReport(bizReportVO);

				if ("1".equals(resultCd)) {
					resultMsg = egovMessageSource.getMessage("success.common.insert");
					sLocationUrl = "forward:/rsm/wmr/BizReportList.do";
				} else {
					resultMsg = "저장에 실패하였습니다.";
					sLocationUrl = "/truebon/com/rsm/wmr/BizReportRegist";
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				resultMsg = "저장에 실패하였습니다.";
				sLocationUrl = "/truebon/com/rsm/wmr/BizReportRegist";
			}
		}

		model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
	}

	/**
	 * 업무보고서 수정화면
	 * 
	 * @param bizReportVO
	 * @param bizDetlReportVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rsm/wmr/BizReportUpdt.do")
	public String BizReportUpdt(@ModelAttribute("bizReportVO") BizReportVO bizReportVO,
			@ModelAttribute("bizDetlReportVO") BizDetlReportVO bizDetlReportVO, Model model) throws Exception {
		// 업무보고서 정보
		bizReportVO = bizReportService.selectBizReport(bizReportVO);
		// 업무상세보고서 목록 정보
		List<BizDetlReportVO> bizDetlReportList = bizDetlReportService.selectBizDetlReportList(bizDetlReportVO);
		// 업무시스템목록
		BizSytmVO bizSytmVO = new BizSytmVO();
		bizSytmVO.setBizSytmCdLvl("2");
		List<BizSytmVO> bizSytmList = bizSytmService.selectBizSytmNmList(bizSytmVO);

		model.addAttribute("bizReportVO", bizReportVO);
		model.addAttribute("bizDetlReportList", bizDetlReportList);
		model.addAttribute("bizSytmList", bizSytmList);

		return "/truebon/com/rsm/wmr/BizReportUpdt";
	}

	/**
	 * 업무보고서 수정
	 * 
	 * @param chargerVO
	 * @param searchVO
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rsm/wmr/updateBizReportUpdt.do")
	public String updateBizReportUpdt(@ModelAttribute("bizReportVO") BizReportVO bizReportVO,
			BindingResult bindingResult, ModelMap model) throws Exception {
		String sLocationUrl = "";
		String resultMsg = "";

		// 유효성 검사
		beanValidator.validate(bizReportVO, bindingResult);
		if (bindingResult.hasErrors()) {
			sLocationUrl = "/truebon/com/rsm/wmr/BizReportUpdt";
			return sLocationUrl;
		} else {
			try {
				bizReportService.updateBizReport(bizReportVO);
				resultMsg = egovMessageSource.getMessage("success.common.update");
				sLocationUrl = "forward:/rsm/wmr/BizReportList.do";
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				resultMsg = "저장에 실패하였습니다.";
				sLocationUrl = "/truebon/com/rsm/wmr/BizReportUpdt";
			}
		}

		model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
	}

	/**
	 * 업무보고서 멀티 삭제한다.
	 * 
	 * @param searchVO
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/rsm/wmr/deleteBizReportList.do")
	public String deleteChargerMngList(@ModelAttribute("searchVO") BizReportVO searchVO, ModelMap model)
			throws Exception {

		// 결과 메시지
		String resultMsg = "";
		String[] delId = searchVO.getCheckedValueForDel().split(",");

		if (delId == null || (delId.length == 0)) {
			resultMsg = egovMessageSource.getMessage("fail.common.delete");
		} else {
			bizReportService.deleteBizReportList(searchVO);
			resultMsg = egovMessageSource.getMessage("success.common.delete");
		}
		model.addAttribute("resultMsg", resultMsg);

		return "forward:/rsm/wmr/BizReportList.do";
	}

	/**
	 * 업무 보고서
	 *
	 * @exception Exception
	 */
	@RequestMapping(value = "/rsm/wmr/RepoBizReportList.do")
	public String RepoBizReportList(@ModelAttribute("bizReportVO") BizReportVO bizReportVO, Model model)
			throws Exception {
		String bizReportNm = "";
		CmmnDetailCode cmmnDetailCode = new CmmnDetailCode();
		ChargerVO chargerVO = new ChargerVO();

		LocalDateTime currDateTime = LocalDateTime.now();
		String formatedCurrDateTime = currDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String strYear = formatedCurrDateTime.substring(0, 4);
		String bizReportKindCd = bizReportVO.getBizReportKindCd() == null ? "01" : bizReportVO.getBizReportKindCd();
		String bizReportKindNm = "주간보고서";

		// 공통코드(공통 프로젝트 명)
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setComnCdId("ISM016");
		List<?> projectNameList = cmmUseService.selectCmmCodeDetail(vo);

		// DB에 공통 프로젝트명이 있다면 최종 등록된 것으로 사용. 없다면 globals.properties 설정된 프로젝트명으로 사용.
		if (!projectNameList.isEmpty()) {
			cmmnDetailCode = (CmmnDetailCode) projectNameList.get(projectNameList.size() - 1);
			bizReportNm = cmmnDetailCode.getComnCdNm() + " " + strYear;
		} else {
			bizReportNm = ProjectName + " " + strYear;
		}

		// 공통코드(업무보고서 종류)
		vo.setComnCdId("ISM015");
		List<CmmnDetailCode> bizReportKindCdList = cmmUseService.selectCmmCodeDetail(vo);

		// DB 저장된 업무보고서 종류 명칭
		if (!bizReportKindCdList.isEmpty()) {
			for (CmmnDetailCode cmmnDetailCode2 : bizReportKindCdList) {
				if (cmmnDetailCode2.getComnCd().equals(bizReportKindCd)) {
					bizReportKindNm = cmmnDetailCode2.getComnCdNm();
				}
			}
		}
		bizReportVO.setBizReportKindCd(bizReportKindCd);
		bizReportVO.setBizReportKindNm(bizReportKindNm);

		chargerVO.setRecordCountPerPage(0);
		chargerVO.setSearchCondition("5");
		chargerVO.setSearchKeyword("PM");
		// PM 조회
		List<ChargerVO> chargerList = chargerService.selectChargerList(chargerVO);

		// PM 담당자가 DB에 있다면...
		if (!chargerList.isEmpty()) {
			// 보고서 제목
			bizReportVO.setBizReportNm(bizReportNm);
			// 보고서 담당자
			bizReportVO.setRepoChrgrNm(chargerList.get(0).getChrgrNm());
			// 보고서 소속
			bizReportVO.setRepoChrgrCmpnyNm(chargerList.get(0).getPositCmpnyNm());
			// 보고서 담당업무
			bizReportVO.setRepoChrgrRoleNm(chargerList.get(0).getChrgrRoleNm());
			// 보고서 작성일자
			bizReportVO.setRepoCurrDate(formatedCurrDateTime);
		} else {
			// 보고서 제목 등록
			bizReportVO.setBizReportNm("담당자관리 메뉴에서 PM(담당자 역할) 존재가 필요합니다.");
			// 보고서 담당자
			bizReportVO.setRepoChrgrNm("");
			// 보고서 소속
			bizReportVO.setRepoChrgrCmpnyNm("");
			// 보고서 담당업무
			bizReportVO.setRepoChrgrRoleNm("");
			// 보고서 작성일자
			bizReportVO.setRepoCurrDate(formatedCurrDateTime);
		}

		return "/truebon/com/rsm/wmr/RepoBizReportList";
	}


		@RequestMapping(value = "/rsm/wmr/YrvacatUsePldc.do")
		public String YrvacatUsePldc() throws Exception {

			return "/truebon/com/rsm/wmr/YrvacatUsePldc";
		}
	
	
		@RequestMapping(value = "/rsm/wmr/YrvacatUseReport.do")
		public String YrvacatUseReport() throws Exception {

			return "/truebon/com/rsm/wmr/YrvacatUseReport";
		}
	

		@RequestMapping(value = "/rsm/wmr/TruebonWeeklyReport.do")
		public String TruebonWeeklyReport() throws Exception {
			
			return "/truebon/com/rsm/wmr/TruebonWeeklyReport";
		}


		@RequestMapping(value = "/rsm/wmr/TruebonMonthReport.do")
		public String TruebonMonthReport() throws Exception {

			return "/truebon/com/rsm/wmr/TruebonMonthReport";
		
	}
}