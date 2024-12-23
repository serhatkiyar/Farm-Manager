package cysapi.models.animals;

public class Cow extends Animal {
    public Cow(double weight, String gender, String healthStatus) {
        super(weight, gender, healthStatus, Genus.COW.getDisplayName());
    }
}