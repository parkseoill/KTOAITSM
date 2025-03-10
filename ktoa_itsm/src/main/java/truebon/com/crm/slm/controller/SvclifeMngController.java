package truebon.com.crm.slm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import truebon.com.crm.hwm.model.HwMngVO;
import truebon.com.crm.hwm.service.HwMngService;
import truebon.com.crm.slm.model.SvclifeMngVO;
import truebon.com.crm.slm.service.SvclifeService;
import truebon.com.crm.swm.model.SwMngVO;
import truebon.com.opm.biz.model.BizSytmVO;
import truebon.com.opm.biz.service.BizSytmService;
import truebon.com.opm.com.service.ComnCdNewService;


@Controller
public class SvclifeMngController {

	
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
	
	/** SvclifeService */
	@Resource(name = "SvclifeService")
	private SvclifeService svcLifeService;
	
	/** HWMng Service */
	@Resource(name = "HwMngService")
	private HwMngService hwMngService;
	
    
    /**
     * 대개체검토관리 목록화면 (paging)
     * @param searchVO - 조회할 정보가 담긴 SvclifeMngVO
     * @exception Exception
     */
	@RequestMapping(value = "/crm/slm/SvclifeMngList.do")
	public String selectSvclifeList(@ModelAttribute("SvclifeMngVO") SvclifeMngVO searchVO, ModelMap model) throws Exception {
				//업무시스템
				BizSytmVO vo1 = new BizSytmVO();
				vo1.setBizSytmCdLvl("2");
				List<?> bizSysmCdList = bizSytmService.selectBizSytmNmList(vo1);
				model.addAttribute("bizSysmCdList", bizSysmCdList);
				
				
				//HW유형
				ComDefaultCodeVO vo2 = new ComDefaultCodeVO();
				vo2.setComnCdId("ITA09");
				vo2.setDetailCondition("2");
				List<CmmnDetailCode> hwTypeCdList = this.cmmUseService.selectCmmCodeNewDetail(vo2);
				model.addAttribute("hwTypeCdList", hwTypeCdList);
				
				
				//자산상태
				if (searchVO.getSearchKeyword3() == null)
				    searchVO.setSearchKeyword3("ITA0302"); 
				
			    ComDefaultCodeVO vo3 = new ComDefaultCodeVO();
			    vo3.setComnCdId("ITA03");
			    List<CmmnDetailCode> assetStatCdList = this.cmmUseService.selectCmmCodeNewDetail(vo3);
			    model.addAttribute("assetStatCdList", assetStatCdList);
			    
			    
			    //가상화 구분
			    if (searchVO.getSearchKeyword5() == null)
				    searchVO.setSearchKeyword5("ITA0101"); 
			    
			    ComDefaultCodeVO vo5 = new ComDefaultCodeVO();
			    vo5.setComnCdId("ITA01");
			    List<CmmnDetailCode> vrtlzDstnctCdList = this.cmmUseService.selectCmmCodeNewDetail(vo5);
			    model.addAttribute("vrtlzDstnctCdList", vrtlzDstnctCdList);
			    
			    
			    /** EgovPropertyService.sample */
			    if(searchVO.getPageUnit()!=10) {			
					searchVO.setPageUnit(searchVO.getPageUnit());
				}else {
					searchVO.setPageUnit(10);
				}
				
				searchVO.setPageSize(propertiesService.getInt("pageSize"));
    
		        /** paging */
				PaginationInfo paginationInfo = new PaginationInfo();
				paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
				paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
				paginationInfo.setPageSize(searchVO.getPageSize());

				searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
				searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
				searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
				
				//총개수
				int totCnt = svcLifeService.selectSvclifeListTotCnt(searchVO);
				paginationInfo.setTotalRecordCount(totCnt);
				model.addAttribute("paginationInfo", paginationInfo);
				
				
			    //목록
				List<SvclifeMngVO> svclifeMngList = svcLifeService.selectSvclifeList(searchVO);
				model.addAttribute("searchVO", searchVO);
				model.addAttribute("svclifeMngList", svclifeMngList);
				
		
		return "/truebon/com/crm/slm/SvclifeMngList";
	}
	
	
	/**
	 * SvclifeMng 상세조회화면 (paging)
	 * 
	 * @param HwMngVO - 조회할 정보가 담긴 HwMngVO
	 * @exception Exception
	 */
	@RequestMapping(value = "/crm/slm/SvclifeHwMngDetail")
	public String HwMngDetail(@ModelAttribute("searchVO") HwMngVO searchVO, ModelMap model) throws Exception {
		// 공통코드NEW(네트워크)
		ComDefaultCodeVO vo1 = new ComDefaultCodeVO();
		vo1.setComnCdId("ITA02");
		List<CmmnDetailCode> ntwrkCdList = cmmUseService.selectCmmCodeNewDetail(vo1);
		model.addAttribute("ntwrkCdList", ntwrkCdList);

		// 공통코드NEW(HW유형)
		ComDefaultCodeVO vo2 = new ComDefaultCodeVO();
		vo2.setComnCdId("ITA09");
		vo2.setDetailCondition("2");
		List<CmmnDetailCode> hwTypeCdList = cmmUseService.selectCmmCodeNewDetail(vo2);
		model.addAttribute("hwTypeCdList", hwTypeCdList);

		// 공통코드NEW(자산상태)
		ComDefaultCodeVO vo3 = new ComDefaultCodeVO();
		vo3.setComnCdId("ITA03");
		List<CmmnDetailCode> assetStatCdList = cmmUseService.selectCmmCodeNewDetail(vo3);
		model.addAttribute("assetStatCdList", assetStatCdList);

		// 공통코드NEW(업무시스템)
		BizSytmVO vo4 = new BizSytmVO();
		vo4.setBizSytmCdLvl("2");
		List<?> bizSysmCdList = bizSytmService.selectBizSytmNmList(vo4);
		model.addAttribute("bizSysmCdList", bizSysmCdList);

		// 공통코드NEW(가상화구분코드)
		ComDefaultCodeVO vo5 = new ComDefaultCodeVO();
		vo5.setComnCdId("ITA01");
		List<CmmnDetailCode> vrtlzDstnctCdList = cmmUseService.selectCmmCodeNewDetail(vo5);
		model.addAttribute("vrtlzDstnctCdList", vrtlzDstnctCdList);

		// 공통코드NEW(HW상세)
		ComDefaultCodeVO vo6 = new ComDefaultCodeVO();
		vo6.setComnCdId("ITA09");
		vo6.setDetailCondition("3");
		List<CmmnDetailCode> hwTypeDetailCdList = cmmUseService.selectCmmCodeNewDetail(vo6);
		model.addAttribute("hwTypeDetailCdList", hwTypeDetailCdList);

		// 공통코드NEW(가상화 유형코드)
		ComDefaultCodeVO vo7 = new ComDefaultCodeVO();
		vo7.setComnCdId("ITA08");
		List<CmmnDetailCode> vrtlzTypeCdList = cmmUseService.selectCmmCodeNewDetail(vo7);
		model.addAttribute("vrtlzTypeCdList", vrtlzTypeCdList);

		// 공통코드NEW(자산소유)
		ComDefaultCodeVO vo8 = new ComDefaultCodeVO();
		vo8.setComnCdId("ITA04");
		List<CmmnDetailCode> assetOwnCd = cmmUseService.selectCmmCodeNewDetail(vo8);
		model.addAttribute("assetOwnCd", assetOwnCd);

		// 공통코드NEW(유지보수계약)
		ComDefaultCodeVO vo10 = new ComDefaultCodeVO();
		vo10.setComnCdId("ITA05");
		List<CmmnDetailCode> maintceCntrCd = cmmUseService.selectCmmCodeNewDetail(vo10);
		model.addAttribute("maintceCntrCd", maintceCntrCd);

		// 공통코드NEW(유지보수점검주기)
		ComDefaultCodeVO vo11 = new ComDefaultCodeVO();
		vo11.setComnCdId("ITA06");
		List<CmmnDetailCode> maintceInspCycleCd = cmmUseService.selectCmmCodeNewDetail(vo11);
		model.addAttribute("maintceInspCycleCd", maintceInspCycleCd);

		HwMngVO result = hwMngService.selectHwMng(searchVO);
		model.addAttribute("result", result);

		      
		return "/truebon/com/crm/slm/SvclifeHwMngDetail";
	}
	
	
	
