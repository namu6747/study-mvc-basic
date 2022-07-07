package repositories;

import java.util.ArrayList;

import beans.MemberVO;
import utils.Criteria;

public interface ManagementDAO {
	
	// limit startRow, perPageNum
	ArrayList<MemberVO> getMemberList(Criteria cri);
	
	// 전체 회원 수
	int getMemberTotalCount();
	
}








