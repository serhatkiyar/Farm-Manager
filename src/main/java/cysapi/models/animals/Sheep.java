package cysapi.models.animals;

public class Sheep extends Animal {
    public Sheep(double weight, String gender, String healthStatus) {
        super(weight, gender, healthStatus, Genus.SHEEP.getDisplayName());
    }

}