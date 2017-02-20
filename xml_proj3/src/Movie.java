
import java.util.*;

public class Movie {

	
	public Movie(){
		
		id = year = 0; 
		title = director = banner_url = trailer_url = "";
	}

	public int id;
	public String title;
	public int year;
	public String director;
	public String banner_url;
	public String trailer_url;
	// public List<Star> starsInMovie = new ArrayList<Star>();
	public List<String> genresInMovie = new ArrayList<String>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getBanner_url() {
		return banner_url;
	}
	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}
	public String getTrailer_url() {
		return trailer_url;
	}
	public void setTrailer_url(String trailer_url) {
		this.trailer_url = trailer_url;
	}
	// public List<Star> getStarsInMovie() {
	// 	return starsInMovie;
	// }
	// public void setStarsInMovie(List<Star> starsInMovie) {
	// 	this.starsInMovie = starsInMovie;
	// }
	public List<String> getGenresInMovie() {
		return genresInMovie;
	}
	public void setGenresInMovie(List<String> genresInMovie) {
		this.genresInMovie = genresInMovie;
	}
}	
