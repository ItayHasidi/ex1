package ex1.src;

import java.util.*;

public class WGraph_DS implements weighted_graph {
    private static HashMap<Integer, node_info> nodes = new HashMap<Integer, node_info>();;
    private static HashMap<Integer, HashMap<Integer , Double>> edges = new HashMap<Integer, HashMap<Integer, Double>>();;
    private int countMC = 0, countEdge = 0;

    /**
     * inner class implementing the ex1.src.node_info interface
     */
    private class WNodeInfo implements node_info{

        private int key, count = 0;
        private double tag;
        private String info;

        /**
         * constructors
         */
        public WNodeInfo(){
            this.key = count++;
            this.tag = 0;
            this.info = "";
        }
        public WNodeInfo(int key){
            this.key = key;
            this.tag = 0;
            this.info = "";
        }
        public WNodeInfo(WNodeInfo n){
            this.key = n.key;
            this.tag = n.tag;
            this.info = n.info;
        }

        /**
         * returns the key of the node
         * @return
         */
        @Override
        public int getKey() {
            return this.key;
        }

        /**
         * returns the info of the tag
         * @return
         */
        @Override
        public String getInfo() {
            return this.info;
        }

        /**
         * gets a string and sets it in info
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        /**
         * returns the tag of the node
         * @return
         */
        @Override
        public double getTag() {
            return this.tag;
        }

        /**
         * gets a double variable and sets instead of the present tag of the node
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }
    }

    /**
     * constructors
     */
    public WGraph_DS(){
        this.countMC = 0;
        this.countEdge = 0;
    }
    public WGraph_DS(WGraph_DS g){
        for(node_info x: g.nodes.values()){
            WNodeInfo node = new WNodeInfo((WNodeInfo) x);
            HashMap<Integer, Double> edge = new HashMap<Integer, Double>();
            addNode(x.getKey());
        }
        for (int h1: g.edges.keySet()){
            for (int h2: g.edges.get(h1).keySet()){
                double d = g.edges.get(h1).get(h2);
                this.connect(h1, h2, d);
            }
        }
    }

    /**
     * returns the node with the given key
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key) {
        return this.nodes.get(key);
    }

    /**
     * returns true if the two nodes have an edge between them
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(this.nodes.containsKey(node1) && this.nodes.containsKey(node2) && node1 != node2){
            if(this.edges.get(node1).containsKey(node2) && this.edges.get(node2).containsKey(node1)){
                return true;
            }
        }
        return false;
    }

    /**
     * returns the distance between the two nodes
     * if no edge exists the function returns -1
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(this.nodes.containsKey(node1) && this.nodes.containsKey(node2) && hasEdge(node1, node2)) {
            return this.edges.get(node1).get(node2);
        }
        return -1;
    }

    /**
     * adds a new node to the graph with the given key
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(!this.nodes.containsKey(key)){
            node_info n = new WNodeInfo(key);
            HashMap<Integer, Double> hm = new HashMap<Integer, Double>();
            this.edges.put(key, hm);
            this.nodes.put(key, n);
            countMC++;
        }

    }

    /**
     * connects the two node if no edge exists between them
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(!hasEdge(node1, node2) && node1 != node2 && w >= 0){
            this.edges.get(node1).put(node2, w);
            this.edges.get(node2).put(node1, w);
            countEdge++;
            countMC++;
        }
    }

    /**
     * returns a Collection of all the nodes present in the graph
     * @return
     */
    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    /**
     * returns a Collection of all the nodes that are neighbors of the given node key
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> col = new ArrayList<node_info>();
        for (int x: this.edges.get(node_id).keySet()){
            col.add(this.nodes.get(x));
        }
        return col;
    }

    /**
     * removes the given node, according to it's key, from the graph and returns the node
     * returns null if no such node exists in the graph
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        if(this.edges.containsKey(key) && this.nodes.containsKey(key)){
            for(node_info x: getV(key)){
                removeEdge(key, x.getKey());
            }
            node_info temp = this.nodes.get(key);
            this.nodes.remove(key);
            countMC++;
            return temp;
        }
        return null;
    }

    /**
     * removes the edge between the two given node, if one exists
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1, node2)){
            this.edges.get(node1).remove(node2);
            this.edges.get(node2).remove(node1);
            this.countEdge--;
            this.countMC++;
        }
    }

    /**
     * returns the number of nodes in the graph
     * @return
     */
    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    /**
     * returns the number of edges in the graph
     * @return
     */
    @Override
    public int edgeSize() {
        return countEdge;
    }

    /**
     * returns the number of action that have been made in the graph: addNode, connect, removeNode, removeEdge
     * @return
     */
    @Override
    public int getMC() {
        return countMC;
    }

    /**
     * a toString function that prints all the node keys and edges between them with the length of each edge
     * @return
     */
    @Override
    public String toString(){
        try{
            StringBuilder sb = new StringBuilder();
            String res = "";
            for(node_info n: nodes.values()){
                sb.append(n.getKey());
            }
            for(HashMap<Integer, Double> d: edges.values()){
                for (double x: d.values()){
                    sb.append(x);
                }
            }
            res = sb.toString();
            return res;
        }
        catch (Exception e){
            return null;
        }

    }
}
