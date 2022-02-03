

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getPoint")
public class GetPointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public GetPointServlet() {
        super();
       
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				final String driverClassName = "com.mysql.jdbc.Driver";
				final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai11";
				final String id = "jsonkadai11";
				final String pass = "JsonKadai11";
				
				String tenpo_id = request.getParameter("TENPO_ID");
				String user_id = request.getParameter("USER_ID");				

		try {
			Class.forName(driverClassName);
			Connection con=DriverManager.getConnection(url,id,pass);
			PreparedStatement st =
					con.prepareStatement(
							"select point from point where  tenpo_id = ? and user_id = ?"
						); 
			PreparedStatement st2 =
					con.prepareStatement(
							"insert into point (tenpo_id,user_id,point) values(?,?,500)"
						); 
			
			int point = 0;	
	
			
			st.setString(1, tenpo_id);
			st.setString(2, user_id);			
			ResultSet result = st.executeQuery();
			
			
			if( result.next() == true) {				
				point = result.getInt("point");
			}
			
			else{
				st2.setString(1, tenpo_id);
				st2.setString(2, user_id);			
				st2.executeUpdate();				
			}
				
				request.setAttribute("point", point);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
				rd.forward(request, response);
			
		} catch (ClassNotFoundException e ) {
			
			e.printStackTrace();
			
		} catch (SQLException e ) {
			
			e.printStackTrace();
		}		
	}
}
