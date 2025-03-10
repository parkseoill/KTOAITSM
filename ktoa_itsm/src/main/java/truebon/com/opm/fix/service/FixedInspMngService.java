package truebon.com.opm.fix.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import truebon.com.opm.fix.model.FixedInspMngVO;

public interface FixedInspMngService {

	/**
	 * tb_sw_asset 목록을 조회한다.
	 * 
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_sw_asset 목록
	 * @exception Exception
	 */
	List<FixedInspMngVO> selectFixedInspList(FixedInspMngVO searchVO) throws Exception;

	/**
	 * 정기점검 목록의 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 정기점검 목록의 총 갯수
	 * @exception Exception
	 */
	int selectFixedInspListTotCnt(FixedInspMngVO searchVO) throws Exception;

	/**
	 * 정기점검을 멀티 삭제 한다.
	 * 
	 * @param checkedLcnMngForDel
	 */
	void deleteFixedInspList(FixedInspMngVO searchVO) throws Exception;

	/**
	 * 정기점검을 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 FixedInspMngVO
	 * @return 조회한 정기점검
	 * @exception Exception
	 */
	FixedInspMngVO selectFixedInsp(FixedInspMngVO vo) throws Exception;

	/**
	 * 정기점검을 수정한다.
	 * 
	 * @param vo - 수정할 정보가 담긴 FixedInspMngVO
	 * @return void형
	 * @exception Exception
	 */
	void updateFixedInsp(FixedInspMngVO vo) throws Exception;

	/**
	 * 정기점검을 삭제한다.
	 * 
	 * @param vo - 삭제할 정보가 담긴 FixedInspMngVO
	 * @return void형
	 * @exception Exception
	 */
	void deleteFixedInsp(FixedInspMngVO vo) throws Exception;

	/**
	 * 정기점검을 멀티 수정 한다.
	 * 
	 * @param checkedLcnMngForDel
	 */
	void fixedInspMltplUpdt(FixedInspMngVO searchVO) throws Exception;

	/**
	 * 회사목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 회사 목록
	 * @exception Exception
	 */
	List<FixedInspMngVO> selectCmpnyList(FixedInspMngVO searchVO) throws Exception;
	
	/**
	 * 전체 회사목록을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 회사 목록
	 * @exception Exception
	 */
	List<FixedInspMngVO> selectAllCmpnyList(FixedInspMngVO searchVO) throws Exception;

	/**
	 * tb_sw_asset 현황을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_sw_asset 목록
	 * @exception Exception
	 */
	List<Map<String, Object>> selectFixedInspStus(FixedInspMngVO searchVO) throws Exception;

	/**
	 * 정기점검 계획을 생성한다
	 * 
	 * @param searchVO - 생성할 정보가 담긴 VO
	 * @exception Exception
	 */
	void insertFixedInspStus(FixedInspMngVO searchVO) throws Exception;
	
    /**
  	 * 정기점검 목록을 조회한다. (엑셀 다운로드)
  	 * @param searchVO - 조회할 정보가 담긴 VO
  	 * @return EgovMap 목록
  	 * @exception Exception
  	 */
    List<EgovMap> selectFixedInspMngEgovMap(FixedInspMngVO searchVO) throws Exception;
}
