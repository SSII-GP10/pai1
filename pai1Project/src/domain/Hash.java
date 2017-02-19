package domain;

public class Hash {
	
	private Integer id;
	private String name;
	private String hash;
	
	public Hash(Integer id, String name, String hash) {
		this.id = id;
		this.name = name;
		this.hash = hash;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}

}
