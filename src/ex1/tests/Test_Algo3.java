package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.Assert;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class Test_Algo3 {

    public static void main(String[] args) {
        // graph : 1,000 nodes
        WGraph_DS wg0 = new WGraph_DS();
        for (int i = 0; i < 1000; i++) {
            wg0.addNode(i);
        }
        for (int i = 0; i < 1000; i++) {
            int rnd = (int) ((Math.random()+1)*3);
            for (int j = 0; j < rnd; j++) {
                int rnd2 = (int) (Math.random()*1000);
                double wei = Math.random()*50;
                wg0.connect(i, rnd2, wei);
            }
        }
        WGraph_Algo ga0 = new WGraph_Algo();
        ga0.init(wg0);
        double clock = System.currentTimeMillis();

        copy(ga0, wg0);
        save_load(ga0);
        ShortestPathDist(ga0);
        ShortestPath(ga0);

        double clock2 = System.currentTimeMillis();
        System.err.println("the whole program took "+((clock2-clock)/1000)+" seconds");
    }


    static void copy(WGraph_Algo ga0, WGraph_DS wg0){
        double clock = System.currentTimeMillis();
        weighted_graph wg0_copy = ga0.copy();
        Assert.assertEquals(wg0.toString(), wg0_copy.toString());
        double clock2 = System.currentTimeMillis();
        System.err.println("copy took "+((clock2-clock)/1000)+" seconds");
        double clock1 = System.currentTimeMillis();
        Assert.assertTrue(ga0.isConnected());
        int rnd2 = (int) (Math.random()*10);
        wg0_copy.removeNode(rnd2);
        wg0_copy.addNode(rnd2);
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(wg0_copy);
        assertFalse(algo.isConnected());
        double clock12 = System.currentTimeMillis();
        System.err.println("isConnected took "+((clock12-clock1)/1000)+" seconds");
    }

    static void save_load(WGraph_Algo ga0){
        double clock = System.currentTimeMillis();
        weighted_graph wg0_copy = ga0.copy();
        String str = "ga3.txt";
        assertTrue(ga0.save(str));
        ga0.load(str);
        assertEquals(wg0_copy.toString(), ga0.getGraph().toString());
        double clock2 = System.currentTimeMillis();
        System.err.println("save_load took "+((clock2-clock)/1000)+" seconds");
    }

    static void ShortestPathDist(WGraph_Algo ga0){
        double clock = System.currentTimeMillis();
        int x = ga0.getGraph().nodeSize() - 1;
        double res = ga0.shortestPathDist(0, x);
        System.out.println("shortest path distance test 1: "+res);
        double res2 = ga0.shortestPathDist(0, x/2);
        System.out.println("shortest path distance test 2: "+res2);
        double res3 = ga0.shortestPathDist(0, x/4);
        System.out.println("shortest path distance test 3: "+res3);
        double res4 = ga0.shortestPathDist(0, x/10);
        System.out.println("shortest path distance test 4: "+res4);
        double clock2 = System.currentTimeMillis();
        System.err.println("ShortestPathDist took "+((clock2-clock)/1000)+" seconds");
    }

    static void ShortestPath(WGraph_Algo ga0){
        double clock = System.currentTimeMillis();
        int x = ga0.getGraph().nodeSize() - 1;
        Collection<node_info> res = ga0.shortestPath(0, x);
        System.out.println("shortest path test 1: "+res.size());
        Collection<node_info> res2 = ga0.shortestPath(0, x/2);
        System.out.println("shortest path test 2: "+res2.size());
        Collection<node_info> res3 = ga0.shortestPath(0, x/4);
        System.out.println("shortest path test 3: "+res3.size());
        Collection<node_info> res4 = ga0.shortestPath(0, x/10);
        System.out.println("shortest path test 4: "+res4.size());
        double clock2 = System.currentTimeMillis();
        System.err.println("ShortestPath took "+((clock2-clock)/1000)+" seconds");
    }
}
