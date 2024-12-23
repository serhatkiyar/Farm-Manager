package cysapi.models.farm;

import cysapi.models.animals.Animal;

import java.util.ArrayList;

public class Farm {
    private static final Farm INSTANCE = new Farm("Çiftlik");
    public static Farm getInstance() {
        return INSTANCE;
    }
    private final ArrayList<Barn> barns = new ArrayList<>();
    private String name;

    public Farm(String name) {
        this.name = name;
    }

    public ArrayList<Animal> getAllAnimals() {
        ArrayList<Animal> allAnimals = new ArrayList<>();
        for (Barn barn : barns) {
            allAnimals.addAll(barn.getAnimals());
        }
        return allAnimals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name=name;}

    public int getBarnCount() {
        return barns.size();
    }

    public ArrayList<Barn> getBarns() {
        return barns;
    }

    public Barn findBarn(String barnName) {
        for (Barn barn : barns)
        {
            if (barn.getName().equals(barnName))
            {
                return barn;
            }
        }
        return null;
    }
}
