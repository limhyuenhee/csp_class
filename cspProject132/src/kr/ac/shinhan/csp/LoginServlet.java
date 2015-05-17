package kr.ac.shinhan.csp;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession session = req.getSession(true);
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		req.getSession().setAttribute("id", id);
		req.getSession().setAttribute("password", password);
		String userId = (String) session.getAttribute("userId");
		
		String token = null;
		boolean success = false;
		boolean checked = req.getParameter("remember") != null;

		MyPersistenceManager.getManager();

		Query qry = MyPersistenceManager.getManager().newQuery(
				UserAccount.class);

		qry.setFilter("userID == idParam");
		qry.declareParameters("String idParam");

		List<UserAccount> userAccount = (List<UserAccount>) qry.execute(id);

		if (userAccount.size() == 0) {
			success = false;
		}

		else if (userAccount.get(0).getPassword().equals(password)) {
			success = true;
		}

		else {
			success = false;
		}

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");

		resp.getWriter().println("<html>");
		resp.getWriter().println("<body>");

		if (!success) {
			resp.getWriter().println("Fail to login");
			resp.getWriter().println("<a href='login.html'>Login Again</a>");
		}

		if (success) {
			session.setAttribute("a_id", id);
			if (checked == true) {
				token = UUID.randomUUID().toString();
				
				Cookie cookie = new Cookie("t_id", token);
				cookie.setMaxAge(60 * 60 * 24 * 30);
				resp.addCookie(cookie);
				
				String expireData = Integer.toString(cookie.getMaxAge());	
				UserLoginToken usertoken = new UserLoginToken(token, id, expireData);
				PersistenceManager pm = MyPersistenceManager.getManager();
				pm.makePersistent(usertoken);
				
			}
			resp.sendRedirect("/index.html");
		}

		resp.getWriter().println("</body>");
		resp.getWriter().println("</html>");

	}

}
