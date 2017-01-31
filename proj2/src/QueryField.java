
public enum QueryField {
	Title("title", "like '", "%'"),
	Year("year", " = ", ""), 
	Director("director", "= '", "'"),
	FirstName("first_name", "= '", "'"),
	LastName("last_name", "= '", "'");
	
	public String name;
	public String fieldValuePrefix;
	public String fieldValueSuffix;
	
	private QueryField(String name, String fieldValuePrefix, String fieldValueSuffix) {
		this.name = name;
		this.fieldValuePrefix = fieldValuePrefix;
		this.fieldValueSuffix = fieldValueSuffix;
	}
	
}
