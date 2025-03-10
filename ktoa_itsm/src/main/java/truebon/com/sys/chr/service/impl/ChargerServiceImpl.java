package truebon.com.sys.chr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import truebon.com.sys.chr.service.ChargerService;
import truebon.com.sys.chr.model.ChargerVO;
import truebon.com.sys.chr.model.Charger;
import truebon.com.sys.chr.dao.ChargerDAO;
/**
 * 담당자를 위한 서비스 구현 클래스
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

@Service("ChargerService")
public class ChargerServiceImpl extends EgovAbstractServiceImpl implements
        ChargerService {
        
    private static final Logger LOGGER = LoggerFactory.getLogger(ChargerServiceImpl.class);

    @Resource(name="ChargerDAO")
    private ChargerDAO chargerDAO;
    
    /** ID Generation */
	@Resource(name="chargerIdGnrService")
	private EgovIdGnrService idgenService;

	/**
	 * tb_charger을 등록한다.
	 * @param vo - 등록할 정보가 담긴 Charger
	 * @exception Exception
	 */
    public void insertCharger(Charger vo) throws Exception {
    	/** ID Generation Service */
    	String uniqId = idgenService.getNextStringId();
		vo.setChrgrNo(uniqId);
		
		// 로그인 객체 선언
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	if(user == null) {
    		vo.setRegstrId("guest");
    		vo.setAmndrId("guest");
    	}else {
    		vo.setRegstrId(user.getId());
    		vo.setAmndrId(user.getId());
    	}
    	chargerDAO.insertCharger(vo);
    }

    /**
	 * tb_charger을 수정한다.
	 * @param vo - 수정할 정보가 담긴 Charger
	 * @return void형
	 * @exception Exception
	 */
    public void updateCharger(Charger vo) throws Exception {
    	// 로그인 객체 선언
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	vo.setAmndrId(user.getId());
    	
    	chargerDAO.updateCharger(vo);
    }

    /**
	 * tb_charger을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 Charger
	 * @return void형 
	 * @exception Exception
	 */
    public void deleteCharger(Charger vo) throws Exception {
    	// 로그인 객체 선언
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	vo.setAmndrId(user.getId());
    	
    	chargerDAO.deleteCharger(vo);
    }

    /**
	 * tb_charger을 조회한다.
	 * @param vo - 조회할 정보가 담긴 ChargerVO
	 * @return 조회한 tb_charger
	 * @exception Exception
	 */
    public ChargerVO selectCharger(ChargerVO vo) throws Exception {
    	ChargerVO resultVO = chargerDAO.selectCharger(vo);
        if (resultVO == null)
            throw processException("info.nodata.msg");
        return resultVO;
    }

    /**
	 * tb_charger 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_charger 목록
	 * @exception Exception
	 */
    public List<ChargerVO> selectChargerList(ChargerVO searchVO) throws Exception {
        return chargerDAO.selectChargerList(searchVO);
    }
    
    /**
	 * tb_charger 협력업체 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_charger 목록
	 * @exception Exception
	 */
    public List<ChargerVO> selectChargerVisitList(ChargerVO searchVO) throws Exception {
        return chargerDAO.selectChargerVisitList(searchVO);
    }
    
    /**
	 * tb_charger 협력업체 목록 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_charger 총 갯수
	 * @exception
	 */
    public int ChargerListPopTotCnt(ChargerVO searchVO) {
		return chargerDAO.ChargerListPopTotCnt(searchVO);
	}

    /**
	 * tb_charger 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_charger 총 갯수
	 * @exception
	 */
    public int selectChargerListTotCnt(ChargerVO searchVO) {
		return chargerDAO.selectChargerListTotCnt(searchVO);
	}
    
    /**
     * 화면에 조회된 담당자 목록 정보를 데이터베이스에서 삭제
     * @param checkedValueForDel
     * @throws Exception
     */
    public void deleteChargerMngList(ChargerVO chargerVO) throws Exception {
    	ChargerVO vo = new ChargerVO();
    	String [] delChrgrNo = chargerVO.getCheckedValueForDel().split(",");
		
		for (int i=0; i<delChrgrNo.length ; i++){
			vo.setChrgrNo(delChrgrNo[i]);
			deleteCharger(vo);
		}
	}
    
    /**
	 * tb_charger 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_charger 목록
	 * @exception Exception
	 */
    public List<ChargerVO> selectNameList(ChargerVO searchVO) throws Exception {
        return chargerDAO.selectNameList(searchVO);
    }
    
    /**
	 * tb_charger ktoa,위탁운영자 목록 을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_charger 목록
	 * @exception Exception
	 */
    public List<ChargerVO> selectChargerSrList(ChargerVO searchVO) throws Exception {
        return chargerDAO.selectChargerSrList(searchVO);
    }
    
    /**
	* tb_charger ktoa,위탁운영자 목록 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return tb_charger 총 갯수
	 * @exception
	 */
    public int ChargerSrListPopTotCnt(ChargerVO searchVO) {
		return chargerDAO.ChargerSrListPopTotCnt(searchVO);
	}
    
    /**
 	* tb_charger 등록하려는 방문자 갯수를 조회한다.
 	 * @param searchVO - 조회할 정보가 담긴 VO
 	 * @return tb_charger 총 갯수
 	 * @exception
 	 */
     public int CheckVisitorsNmCmpny(ChargerVO searchVO) {
 		return chargerDAO.CheckVisitorsNmCmpny(searchVO);
 	}
     
     /**
 	 * tb_charger sr요청부서목록 을 조회한다.
 	 * @param searchVO - 조회할 정보가 담긴 VO
 	 * @return tb_charger 목록
 	 * @exception Exception
 	 */
     public List<ChargerVO> selectSrDeptList(ChargerVO searchVO) throws Exception {
         return chargerDAO.selectSrDeptList(searchVO);
     }
}
