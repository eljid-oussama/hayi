package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
    private Node sommet_courant; // Sommet courant associé à ce label
    private boolean marque; // Booléen indiquant si le coût min de ce sommet est connu
    private float cout_realise; // Coût réalisé du plus court chemin depuis l'origine vers le sommet
    private Node pere; // Arc précédent sur le chemin correspondant au plus court chemin courant
    private boolean inTas; // indique si le sommet et dans le tas (vrai) , ca aide  à reconstruire le chemin à la fin de l'algorithme
	private String label; // Label associé à ce nœud

    // Constructeur
    public Label(Node sommet_courant) {
        this.sommet_courant = sommet_courant;
        this.marque = false;
        this.cout_realise = Float.POSITIVE_INFINITY; // Initialiser le coût à l'infini par défaut
        this.pere = null;
        this.label = null; // Initialiser le label à null par défaut
    }

    // Getter 
      public Node getSommet_courant() {
        return this.sommet_courant;
    }
    public boolean getMarque() {
        return this.marque;
    }

   public float getcout_realise() {
        return this.cout_realise;
    }

    public Node getPere() {
        return this.pere;
    }

    // Getter pour le coût, qui renvoie le coût réalisé
    public float getTotalCost() {
        return this.cout_realise;
    }

    /* Retourne true si le noeud a été mis dans le tas */
	public boolean getinTas() {
		return this.inTas;
	}
    
    public String getLabel() {
        return this.label;
    }

   // Setteur
    public void setCout_realise(float costSoFar) {
        this.cout_realise = costSoFar;
    }
    
    public void setMarque() {
        this.marque = true;
    }

    public void setPere(Node pere) {
        this.pere = pere;
    }
    
    public void setInTas() {
		this.inTas = true;
	}
    
    public void setLabel(String label) {
        this.label = label;
    }
    

    //comprend pas : essaye yongjia!!!!!!!!!
    
	/* Compare les Labels selon leur coût */
	public int compareTo(Label autre) {
		int resultat;
		if (this.getcout_realise() < autre.getcout_realise()) {
			resultat = -1;
		}
		else if (this.getcout_realise() == autre.getcout_realise()) {
			resultat = 0;
		}
		else {
			resultat = 1;
		}
		return resultat;
	}

    //????Vous devez aussi pouvoir associer un label à chaque noeud — sans modifier la classe Node ni la classe Graph. 
}
