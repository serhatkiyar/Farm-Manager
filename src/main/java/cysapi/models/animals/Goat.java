package cysapi.models.animals;

public class Goat extends Animal {
    public Goat(double weight, String gender, String healthStatus) {
        super(weight, gender, healthStatus, Genus.GOAT.getDisplayName());
    }
}