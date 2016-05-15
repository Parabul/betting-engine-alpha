package kz.nmbet.betradar.web.beans;

public class ShortOdd {

	private Integer id;
	private String info;

	public ShortOdd(Integer id, String info) {
		super();
		this.id = id;
		this.info = info;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
