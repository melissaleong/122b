package HelperClasses;

public enum QueryField {
	Title("movies.title", "like '%", "%'"),
	Year("movies.year", " = ", ""), 
	Director("movies.director", "like '%", "%'"),
	FirstName("stars.first_name", "like '%", "%'"),
	LastName("stars.last_name", "like '%", "%'");
	
	public String name;
	public String fieldValuePrefix;
	public String fieldValueSuffix;
	
	private QueryField(String name, String fieldValuePrefix, String fieldValueSuffix) {
		this.name = name;
		this.fieldValuePrefix = fieldValuePrefix;
		this.fieldValueSuffix = fieldValueSuffix;
	}
	
}
