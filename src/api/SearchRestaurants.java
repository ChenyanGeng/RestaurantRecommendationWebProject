package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MongoDBConnection;
import db.MySQLDBConnection;

/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet({ "/SearchRestaurants", "/restaurants" })
public class SearchRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final DBConnection connection = new MongoDBConnection();
	private static final DBConnection connection = new MySQLDBConnection();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchRestaurants() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// allow access only if session exists
					HttpSession session = request.getSession();
					if (session.getAttribute("user") == null) {
						response.setStatus(403);
						System.out.println("wrong");
						return;
					}
		JSONArray array = new JSONArray();
	   		 if (request.getParameterMap().containsKey("user_id")
	   			 && request.getParameterMap().containsKey("lat")
	   			 && request.getParameterMap().containsKey("lon")) {
	   			 String userId = request.getParameter("user_id");
	   			 double lat = Double.parseDouble(request.getParameter("lat"));
	   			 double lon = Double.parseDouble(request.getParameter("lon"));
	   			 System.out.println("here");
	   			 array = connection.searchRestaurants(userId, lat, lon);
	   		 }
	   	 
	   	 RpcParser.writeOutput(response, array);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
