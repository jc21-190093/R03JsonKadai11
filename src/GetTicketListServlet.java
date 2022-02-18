
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


		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			final String driverClassName = "com.mysql.jdbc.Driver";
			final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai11";
			final String id = "jsonkadai11";
			final String pass = "JsonKadai11";

			try {
				Class.forName(driverClassName);
				Connection con = DriverManager.getConnection(url, id, pass);
				int point = 0;

				String tenpo_id = request.getParameter("TENPO_ID");
				String user_id = request.getParameter("USER_ID");
				

				
				PreparedStatement st = con
						.prepareStatement("select * from POINT where tenpo_id =? and user_id=?");
				st.setString(1, tenpo_id);
				st.setString(2, user_id);
				ResultSet result = st.executeQuery();

				if (result.next()) {
					point = result.getInt("point");
				}

				PreparedStatement pst = con.prepareStatement("select * from TICKET where tenpo_id=?  && POINT<=?");
				pst.setString(1, tenpo_id);
				pst.setInt(2, point);
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
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getTicketList.jsp");
				rd.forward(request, response);

			} catch (ClassNotFoundException e) {

				e.printStackTrace();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}