	     /**
		 * SvclifeMng Excel 다운로드
		 * 
		 * @param SvclifeCodeMngVO - 다운로드할 정보가 담긴 SvclifeMngVO
		 * @exception Exception
		 */
	@RequestMapping("/crm/slm/downloadExcel.do")
	public ModelAndView downloadExcel(HttpServletResponse response, HttpServletRequest request, 
			@ModelAttribute("SvclifeMngVO") SvclifeMngVO svcLifeMngVO) throws Exception {

		
		    /** EgovPropertyService.sample */
	     	svcLifeMngVO.setPageUnit(propertiesService.getInt("pageUnit"));
	    	svcLifeMngVO.setPageSize(propertiesService.getInt("pageSize"));
		
			/** paging */
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(svcLifeMngVO.getPageIndex());
			paginationInfo.setPageSize(svcLifeMngVO.getPageSize());
	
			svcLifeMngVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			svcLifeMngVO.setLastIndex(paginationInfo.getLastRecordIndex());
		
			ModelAndView mav = new ModelAndView("excelView");
		    Map<String, Object> dataMap = new HashMap<String, Object>();
		    
		    int tot = svcLifeService.selectSvclifeListTotCnt(svcLifeMngVO);
		    svcLifeMngVO.setRecordCountPerPage(tot);
		    List<?> svcLifeMngEgovMapList = svcLifeService.selectSvclifeEgoMap(svcLifeMngVO);
		    
		    SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd'_'HH:mm:ss");
		    Date date = new Date(System.currentTimeMillis());
		    String dateStr = formatter.format(date);
		  
		    String filename = "SvcLifeMng_" + dateStr;
		    String[] columnTitle = {"HW자산ID", "업무시스템", "HW유형", "제품명", "용도", "입고일", "내용연수(년)", "사용연수(년)","초과(년)", "EOSL", "자산상태"}; 
		    String[] columnName = {"hwAssetId", "bizSytm","hwType", "prodNm", "prodPurp", "entrngDate","textYearCo" ,"useYear", "excessYear", "eoslDate", "assetStat"};
		  
		    dataMap.put("columnTitle", columnTitle);
		    dataMap.put("columnName", columnName);
		    dataMap.put("sheetNm", "SvcLifeMng");    
		    dataMap.put("list", svcLifeMngEgovMapList);
		    
		    mav.addObject("dataMap", dataMap);
		    mav.addObject("filename", filename);
		    
		    return mav;
	}
}
