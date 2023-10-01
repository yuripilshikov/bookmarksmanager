package bookmarksstorage.model;

public class Bookmark {
	private final int id;
	private String name;
	private String description;
	private String link;
	private int grade;
	
	
	public Bookmark(int id, String name, String description, String link, int grade) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.link = link;
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}


	@Override
	public String toString() {
		return id + " " + name;
	}
	
	
}
