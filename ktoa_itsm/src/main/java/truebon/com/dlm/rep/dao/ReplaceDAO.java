package truebon.com.dlm.rep.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import truebon.com.dlm.rep.model.ReplaceVO;

@Repository("ReplaceDAO")
public class ReplaceDAO extends EgovComAbstractDAO {
   
    public List<ReplaceVO> selectReplaceList(ReplaceVO searchVO) throws Exception {
        return selectList("ReplaceMapper.selectReplaceList", searchVO);
    }    
    
    public List<ReplaceVO> selectReplaceUseList(ReplaceVO ReplaceVO) throws Exception {
        return selectList("ReplaceMapper.selectReplaceUseList", ReplaceVO);
    }   

    public void insertReOffday(ReplaceVO searchVO ) throws Exception {
	    insert("ReplaceMapper.insertReOffday", searchVO);
	 }
    
    
}