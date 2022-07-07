package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.MemberVO;
import utils.Criteria;
import utils.DBCPUtil;

public class ManagementDAOImpl implements ManagementDAO {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	@Override
	public ArrayList<MemberVO> getMemberList(Criteria cri) {
		ArrayList<MemberVO> list = new ArrayList<>();
		
		String sql = "SELECT * FROM mvc_member"
				   + " ORDER BY num DESC "
				   + " limit ?, ?";
		conn = DBCPUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cri.getStartRow());
			pstmt.setInt(2, cri.getPerPageNum());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberVO m = new MemberVO(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getInt(5),
					rs.getString(6),
					rs.getString(7).charAt(0),
					rs.getTimestamp(8),
					rs.getTimestamp(9)
				);
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs,pstmt,conn);
		}
		return list;
	}

	@Override
	public int getMemberTotalCount() {
		String sql = "SELECT count(*) FROM mvc_member";
		int totalCount = 0;
		
		conn = DBCPUtil.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs,pstmt,conn);
		}
		return totalCount;
	}

}








