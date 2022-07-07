package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.NoticeVO;
import utils.DBCPUtil;

public class NoticeDAOImpl implements NoticeDAO {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	@Override
	public boolean noticeWrite(NoticeVO vo) {
		try {
			conn = DBCPUtil.getConnection();
			String sql = "INSERT INTO notice_board " 
					+ " VALUES(null,?,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getNotice_category());
			pstmt.setString(2, vo.getNotice_author());
			pstmt.setString(3, vo.getNotice_title());
			pstmt.setString(4, vo.getNotice_content());
			
			if(pstmt.executeUpdate() > 0) return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(pstmt, conn);
		}

		return false;
	}

	@Override
	public ArrayList<NoticeVO> noticeList(int startRow, int count) {
		long start = System.currentTimeMillis();
		ArrayList<NoticeVO> list = new ArrayList(10);
		try {
			
			conn = DBCPUtil.getConnection();
			String sql = "SELECT * FROM notice_board " 
					+ "	ORDER BY n_num DESC " + " LIMIT ?, ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, count);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				NoticeVO vo = new NoticeVO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getTimestamp(6)
						);
				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		long end = System.currentTimeMillis();
		System.out.println("DBCP noticeList lead time : " + (end-start));

		return list;
	}

	@Override
	public int getTotalCount() {
		int totalCount = 0;
		try {

			conn = DBCPUtil.getConnection();
			String sql = "SELECT count(*) FROM notice_board "; 
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}

		return totalCount;
	}

	@Override
	public NoticeVO noticeDetail(int notice_num) {
		NoticeVO vo = null;
		try {

			conn = DBCPUtil.getConnection();
			String sql = "SELECT * FROM notice_board "
						+ " WHERE n_num = ? "; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new NoticeVO(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getTimestamp(6)
						);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(rs, pstmt, conn);
		}
		
		return vo;
	}

	@Override
	public boolean noticeUpdate(NoticeVO vo) {
		try {
			conn = DBCPUtil.getConnection();
			String sql = "UPDATE notice_board SET" 
					+ "		n_category = ?,"
					+ "		n_title = ?,"
					+ "		n_content = ? "
					+ "		WHERE n_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getNotice_category());
			pstmt.setString(2, vo.getNotice_title());
			pstmt.setString(3, vo.getNotice_content());
			pstmt.setInt(4, vo.getNotice_num());
			
			if(pstmt.executeUpdate() > 0) return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(pstmt, conn);
		}
		return false;
	}

	@Override
	public boolean noticeDelete(int notice_num) {
		try {
			conn = DBCPUtil.getConnection();
			String sql = "DELETE FROM notice_board WHERE n_num = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice_num);
			
			if(pstmt.executeUpdate() > 0) return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCPUtil.close(pstmt, conn);
		}
		return false;
	}

}
