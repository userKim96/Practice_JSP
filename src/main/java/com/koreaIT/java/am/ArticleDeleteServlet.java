package com.koreaIT.java.am;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.koreaIT.java.am.util.DBUtil;
import com.koreaIT.java.am.util.SecSql;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/article/delete") 
public class ArticleDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8;");
		
		int id = Integer.parseInt(request.getParameter("id"));

		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JSP_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
			conn = DriverManager.getConnection(url, "root", "");

			SecSql sql = new SecSql();
			sql.append("DELETE FROM article");
			sql.append("WHERE id = ?", id);

			DBUtil.delete(conn, sql);
			
			response.getWriter().append(String.format("<script>alert('%d번 게시물이 삭제되었습니다'); location.replace('list');</script>", id));

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}