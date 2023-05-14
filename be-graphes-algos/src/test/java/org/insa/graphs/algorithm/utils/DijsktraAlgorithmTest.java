package org.insa.graphs.algorithm.utils;

import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import org.insa.graphs.algorithm.*;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.*;


import org.junit.*;

public class DijsktraAlgorithmTest {
    
   
    public void testScenario(String mapName, int typeEvaluation, int origine, int destination) throws Exception {

		// Create a new BinaryGraphReader that read from the given input stream.
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
		Graph graph = reader.read();

            //Argument invalide
		if (typeEvaluation!=0 && typeEvaluation!=1) {
			System.out.println("Argument invalide");
		} else {
                 //// cas:hors du graphe ou sommets inexistent
			if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { 
				System.out.println("ERREUR : Paramètres invalides ");

			} else {
                //This class can be used to indicate to an algorithm which arcs can be used and the costs of the usable arcs..
				ArcInspector arcInspectorDijkstra;

                //ArcInspectorFactory:filtre des arcs d'apres le choix de mode
                    //en temps
				if (typeEvaluation == 0) { 
					System.out.println("Mode : Temps");
                    // correspond au troisième filtre "Only road allowed for cars and time"
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2); 
				} 
                else { 
					System.out.println("Mode : Distance");
                    // correspond au premième filtre "No filter (all arcs allowed)"
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
				}


				//afficher infos de depart et destination
				System.out.println("Origine : " + origine);
				System.out.println("Destination : " + destination);

				if(origine==destination) {
					System.out.println("Origine et Destination identiques");
					System.out.println("Cout solution: 0");

				} 
                else 
                {		
                    //conclurer des infos et mise a jour avec different algo
					ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);

                    //BellmanFord et Dijkstra
					BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm D = new DijkstraAlgorithm(data);

					// Recuperation des solutions de Bellman et Dijkstra pour comparer 
					ShortestPathSolution solutionD = D.run();
					ShortestPathSolution solutionB = B.run();


					double costSolutionD;
					double costSolutionB;
                    double CoutSolution;
                    //get le temps ou le longueur d'apres le mode
					if(typeEvaluation == 0) { //Temps
						//Calcul du cout de la solution 
						costSolutionD = solutionD.getPath().getMinimumTravelTime();
						costSolutionB = solutionB.getPath().getMinimumTravelTime();

					} else {
						costSolutionD = solutionD.getPath().getLength();
						costSolutionB = solutionB.getPath().getLength();
					}
                    //comparer le moins court ou le plus rapide
                    if(costSolutionD==costSolutionB){
                        System.out.println("BellmanFord et Dijkstra onn le meme resultat: " + costSolutionD);

                    }else{
                        if(costSolutionD<costSolutionB){
                            CoutSolution=costSolutionD;
                        }else{
                            CoutSolution=costSolutionB;
                        }
                        System.out.println("Cout solution: " + CoutSolution);
                    }


				}
			}
		}
		System.out.println();
		System.out.println();
	}


    
    
    //@BeforeClass sert force l'execution de initiAll() avant toutes les meth de la classe;Cette méthode est exécutée une seule fois avant le démarrage de tous les tests de la classe 
    

       @Test 

        public void testMapINSAdistance() throws Exception {

            System.out.println("test de distance avec la carte insa ");

            String mapName = "/home/el-jid/Bureau/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";

            DijsktraAlgorithmTest test = new DijsktraAlgorithmTest();
            int  origine ;
            int destination;

            System.out.println("test avec un chemin normal");
            origine = 700 ;
            destination = 300;
            test.testScenario(mapName, 1,origine,destination);


            System.out.println("test avec un chemin null");
            origine = 700 ;
            destination = 90000;
            test.testScenario(mapName, 1, origine, destination);

            System.out.println("test avec un chemin null");
            origine = 700 ;
            destination = 700;
            test.testScenario(mapName, 1,origine,destination);

        }
        
       @Test 
        public void testMapINSAtemps() throws Exception {

            System.out.println("test de distance avec la carte insa ");

            String mapName = "/home/el-jid/Bureau/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
            DijsktraAlgorithmTest Test = new DijsktraAlgorithmTest();
            int  origine ;
            int destination;

            System.out.println("test avec un chemin normal");
            origine = 700 ;
            destination = 300;
            Test.testScenario(mapName, 0,origine,destination);


            System.out.println("test avec un chemin null");
            origine = 700 ;
            destination = 90000;
            Test.testScenario(mapName, 0, origine, destination);

            System.out.println("test avec un chemin null");
            origine = 700 ;
            destination = 700;
            Test.testScenario(mapName, 0,origine,destination);

        }
}

