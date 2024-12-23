package cysapi.services;

import cysapi.models.animals.Genus;

public class EarringNumberGenerator {
    private static int cowCounter = 1000;
    private static int sheepCounter = 1000;
    private static int goatCounter = 1000;

    public static synchronized String generate(String genus) {

        if (genus.equals(Genus.COW.getDisplayName())) {
            return "TR-" + Genus.COW.getCode() + "-" + cowCounter++;
        }
        else if (genus.equals(Genus.SHEEP.getDisplayName())) {
            return "TR-" + Genus.SHEEP.getCode() + "-" + sheepCounter++;
        }
        else if (genus.equals(Genus.GOAT.getDisplayName())) {
            return "TR-" + Genus.GOAT.getCode() + "-" + goatCounter++;
        }
        else {
            return "TR-null-0000";
        }
    }

}
