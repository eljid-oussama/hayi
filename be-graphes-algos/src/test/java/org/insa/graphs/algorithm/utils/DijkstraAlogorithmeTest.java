package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.Test;

public class DijkstraAlogorithmeTest {
    
    @Test
    public void testMapINSAdistance() throws Exception {

        System.out.println("test de distance avec la carte insa ");

        String mapName = "C:/Users/Utilisateur/Desktop/3A MIC/Kimi/graphe/Maps/insa.mapgr";

        DijkstraAlgorithmTestMap test = new DijkstraAlgorithmTestMap();
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

        String mapName = "C:/Users/Utilisateur/Desktop/3A MIC/Kimi/graphe/Maps/insa.mapgr";
        DijkstraAlgorithmTestMap Test = new DijkstraAlgorithmTestMap();
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
