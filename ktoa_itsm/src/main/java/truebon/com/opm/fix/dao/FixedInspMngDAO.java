package truebon.com.opm.fix.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import truebon.com.opm.fix.model.FixedInspMngVO;

@Repository("FixedInspMngDAO")
public class FixedInspMngDAO extends EgovComAbstractDAO {

	/**
	 * tb_sw_asset 목록을 조회한다.
	 * 
	 * @param searchMap - 조회할 정보가 담긴 Map
	 * @return tb_sw_asset 목록
	 * @exception Exception
	 */
	public List<FixedInspMngVO> selectFixedInspList(FixedInspMngVO searchVO) throws Exception {
		return selectList("FixedInspMapper.selectFixedInspList", searchVO);
	}

	/**
	 * 정기점검 목록의 총 갯수를 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 정기점검 목록의 총 갯수
	 * @exception Exception
	 */
	public int selectFixedInspListTotCnt(FixedInspMngVO searchVO) throws Exception {
		return (Integer) selectOne("FixedInspMapper.selectFixedInspListTotCnt", searchVO);
	}

	/**
	 * 정기점검을 삭제한다.
	 * 
	 * @param vo - 삭제할 정보가 담긴 FixedInspMngVO
	 * @return void형
	 * @exception Exception
	 */
	public void deleteFixedInsp(FixedInspMngVO vo) throws Exception {
		delete("FixedInspMapper.deleteFixedInsp", vo);
	}

	/**
	 * 정기점검을 조회한다.
	 * 
	 * @param vo - 조회할 정보가 담긴 FixedInspMngVO
	 * @return 조회한 정기점검
	 * @exception Exception
	 */
	public FixedInspMngVO selectFixedInsp(FixedInspMngVO vo) throws Exception {
		return (FixedInspMngVO) selectOne("FixedInspMapper.selectFixedInsp", vo);
	}

	/**
	 * 정기점검을 수정한다.
	 * 
	 * @param vo - 삭제할 정보가 담긴 FixedInspMngVO
	 * @return void형
	 * @exception Exception
	 */

	public void updateFixedInsp(FixedInspMngVO vo) throws Exception {
		update("FixedInspMapper.updateFixedInsp", vo);
	}

	public List<FixedInspMngVO> selectCmpnyList(FixedInspMngVO searchVO) {
		return selectList("FixedInspMapper.selectCmpnyList", searchVO);
	}
	
	public List<FixedInspMngVO> selectAllCmpnyList(FixedInspMngVO searchVO) {
		return selectList("FixedInspMapper.selectAllCmpnyList", searchVO);
	}

	/**
	 * tb_sw_asset 현황을 조회한다.
	 * 
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_sw_asset 목록
	 * @exception Exception
	 */
	public List<Map<String, Object>> selectFixedInspStus(FixedInspMngVO searchVO) throws Exception {
		return selectList("FixedInspMapper.selectFixedInspStus", searchVO);
	}

	/**
	 * 정기점검 계획을 생성한다
	 * 
	 * @param searchVO - 생성할 정보가 담긴 VO
	 * @exception Exception
	 */
	public void insertFixedInspStus(FixedInspMngVO searchVO) {
		selectList("FixedInspMapper.insertFixedInspStus", searchVO);

	}
	
    /**
  	 * 정기점검 목록을 조회한다. (엑셀 다운로드)
  	 * @param searchVO - 조회할 정보가 담긴 VO
  	 * @return EgovMap 목록
  	 * @exception Exception
  	 */
	public List<EgovMap> selectFixedInspMngEgovMap(FixedInspMngVO searchVO) throws Exception{
    	return selectList("FixedInspMapper.selectFixedInspMngEgovMap", searchVO);
    }
}
