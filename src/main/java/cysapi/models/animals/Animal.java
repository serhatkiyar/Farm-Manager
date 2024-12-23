package cysapi.models.animals;

import cysapi.models.farm.Barn;
import cysapi.services.EarringNumberGenerator;

import java.util.Collections;
import java.util.List;

public class Animal {
    private static int cowCounter = 1000;
    private static int sheepCounter = 1000;
    private static int goatCounter = 1000;
    protected double weight;
    protected String genus;
    protected Barn barnLocation;
    protected String gender;
    protected String healthStatus;
    protected String earringNumber;
    private List<String> vaccinations;

    public Animal(double weight, String gender, String healthStatus, String genus) {
        this.weight = weight;
        this.gender = gender;
        this.healthStatus = healthStatus;
        this.genus = genus;
        this.earringNumber = EarringNumberGenerator.generate(genus);
    }

    public void addVaccination(String vaccineName)
    {
        vaccinations.add(vaccineName);
    }

    public List<String> getVaccinations()
    {
        return Collections.unmodifiableList(vaccinations);
    }

    public String[] getInformation() {
        return new String[]{Double.toString(weight), gender, healthStatus};
    }

    public String getEarringNumber() {
        return earringNumber;
    }

    public void setEarringNumber(String earringNumber) {
        this.earringNumber = earringNumber;
    }

    public String getGenus() {
        return genus;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public static int getCowCounter() {
        return cowCounter;
    }

    public static void setCowCounter(int cowCounter) {
        Animal.cowCounter = cowCounter;
    }

    public static int getSheepCounter() {
        return sheepCounter;
    }

    public static void setSheepCounter(int sheepCounter) {
        Animal.sheepCounter = sheepCounter;
    }

    public static int getGoatCounter() {
        return goatCounter;
    }

    public static void setGoatCounter(int goatCounter) {
        Animal.goatCounter = goatCounter;
    }

    public Barn getBarnLocation() {
        return barnLocation;
    }

    public void setBarnLocation(Barn barnLocation) {
        this.barnLocation = barnLocation;
    }

}



