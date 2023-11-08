package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;


public class Village {
	private static class Marche {
		private Etal[] etals;
		private Marche(int nbeEtal) {
			etals = new Etal[nbeEtal];
			for (int i = 0; i < nbeEtal; i++) {
                etals[i] = new Etal();
            }
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
                Etal etal = etals[indiceEtal];
                etal.occuperEtal(vendeur, produit, nbProduit);  
			}
			else {
                System.out.println("L'indice de l'étal est invalide.");
            }
		}
		private int trouverEtalLibre() {
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
				if(etals[i].getVendeur().getNom().equals(gaulois.getNom())) {
					return etals[i];
				}
			}
			return null;
		}
		public void libererEtal(Etal etal) {
            for (int i = 0; i < etals.length; i++) {
                if (etals[i] == etal) {
                    etals[i].libererEtal();
                    return;
                }
            }
        }
		private String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder sortie = new StringBuilder();
			for (int i=0; i<etals.length; i++) {
				if (etals[i].isEtalOccupe()){
					sortie.append(etals[i].afficherEtal()).append("\n");
				}
				else {
					nbEtalVide++;
				}
			}
			if (nbEtalVide>0) {
				sortie.append("Il reste ").append(nbEtalVide).append(" étals non utilisés dans le marché.\n");
			}
			return sortie.toString();
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
		marche = new Marche(nbEtalsMaximum);
	}
    public static class VillageSansChefException extends Exception {
        public VillageSansChefException(String message) {
            super(message);
        }
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
		if (chef == null) {
	        throw new VillageSansChefException("Le village n'a pas de chef !");
	    }
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        int etalLibre = marche.trouverEtalLibre();
        if (etalLibre != -1) {
            String message = vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".";
            marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
            return message + " " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (etalLibre+1) + ".";
        } else {
            return vendeur.getNom() + " n'a pas pu trouver d'étal libre pour vendre des " + produit + ".";
        }
    }
    public String rechercherVendeursProduit(String produit) {
        StringBuilder vendeurs = new StringBuilder("Les vendeurs qui proposent des " + produit + " sont :\n");
        for (int i = 0; i < marche.etals.length; i++) {
            if (marche.etals[i].contientProduit(produit)) {
            	
                vendeurs.append("- ").append(marche.etals[i].getVendeur().getNom()).append("\n");
            }
        }
        return vendeurs.toString();
    }
    public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }
    public String partirVendeur(Gaulois vendeur) {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal != null) {
            String libereEtal = etal.libererEtal();
            return "Le vendeur " + vendeur.getNom() + " quitte son étal, " + libereEtal;
        } else {
            return vendeur.getNom() + " ne possède pas d'étal à quitter.";
        }
    }
    public String afficherMarche() {
        return marche.afficherMarche();
    }
}