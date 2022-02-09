
import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getTicketList")
public class GetTicketListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetTicketListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public class TICKET {
		public String tenpo_id;
		public String ticket_id;
		public String ticket_name;
		public String point;

		public TICKET(String tenpo_id, String ticket_id, String ticket_name, String point) {
			this.tenpo_id = tenpo_id;
			this.ticket_id = ticket_id;
			this.ticket_name = ticket_name;
			this.point = point;
		}

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			final String driverClassName = "com.mysql.jdbc.Driver";
			final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai11";
			final String id = "jsonkadai11";
			final String pass = "JsonKadai11";

			try {
				Class.forName(driverClassName);
				Connection con = DriverManager.getConnection(url, id, pass);
				String point = null;

				String tenpo_id = request.getParameter("TENPO_ID");
				String user_id = request.getParameter("USER_ID");

				PreparedStatement st = con
						.prepareStatement("select * from POINT where tenpo_id =? and user_id=?");
				st.setString(1, tenpo_id);
				st.setString(2, user_id);
				ResultSet result = st.executeQuery();

				if (result.next()) {
					point = result.getString("point");
				}

				int point2 = Integer.parseInt("point");
				PreparedStatement pst = con.prepareStatement("select * from TICKET where tenpo_id=?  && POINT<=?");
				st.setInt(1, point2);
				st.setString(2, tenpo_id);
				ResultSet result2 = pst.executeQuery();

				List<String[]> list = new ArrayList<>();
				while (result2.next()) {
					String[] s = new String[3];
					s[0] = result2.getString("ticket_id");
					s[1] = result2.getString("ticket_name");
					s[2] = result2.getString("point");
					list.add(s);
				}

				request.setAttribute("LIST", list);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
				rd.forward(request, response);

			} catch (ClassNotFoundException e) {

				e.printStackTrace();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
}
/*
 * PreparedStatement st = con.preparSeStatement( "select * from TICKET" );
 * ResultSet result = st.executeQuery(); List<TICKET> list = new ArrayList<>();
 * 
 * while(result.next()==true) { String[] s = new String[1];
 * s[0]=result.getString(""); list.add(s);
 * 
 * }
 * 
 * 
 */
/*
 * request.setAttribute("list", json1); RequestDispatcher rd =
 * request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
 * rd.forward(request, response);
 * 
 * } catch (ClassNotFoundException e ) {
 * 
 * e.printStackTrace();
 * 
 * } catch (SQLException e ) {
 * 
 * e.printStackTrace(); }
 */
