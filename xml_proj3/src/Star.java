import java.util.*;

public class Star {

	public Star(){
		
		id = 0; 
		fn = ln = dob = photo_url = "";
	}

	public int id;
	public String fn;
	public String ln;
	public String dob;
	public String photo_url;
	public List<Integer> featuredMovies = new ArrayList<Integer>();
	public List<String> featuredMoviesTitle = new ArrayList<String>();

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Actor information - ");
		sb.append("Name: " + fn + " " + ln);
		sb.append(" DoB: " + dob);

		return sb.toString();
	}
	
	public String getFN() {
		return fn;
	}
	public String getLN() {
		return ln;
	}


	
}	
