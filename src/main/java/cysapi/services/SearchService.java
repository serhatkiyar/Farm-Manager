package cysapi.services;

import cysapi.models.animals.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SearchService  {

    public static List<Animal> searchWithEarringNumber(ArrayList<Animal> data, String earringNumber) {
        return data.stream()
                .filter(animal -> animal.getEarringNumber().equals(earringNumber))
                .collect(Collectors.toList());
    }

    public static List<Animal> searchWithGenus(ArrayList<Animal> data, ArrayList<String> genusList) {
        return data.stream()
                .filter(animal -> genusList.contains(animal.getGenus()))
                .collect(Collectors.toList());
    }

    public static List<Animal> searchWithGender(ArrayList<Animal> data, ArrayList<String> genderList) {
        return data.stream().
                filter(animal -> genderList.contains(animal.getGender()))
                .collect(Collectors.toList());
    }

    public static List<Animal> searchHealthStatus(ArrayList<Animal> data, ArrayList<String> healthStatusList) {
        return data.stream()
                .filter(animal -> healthStatusList.contains(animal.getHealthStatus()))
                .collect(Collectors.toList());
    }

    public static List<Animal> searchWithBarns(ArrayList<Animal> data, ArrayList<String> barnNameList) {
        return data.stream()
                .filter(animal -> barnNameList.contains(animal.getBarnLocation().getName()))
                .collect(Collectors.toList());
    }

    public static List<Animal> searchWithWeight(ArrayList<Animal> data, int minWeight, int maxWeight) {
        return data.stream()
                .filter(animal -> (animal.getWeight() >= minWeight) && (animal.getWeight() <= maxWeight))
                .collect(Collectors.toList());
    }

}
