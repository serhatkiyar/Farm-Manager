package cysapi.services;

import cysapi.models.animals.Animal;
import cysapi.models.farm.Barn;

public class BarnService {
    public static boolean addAnimalToBarn(Barn barn, Animal animal) {
        if (barn.getBarnSize() < barn.getCapacity()) {
            barn.getAnimals().add(animal);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean removeAnimalFromBarn(Barn barn, String earringNumber) {
        return barn.getAnimals().removeIf(animal -> animal.getEarringNumber().equals(earringNumber));
    }
}
