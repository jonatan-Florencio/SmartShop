package model.entity;

public class Bloco {

	private int id;
	private String bloco;
	
	public Bloco() {
		super();
	}

	public Bloco(int id, String bloco) {
		super();
		this.id = id;
		this.bloco = bloco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}	
}