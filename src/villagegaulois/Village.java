package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private static class Marche {
		private Etal[] etals;
		private Marche(int nbeEtal) {
			etals = new Etal[nbeEtal];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		private int trouverEtatLibre() {
			for (int i=0; i<etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		private Etal[] trouverEtals(String produit) {
			Etal[] etalsProduit = new Etal[etals.length];
			int j=0;
			for (int i=0; i<etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					etalsProduit[j] = etals[i];
					j+=1;
				}
			}
			return etalsProduit;
		}
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<etals.length;i++) {
				if(etals[i].getVendeur().getNom() == gaulois.getNom()) {
					return etals[i];
				}
			}
			return null;
		}
		private String afficherMarche() {
			int nbEtalVide = 0;
			String sortie = "";
			for (int i=0; i<etals.length; i++) {
				if (etals[i].isEtalOccupe()){
					sortie+=etals[i].afficherEtal();
				}
				else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide>0) {
				sortie+=("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			return sortie;
		}
	}
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtalsMaximum);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}