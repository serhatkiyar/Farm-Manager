package cysapi.services;

import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;

public class FarmService {

    public static void addBarnToFarm(Farm farm, Barn barn) {
        farm.getBarns().add(barn);
    }

    public static boolean removeBarnFromFarm(Farm farm, String barnID) {
        return farm.getBarns().removeIf(barn -> barn.getName().equals(barnID));
    }
}
