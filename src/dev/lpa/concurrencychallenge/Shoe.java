package dev.lpa.concurrencychallenge;

public abstract class Shoe {
    private String model;
    private String brand;

    public Shoe(String model, String brand) {
        this.model = model;
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

}

class AdidasShoe extends Shoe{

    public AdidasShoe() {
        super("Training","Adidas");
    }
}
class FilaShoe extends Shoe{


    public FilaShoe() {
        super("Casual","Fila");
    }
}

class NikeShoe extends Shoe{

    public NikeShoe() {
        super("Running", "Nike");
    }
}

