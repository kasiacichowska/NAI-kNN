import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    static int k;
    static String sciezkaDoTreningowego;
    static String sciezkaDoTestowego;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj wartość K: ");
        k = scanner.nextInt();

        System.out.println("Podaj ścieżkę do pliku treningowego: ");
        sciezkaDoTreningowego = scanner.next();

        System.out.println("Czy chcesz użyć pliku testowego (T) czy wprowadzić własny wektor testowy (W)?");
        String wybor = scanner.next();

        List<Wektor> wektoryTreningowe = tworzWektory(sciezkaDoTreningowego);

        if (wybor.equals("T")) {
            System.out.println("Podaj ścieżkę do pliku testowego: ");
            sciezkaDoTestowego = scanner.next();
        } else if (wybor.equals("W")) {
            System.out.println("Podaj wartości wektora testowego (oddzielone przecinkami i z etykietą): ");
            String wektorTestowyInput = scanner.next();
            Map<String, Double> nazwyMap = new HashMap<>();
            Wektor wektorTestowy = tworzWektorTestowy(wektorTestowyInput, nazwyMap);
            List<Wektor> wektoryTestowe = new ArrayList<>();
            wektoryTestowe.add(wektorTestowy);
            analizujKlasyfikacje(wektoryTreningowe, wektoryTestowe);
            return;
        } else {
            System.out.println("Niepoprawny wybór. Koniec programu.");
            return;
        }

        scanner.close();

        List<Wektor> wektoryTestowe = tworzWektory(sciezkaDoTestowego);

        analizujKlasyfikacje(wektoryTreningowe, wektoryTestowe);
    }

    public static List<Wektor> tworzWektory(String sciezkaDoPliku) throws IOException {
        List<Wektor> wektory = new ArrayList<>();
        String line;
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(sciezkaDoPliku))) {
            while ((line = br.readLine()) != null) {
                // użyj przecinka jako separatora
                String[] data = line.split(splitBy);

                if (data.length == 5) {
                    Wektor wektor = new Wektor();
                    wektor.x1 = Double.parseDouble(data[0]);
                    wektor.x2 = Double.parseDouble(data[1]);
                    wektor.x3 = Double.parseDouble(data[2]);
                    wektor.x4 = Double.parseDouble(data[3]);
                    wektor.etykieta = data[4];
                    wektory.add(wektor);
                }
            }
        }
        return wektory;
    }

    public static Wektor tworzWektorTestowy(String input, Map<String, Double> nazwyMap) {
        String[] data = input.split(",");
        Wektor wektor = new Wektor();
        wektor.x1 = Double.parseDouble(data[0]);
        wektor.x2 = Double.parseDouble(data[1]);
        wektor.x3 = Double.parseDouble(data[2]);
        wektor.x4 = Double.parseDouble(data[3]);
        wektor.etykieta = data[4];
        return wektor;
    }

    public static void analizujKlasyfikacje(List<Wektor> wektoryTreningowe, List<Wektor> wektoryTestowe) {
        Map<Wektor, Map<Wektor, Double>> mapaKnajblizszychSasiadow = new HashMap<>();

        for (Wektor testowyWektor : wektoryTestowe) {
            Map<Wektor, Double> najblizsiSasiedzi = new HashMap<>();

            for (Wektor treningowyWektor : wektoryTreningowe) {
                double odleglosc = obliczOdlegloscEuklidesowa(testowyWektor, treningowyWektor);
                najblizsiSasiedzi.put(treningowyWektor, odleglosc);
            }

            najblizsiSasiedzi = sortByValue(najblizsiSasiedzi);

            Map<Wektor, Double> kNajblizszychSasiadow = new LinkedHashMap<>();
            int licznik = 0;
            for (Map.Entry<Wektor, Double> entry : najblizsiSasiedzi.entrySet()) {
                if (licznik >= k) {
                    break;
                }
                kNajblizszychSasiadow.put(entry.getKey(), entry.getValue());
                licznik++;
            }

            mapaKnajblizszychSasiadow.put(testowyWektor, kNajblizszychSasiadow);
        }

        int poprawnieZaklasyfikowane = 0; //liczba poprawnie zaklasyfikowanych wektorów

        for (Map.Entry<Wektor, Map<Wektor, Double>> entry : mapaKnajblizszychSasiadow.entrySet()) {
            Wektor testowyWektor = entry.getKey();
            Map<Wektor, Double> kNearestNeighbors = entry.getValue();

            Map<String, Integer> labelCount = new HashMap<>();
            for (Wektor neighbor : kNearestNeighbors.keySet()) {
                String label = neighbor.etykieta;
                labelCount.put(label, labelCount.getOrDefault(label, 0) + 1);
            }

            String najczestszaEtykieta = Collections.max(labelCount.entrySet(), Map.Entry.comparingByValue()).getKey();

            if (najczestszaEtykieta.equals(testowyWektor.etykieta)) {
                poprawnieZaklasyfikowane++; //jeśli etykieta jest poprawna
            }

            // dla każdego wektora testowego
            System.out.print("Dla punktu testowego " + testowyWektor + '\n' + "etykieta k-NN: " + najczestszaEtykieta);
            System.out.println(" Etykieta prawidłowa: " + testowyWektor.etykieta);
            System.out.print("Zgodność wektorów:  " );
            if(najczestszaEtykieta.equals(testowyWektor.etykieta)){
                System.out.println("TAK");
            }else{
                System.out.println("NIE");
            }
        }


        double dokladnosc = ((double) poprawnieZaklasyfikowane / wektoryTestowe.size()) * 100;
        System.out.println("Dokładność: " + dokladnosc + "%");
        System.out.println();

    }

    public static List<Wektor> tworzWektory(String sciezkaDoPliku, Map<String, Double> nazwyMap) throws IOException {
        List<Wektor> wektory = new ArrayList<>();
        String line;
        String splitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(sciezkaDoPliku))) {
            while ((line = br.readLine()) != null) {
                // użyj przecinka jako separatora
                String[] data = line.split(splitBy);

                if (data.length == 5) {
                    Wektor wektor = new Wektor();
                    wektor.x1 = Double.parseDouble(data[0]);
                    wektor.x2 = Double.parseDouble(data[1]);
                    wektor.x3 = Double.parseDouble(data[2]);
                    wektor.x4 = Double.parseDouble(data[3]);
                    wektor.etykieta = data[4];

                    if (!nazwyMap.containsKey(wektor.etykieta)) {
                        double numer = nazwyMap.size() + 1;
                        nazwyMap.put(wektor.etykieta, numer);
                    }
                    wektor.numerEtykiety = nazwyMap.get(wektor.etykieta);
                    wektory.add(wektor);
                }
            }
        }
        return wektory;
    }

    public static double obliczOdlegloscEuklidesowa(Wektor wektorA, Wektor wektorB) {
        double sumaKwadratowRoznic = Math.pow(wektorA.x1 - wektorB.x1, 2) +
                Math.pow(wektorA.x2 - wektorB.x2, 2) +
                Math.pow(wektorA.x3 - wektorB.x3, 2) +
                Math.pow(wektorA.x4 - wektorB.x4, 2);
        return Math.sqrt(sumaKwadratowRoznic);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}