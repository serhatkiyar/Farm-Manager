package cysapi.models.farm;

import cysapi.models.animals.Animal;
import java.util.ArrayList;

public class Barn {
    private String name;
    private int capacity;
    private ArrayList<Animal> animals;

    public Barn(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.animals = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getBarnSize(){
        return animals.size();
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Barn barn = (Barn) obj;
        return name.equalsIgnoreCase(barn.name); // Ahır isimlerine göre karşılaştır
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode(); // Ahır ismine göre hash kodu üret
    }


}