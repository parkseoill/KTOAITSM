package truebon.com.dlm.wrk.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import truebon.com.dlm.wrk.model.WorkVO;
import truebon.com.dlm.rep.model.ReplaceVO;
import truebon.com.dlm.wrk.model.Work;



@Repository("WorkDAO")
public class WorkDAO extends EgovComAbstractDAO{
    
	
    /**
    * tb_work를 조회한다.
    * @param searchMap - 조회할 정보가 담긴 Map
    * @return tb_work 목록
    * @exception Exception
    */
    public List<WorkVO> selectWorkList(Work searchVO) throws Exception {
        return selectList("WorkMapper.selectWorkList", searchVO);
    }

    public List<WorkVO> selectRmark(Work searchVO) throws Exception {
        return selectList("WorkMapper.selectRmark", searchVO);
    }
    
    
    /**
    * tb_work을 수정한다.
    * @param vo - 수정할 정보가 담긴 WorkVO
    * @return void형
    * @exception Exception
    */
    public void updateWork(Work vo) throws Exception {
        update("WorkMapper.updateWork", vo);
    }

    public void updateResWork(Work vo) throws Exception {
        update("WorkMapper.updateResWork", vo);
    }
    
    
    /**
     * 마감
     */
    public void closing(Work vo) throws Exception {
        update("WorkMapper.closing", vo);
    }      
    public void insertClosing(Work vo) throws Exception {
        insert("WorkMapper.insertClosing", vo);
    }
    
    
    /**
    * OP 일괄 수정
    */
    public void updateOp(Work vo) throws Exception {
    	if(vo.getOpDatechk().equals("1")) {  //1일 체크
    		update("WorkMapper.updateOp1", vo);
    	}
    	if(vo.getOpDatechk().equals("2")) { //2일 체크
    		update("WorkMapper.updateOp2", vo);
    	}
    	if(vo.getOpDatechk().equals("3")) {  //삭제
      		update("WorkMapper.updateOp3", vo);
       	}     
    }
    
    
}