package bookmarksstorage.model;

public class Bookmark {
	private final int id;
	private String name;
	private String description;
	private String link;
	private int grade;
	private String category;
	
	public Bookmark(int id, String name, String description, String link, int grade, String category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.link = link;
		this.grade = grade;
		this.category = category;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Bookmark [id=" + id + ", name=" + name + ", description=" + description + ", link=" + link + ", grade="
				+ grade + ", category=" + category + "]";
	}
	
	
}
