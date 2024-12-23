package cysapi.dataaccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import cysapi.models.animals.*;
import cysapi.models.farm.Barn;
import cysapi.models.farm.Farm;
import cysapi.services.BarnService;
import cysapi.services.FarmService;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class DataService {
    private static final Set<String> existingEarringNumbers = new HashSet<>();
    private static final Set<String> existingBarnNames = new HashSet<>();

    public static void readFromCSV(String barnName) throws IOException
    {
        int counter = 0;
        String path = "/Users/Serhat/IdeaProjects/Farm Manager/src/main/resources/data/farm/barns";
        File barnfile = new File(path, barnName + ".txt");

        if (!barnfile.exists())
        {
            throw new FileNotFoundException("Hata: " + barnfile.getAbsolutePath() + " dosyası bulunamadı!");
        }
        if (existingBarnNames.contains(barnName)) {
            System.out.println("Tekrarlayan ahır ismi: " + barnName + ", işlem yapılmadı.");
            return;
        }

        {

            try (BufferedReader reader = new BufferedReader(new FileReader(barnfile))) {
                String firstLine = reader.readLine();
                if (firstLine == null || firstLine.trim().isEmpty())
                {
                    throw new IOException("Dosya formatı hatalı: İlk satır kapasiteyi içermiyor.");
                }

                int capacity;

                try {
                    capacity = Integer.parseInt(firstLine.trim());
                    System.out.println(firstLine);
                } catch (NumberFormatException e) {
                    throw new IOException("Dosya formatı hatalı: İlk satır geçerli bir kapasite değeri içermiyor. (" + firstLine + ")");
                }

                Barn barn = new Barn(barnName, capacity);
                Farm farm = Farm.getInstance();
                FarmService.addBarnToFarm(farm, barn);
                existingBarnNames.add(barnName);
                System.out.println(farm.getBarns());

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 5) {
                        String earringNumber = parts[0].trim();
                        String genus = parts[1].trim();
                        String gender = parts[2].trim();
                        String healthStatus = parts[3].trim();
                        String weightString = parts[4].trim();
                        double weight = Double.parseDouble(weightString);

                        if (!existingEarringNumbers.contains(earringNumber)) {
                            existingEarringNumbers.add(earringNumber);

                            Animal animal = null;
                            if (genus.equals(Genus.COW.getDisplayName())) {
                                animal = new Cow(weight, gender, healthStatus);
                            }
                            else if (genus.equals(Genus.SHEEP.getDisplayName())) {
                                animal = new Sheep(weight, gender, healthStatus);
                            }
                            else if (genus.equals(Genus.GOAT.getDisplayName())) {
                                animal = new Goat(weight, gender, healthStatus);
                            }

                            if (animal != null) {
                                animal.setBarnLocation(barn);
                                animal.setEarringNumber(earringNumber);

                                BarnService.addAnimalToBarn(barn, animal);
                                counter++;
                            } else {
                                System.out.println("Hayvan Oluşturma Başarısız.");
                            }
                        } else {
                            System.out.println("Tekrarlayan küpe numarası: " + earringNumber + ", işlem yapılmadı.");
                        }
                    } else {
                        System.out.println("Hatalı veri formatı: " + line);
                    }
                }
                System.out.println(counter + " Adet hayvan eklendi.");
            }
        }
    }
    public static void readAllBarnFiles() throws IOException
    {
        Farm farm = Farm.getInstance();
        String path = "/Users/Serhat/IdeaProjects/Farm Manager/src/main/resources/data/farm/barns";
        File folder = new File(path);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        String counterPath = "/Users/Serhat/IdeaProjects/Farm Manager/src/main/resources/data/farm/CounterData.txt";
        File counterFile = new File(counterPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(counterFile))) {
            for (int i = 0; i < 3; i++) {
                String line = reader.readLine();
                if (line == null || line.trim().isEmpty()) {
                    throw new IOException("Dosya formatı hatalı: Tür counter bilgisi eksik.");
                }
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String species = parts[0].trim();
                    int count = Integer.parseInt(parts[1].trim());
                    if (species.equalsIgnoreCase("Cow")) {
                        Animal.setCowCounter(count);
                    } else if (species.equalsIgnoreCase("Sheep")) {
                        Animal.setSheepCounter(count);
                    } else if (species.equalsIgnoreCase("Goat")) {
                        Animal.setGoatCounter(count);
                    }
                }
            }
        }
        if (files == null || files.length == 0)
        {
            System.out.println("Okunacak dosya bulunamadı.");
            return;
        }

        for (File file : files)
        {
            String fileName = file.getName();
            String barnName = fileName.substring(0, fileName.lastIndexOf('.'));

            System.out.println("Okunan Dosya: " + fileName);

            try {
                System.out.println("Ağıl ismi test: " +     barnName);
                readFromCSV(barnName);
            } catch (IOException e) {
                System.err.println("Dosya okunurken hata oluştu: " + file.getName());
                e.printStackTrace();
            }

        }
    }

    public static void writeToCSV(Animal animal) throws IOException
    {
        String path = "animals/data/" + animal.getBarnLocation();
        File directory = new File(path);
        int capacity = animal.getBarnLocation().getCapacity();
        if (!directory.exists()) {
            directory.mkdir();
        }


        File file = new File(directory, animal.getBarnLocation() + ".txt");
        boolean isNewFile = !file.exists();

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(file, true)))
        {
            if (isNewFile)
            {
                bWriter.write(String.valueOf(animal.getBarnLocation().getCapacity()));
                bWriter.newLine();
            }

            String animalInformation = animal.getEarringNumber() + "," + animal.getGenus() + "," +
                    animal.getGender() + "," + animal.getHealthStatus() + "," +
                    animal.getWeight() + "," + animal.getBarnLocation();
            bWriter.write(animalInformation);
            bWriter.newLine();
        }
    }
}
