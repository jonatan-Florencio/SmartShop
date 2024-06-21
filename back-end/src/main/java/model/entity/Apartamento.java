package model.entity;

public class Apartamento {

    private int id;
    private int apartamento;
    
    public Apartamento() {
        super();
    }

    public Apartamento(int id, int apartamento) {
        super();
        this.id = id;
        this.apartamento = apartamento;
    }

    public Apartamento(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApartamento() {
        return apartamento;
    }

    public void setApartamento(int apartamento) {
        this.apartamento = apartamento;
    }
}
