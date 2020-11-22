package ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph wg;

    /**
     * initiates this class with a weighted graph
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        if (g != null) {
            this.wg = g;
        }
    }

    /**
     * returns the graph that has been initiated
     */
    @Override
    public weighted_graph getGraph() {
        return this.wg;
    }

    /**
     * creates a deep copy of the the internal graph and returns the copy as a weighted graph
     * @return
     */
    @Override
    public weighted_graph copy() {
        if (wg == null) return null;
        WGraph_DS res = new WGraph_DS((WGraph_DS) wg);
        return res;
    }

    /**
     * checks whether the graph is fully connected, returns true if it is, and false otherwise
     * @return
     */
    @Override
    public boolean isConnected() {
        if (this.wg == null || this.wg.nodeSize() == 0 || this.wg.nodeSize() == 1) {
            return true;
        }
        resetTag(0);
        Queue<node_info> q = new LinkedList<node_info>();
        int count = 0, res = 0;
        if (wg.nodeSize() == 0 || wg.nodeSize() == 1) return true;
        Collection<node_info> col_node = wg.getV();
        Iterator<node_info> itr_node = col_node.iterator();
        if (itr_node.hasNext()) {
            node_info temp_node = itr_node.next();
            temp_node.setTag(1);
            count++;
            q.add(temp_node);
        }
        while (!q.isEmpty()) {
            node_info temp_Ni = q.poll();
            if (temp_Ni != null) {
                Collection<node_info> col = wg.getV(temp_Ni.getKey()); //temp_Ni.getNi();
                Iterator<node_info> itr = col.iterator();
                while (itr.hasNext()) {
                    node_info Ni = itr.next();
                    if (Ni.getTag() == 0) {
                        count++;
                        Ni.setTag(1);
                        q.add(Ni);
                    }
                }
            }
        }
        if (count == wg.nodeSize()) return true;

        return false;
    }

    /**
     * sets the tag of all nodes in the graph to the given value: val.
     *
     * @param val
     */
    private void resetTag(int val) {
        for (node_info index : wg.getV()) {
            index.setTag(val);
        }
    }

    /**
     * returns the shortest distance between the two given nodes
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.wg.getNode(src) != null && this.wg.getNode(dest) != null) {
            if (src == dest) {
                return 0;
            }
            this.mc = this.wg.getMC();
            resetTag(-1);
            wg.getNode(src).setTag(0);
            Queue<node_info> q = new LinkedList<node_info>();
            q.add(this.wg.getNode(src));
            while (!q.isEmpty()) {
                node_info temp = q.poll();
                double length = temp.getTag();
                for (node_info i : this.wg.getV(temp.getKey())) {
                    double length_i = wg.getEdge(temp.getKey(), i.getKey());
                    if (i.getTag() < 0 || i.getTag() > length + length_i) {
                        i.setTag(length + length_i);
                        q.add(i);
                    }
                }
            }
        }
        return this.wg.getNode(dest).getTag();
    }

    private int mc;

    /**
     * returns a List containing the shortest path between the two given nodes
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (src == dest || this.wg.nodeSize() == 0) {
            return list;
        }
        int chkMC = this.wg.getMC();
        if (list.isEmpty() || chkMC != mc) {
            shortestPathDist(src, dest);
        }
        list.add(this.wg.getNode(dest));
        node_info temp = this.wg.getNode(dest);
        boolean flag = true;
        while (flag) {
            int min = getMinEdge(temp);
            if (min >= 0) {
                temp = this.wg.getNode(min);
                list.add(temp);
                if (temp.getKey() == src) flag = false;
            }
        }
        swapList();
        return list;
    }

    private static List<node_info> list = new ArrayList<node_info>();

    /**
     * a function that gets a node and returns the shortest path according to src->dest path.
     * meaning it gives the way back from dest to src in steps.
     * @param node
     * @return
     */
    private int getMinEdge(node_info node) {
        double min = Double.MAX_VALUE;
        int minKey = -1;
        for (node_info temp : this.wg.getV(node.getKey())) {
            double e = this.wg.getEdge(temp.getKey(), node.getKey());
            if (temp.getTag() >= 0 && temp.getTag() + e < min) {
                min = temp.getTag() + e;
                minKey = temp.getKey();
            }
        }
        return minKey;
    }

    /**
     * changes the order of the given List so that the end becomes the beginning and so on.
     */
    private void swapList() {
        List<node_info> temp = new ArrayList<node_info>();
        int length = list.size();
        for (int i = 0; i < length; i++) {
            temp.add(list.get(length - 1 - i));
        }
        list = temp;
    }
    private String chk  = "";

    /**
     * a saving function that writes the graph on the given file name
     * the function writes all the nodes and all of the edges between them including the distance between them
     * @param file - the file name (may include a relative path).
     *
     *
     * this program is based on the program written by yael in her exercise.
     * @return
     */
    @Override
    public boolean save(String file) {
        try {
            PrintWriter pw = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();
            resetTag(0);
            for (node_info n : this.wg.getV()) {
                for (node_info n2 : this.wg.getV(n.getKey())) {
                    if(n2.getTag() != -1) {
                        int key1 = n.getKey(), key2 = n2.getKey();
                        Double w = this.wg.getEdge(key1, key2);
                        String str = ","+key1+","+key2+","+w+"\n";
                        sb.append(str);
                        pw.write(sb.toString());
                        sb.setLength(0);
                    }
                }
                n.setTag(-1);
            }
            pw.close();
            return true;
        } catch (IOException e) {
            return false;
        }

    }


    /**
     * loads a saved graph from the given file name
     * meaning, it creates a new graph according to the data written on the save file
     *
     *
     * this program is based on the program written by yael in her exercise.
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            WGraph_DS g = new WGraph_DS();
            boolean flag = true;
            if (br != null) {
                String s = br.readLine();
                while (s != null && flag) {
                    String[] arr = s.split(",");
                    int x1 = Integer.parseInt(arr[1]);
                    g.addNode(x1);
                    int x2 = Integer.parseInt(arr[2]);
                    g.addNode(x2);
                    double d = Double.parseDouble(arr[3]);
                    g.connect(x1, x2, d);
                    if (br != null) {
                        s = br.readLine();
                    } else flag = false;
                }
            }
            if (g != null) {
                this.wg = g;

                return true;
            }
            return false;
        } catch (IOException x) {
            return false;
        }
    }
}


