package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;

//algorithme de Dijkstra pour trouver le plus court chemin dans un graphe
public class DijkstraAlgorithm extends ShortestPathAlgorithm {
    protected int nbSommetV;   //nombre de sommet deja visite
    protected int nbSommet;
   

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.nbSommetV = 0;
    }

    //utilise un tas binaire pour stocker les nœuds visités et un tableau de labels pour stocker les coûts et les prédecesseurs des nœuds.
    @Override
    protected ShortestPathSolution doRun() {
        boolean fin = false ;
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        int sizeGraph = graph.size();
        

        ShortestPathSolution solution = null;
        
        //tableau de Labels 
        Label tabLabels[] = new Label [sizeGraph];
        BinaryHeap<Label> tas = new BinaryHeap<Label>();

        //tableau des predecesseurs  pour chaque sommet
        Arc[] predecessorArcs = new Arc[sizeGraph];
        
        //Ajout du sommet de départ
        Label depart = newLabel(data.getOrigin(),data) ;
        tabLabels[depart.getSommet_courant().getId()] = depart;
        tas.insert(depart);
     
        depart.setInTas();
        depart.setCout_realise(0);

        //Notifier tous les observateurs que l'origine a été traitée
        notifyOriginProcessed(data.getOrigin());

        //Itérations tant qu'il existe des sommets non marqués 
        while(!tas.isEmpty() && !fin){

            Label current = tas.deleteMin();
           

            //tous les observateurs qu'un sommet a été marqué
            notifyNodeMarked(current.getSommet_courant());
            
            // Vérification de l'ordre croissant des coûts des labels marqués
            //System.out.println(current.getTotalCost());
            current.setMarque();

            //si le noeud marque est deja notre destination , on arrete le parcours */
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
                    //null pointer access
                        //tabLabels[successeurLabel.getSommet_courant().getId()] = newLabel(successeur, data);
                        //this.nbSommetV++;
                    successeurLabel = newLabel(successeur, data); // Créez un nouveau Label
                    tabLabels[successeur.getId()] = successeurLabel; // Mettez à jour le tableau de Labels avec le nouveau Label
                    this.nbSommetV++;
                }

                //si le successeur n'est pas encore marque 
                if(!successeurLabel.getMarque()){
                    
                    //si on obtient un meilleur coût pour le successeur
                    if((successeurLabel.getTotalCost()>(current.getcout_realise()+data.getCost(arcIter)
						+(successeurLabel.getTotalCost()-successeurLabel.getcout_realise()))) 
						|| (successeurLabel.getcout_realise()==Float.POSITIVE_INFINITY)){

                            successeurLabel.setCout_realise((current.getcout_realise()+(float)data.getCost(arcIter)));
                            successeurLabel.setPere(current.getSommet_courant());
                            /*si le label est deja dans le tas on met à jour sa position dans le tas */
                            if(successeurLabel.getinTas()){
                                tas.remove(successeurLabel);  //nous avons bien utilise,sion il arrete car tous les sommets sont marque
                               
                            }else{
                                //ajoute le label dans le tas 
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

            //Inverser le chemin:du nœud de destination au nœud de départ
            Collections.reverse(arcs);

            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        System.out.println("nombre de sommet visite "+nbSommetV);
        

        return solution;
    }

    /* Crée et retourne le Label correspondant au Node */
	protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
    
}
