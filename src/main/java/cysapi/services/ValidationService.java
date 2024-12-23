package cysapi.services;



public class ValidationService {


    public static boolean validateEarringNumber(String earringNumber) {
        if (earringNumber == null || earringNumber.trim().isEmpty())
        {
            AlertService.showAlert("Uyarı", "Küpe numarası boş bırakılamaz!");
            return false;
        }

        String regex = "TR-(CW|GT|SP)-\\d{4}";
        if (!earringNumber.matches(regex))
        {
            AlertService.showAlert("Hata", "Küpe numarası formatı hatalı!\n" +
                    "Doğru format: TR-<TÜR>-<COUNTER>\n" +
                    "Örneğin: TR-CW-1000, TR-GT-1234");
            return false;
        }

        String[] parts = earringNumber.split("-");
        int counter = Integer.parseInt(parts[2]);
        if (counter < 1000)
        {
            AlertService.showAlert("Hata", "Küpe numarasındaki sayaç kısmı 1000'den küçük olamaz!");
            return false;
        }

        return true;
    }
}