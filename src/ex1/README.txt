Ex1:

in this repo there is an implementation of a weighted graph using two classes: ex1.src.WGraph_DS & ex1.src.WGraph_Algo.
in ex1.src.WGraph_DS: we have an internal class that implements a simple node with three variables - tag, info & key.
              tag: is for calculations made by the program.
              info: for meta-data.
              key: for accessing the node by the data structure.
              ex1.src.WGraph_DS also contains an implementation of a weighted graph using two HashMaps
              one for all the nodes in the graph and one for all the edges and their weight.
              with simple actions like:
              get & set: for key, info, tag.
              addNode: which adds a new node to the graph using it's key.
              connect: connects two nodes in the graph.
              removeNode: removes a node from the graph and all it's edges.
              removeEdge: remove an edge between two nodes.
              getEdge: returns the distance between two nodes.
              hasEdge: checks if two nodes have an edge.
              edgeSize: returns the number of edges in the graph.
              nodeSize: returns the number of nodes in the graph.
              getV: returns all the nodes in the graph, or returns all the neighbors of a specific node.
              getMC: returns the number of actions that have been made on the graph.

ex1.src.WGraph_Algo: gives us extra actions that can be made on the graph but first the graph needs to be
             initiated with a weighted graph using the init function, next we have a few functions like:
             getGraph: that returns the initiated graph.
             copy: that creates a new graph that is essentially a deep copy of the initiated graph.
             isConnected: checks if a graph is fully connected.
             shortestPathDist: returns the distance between two nodes.
             shortestPath: returns the shortest path between two nodes.
             save: saves the whole graph on a file, including all nodes and all edges with their weight.
             load: creates a new graph according to a save file.
