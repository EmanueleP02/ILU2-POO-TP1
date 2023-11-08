package histoire;

import villagegaulois.Etal;
import personnages.Gaulois;

public class ScenarioCasDegrade {

    public static void main(String[] args) {
        Etal etal = new Etal();
        try {
            System.out.println(village.afficherVillageois());
        } catch (VillageSansChefException e) {
            System.out.println("Exception : " + e.getMessage());
        }
        try {
            etal.acheterProduit(10, null);
        } catch (NullPointerException e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
        try {
            etal.acheterProduit(-5, new Gaulois("Acheteur", 10));
        } catch (IllegalArgumentException e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
        try {
            etal.acheterProduit(5, new Gaulois("Acheteur", 10));
        } catch (IllegalStateException e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
        try {
            etal.libererEtal();
        } catch (Etal.EtalNonOccupeException e) {
            System.out.println("Exception : " + e.getMessage());
        }
        System.out.println("Fin du test");
    }
}
