

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class searchTimeTest
 */
@WebFilter("/searchTimeTest")
public class searchTimeTest implements Filter {

    /**
     * Default constructor. 
     */
    public searchTimeTest() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String date = new Date().toString();
		long servletStart = System.nanoTime();
		chain.doFilter(request, response);
		long servletEnd = System.nanoTime();
		long elapsedTime = servletEnd-servletStart;
		write( request, date, elapsedTime);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public void write(ServletRequest request, String date, long time){
		try{
			String filename = request.getServletContext().getRealPath("/jmeterTest.txt");
			File file = new File(filename);
			FileWriter writer = new FileWriter(file, true);
			writer.write("Date: " + date + "time elapsed: " + time + "\n");
			writer.flush();
			writer.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
