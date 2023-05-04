package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
    protected int ndSommetsVisites;
    protected int nbSommets;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.ndSommetsVisites = 0;
    }

    @Override
    protected ShortestPathSolution doRun() {
        boolean fin = false ;
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        int sizeGraph = graph.size();

        ShortestPathSolution solution = null;
        
        /*Tableau de Labels */
        Label tabLabels[] = new Label [sizeGraph];
        
     
        /*Tas de Labels */
        BinaryHeap<Label> tas = new BinaryHeap<Label>();

        /*tableau des predecesseurs */
        Arc[] predecessorArcs = new Arc[sizeGraph];
        
        
        /*ajout du sommet de depart */
        Label depart = newLabel(data.getOrigin(),data) ;
        tabLabels[depart.getSommet_courant().getId()] = depart;
        tas.insert(depart);
        depart.setInTas();
        depart.setCout_realise(0);

        /*Notify all observers that the origin has been processed.*/
        notifyOriginProcessed(data.getOrigin());

        /*Iterations: while il existe des sommets non marques */
        while(!tas.isEmpty() && !fin){

            Label current = tas.deleteMin();

            /*Notify all observers that a node has been marked */
            notifyNodeMarked(current.getSommet_courant());
            current.setMarque();

            /*si le noeud marque est deja notre destination , on arrete le parcours */
            if(current.getSommet_courant() == data.getDestination()){
                fin = true ;
            }

            /*Parcours de tous les successeurs */
            Iterator<Arc> arc = current.getSommet_courant().iterator() ; 
            while (arc.hasNext()){
                Arc arcIter = arc.next();

                Node successeur = arcIter.getDestination();

                /*on recupere le label correspondant au noeud depuis le tableau de labels */
                Label successeurLabel = tabLabels[successeur.getId()];

                /*si ce successeur n'a pas de label , on lui associe un */
                if(successeurLabel == null){
                    tabLabels[successeurLabel.getSommet_courant().getId()] = newLabel(successeur, data);
                    /*On incremente le nombre des sommets visites pour le test de performance*/
                    this.ndSommetsVisites++;
                }

                /*si le successeur n'est pas encore marque */
                if(!successeurLabel.getMarque()){
                    /*si on obtient un meilleur cout */
                    /*Alors on le met a jour */

                    if((successeurLabel.getTotalCost()>(current.getcout_realise()+data.getCost(arcIter)
						+(successeurLabel.getTotalCost()-successeurLabel.getcout_realise()))) 
						|| (successeurLabel.getcout_realise()==Float.POSITIVE_INFINITY)){
                            successeurLabel.setCout_realise((current.getcout_realise()+(float)data.getCost(arcIter)));
                            successeurLabel.setPere(current.getSommet_courant());
                            /*si le label est deja dans le tas */
                            /*alors on met à jour sa position dans le tas */
                            if(successeurLabel.getinTas()){
                                tas.remove(successeurLabel);
                            }else{
                                /*on ajoute le label dans le tas */
                                successeurLabel.setInTas();
                            }
                            tas.insert(successeurLabel);
                            predecessorArcs[arcIter.getDestination().getId()] = arcIter ;
                     }
                }
            }

        }

        //si la destination a au moins un predecesseur
        if(predecessorArcs[data.getDestination().getId()] != null){
            /*informer les observateurs que la destination est trouvee */
            notifyDestinationReached(data.getDestination());

            /*contruire le parcours a partir de la liste des predecesseurs */
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];

            while(arc != null){
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            /*Inverser le chemin  */
            Collections.reverse(arcs);

            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        return solution;
    }

    /* Crée et retourne le Label correspondant au Node */
	protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
    
}
