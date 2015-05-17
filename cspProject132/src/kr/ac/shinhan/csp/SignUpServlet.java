package kr.ac.shinhan.csp;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet  extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		boolean check = false;
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
	
		PersistenceManager pm = MyPersistenceManager.getManager();
		
		Query qry = pm.newQuery(UserAccount.class);
		List<UserAccount> List = (List<UserAccount>) qry.execute();
		resp.getWriter().println("<html>");
		resp.getWriter().println("<body>");
		
		for(UserAccount a:List)
		{
			if(id.equals(a.getUserID()))
			{
				check = true;
			}
		}
		if(check==true)
		{
			resp.getWriter().println("아이디가 중복됩니다");
		}
		else
		{
			UserAccount ua = new UserAccount(id,password,name);
			pm.makePersistent(ua);
			resp.getWriter().println("회원가입이 되었습니다");
		}
	

		
		resp.getWriter().println("</html>");
		resp.getWriter().println("</body>");
		
	}

}
