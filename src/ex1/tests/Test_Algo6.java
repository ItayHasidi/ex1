package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test_Algo6 {
    @Test
    void test6(){
        // graph - 1,000,000
        WGraph_DS wg0 = new WGraph_DS();
        for (int i = 0; i < 1000000; i++) {
            wg0.addNode(i);
        }
        for (int i = 0; i < 1000000; i++) {
            int rnd = (int) ((Math.random()+1)*7);
            for (int j = 0; j < rnd; j++) {
                int rnd2 = (int) (Math.random()*900000);
                double wei = Math.random()*50;
                wg0.connect(i, rnd2, wei);
            }
        }
        System.out.println(wg0.edgeSize());
        double clock = System.currentTimeMillis();
        WGraph_Algo ga0 = new WGraph_Algo();
        ga0.init(wg0);
        assertTrue(ga0.isConnected());
        weighted_graph wg0_copy = ga0.copy();
        assertEquals(wg0.toString(), wg0_copy.toString());
        String str = "ga6.txt";
        assertTrue(ga0.save(str));
        ga0.load(str);
        assertEquals(wg0_copy.toString(), ga0.getGraph().toString());
        double clock2 = System.currentTimeMillis();
        System.out.println(clock2-clock);
    }
}