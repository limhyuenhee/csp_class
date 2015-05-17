package kr.ac.shinhan.csp;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		HttpSession session = req.getSession(false);

		PersistenceManager pm = MyPersistenceManager.getManager();
		Query qry = pm.newQuery(UserLoginToken.class);
		List<UserLoginToken> userLoginToken = (List<UserLoginToken>) qry
				.execute();

		boolean success = false;

		Cookie[] cookies = req.getCookies();

		for (Cookie c : cookies) {
			if (c.getName().equals("t_id")) {
				String token = c.getValue();
				for (UserLoginToken ult : userLoginToken) {
					if (ult.getToken().equals(token)) {
						pm.deletePersistent(ult);
						success = true;
					}
				}
				c.setValue(null);
				c.setMaxAge(0);
				resp.addCookie(c);
			}

		}
		session.setMaxInactiveInterval(0);
		session.invalidate();
		resp.sendRedirect("/login.html");
	}
}
