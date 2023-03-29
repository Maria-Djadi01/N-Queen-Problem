import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Double> timesSpentDFS = new ArrayList<Double>();
		ArrayList<Double> timesSpentBFS = new ArrayList<Double>();
		ArrayList<Double> timesSpentAStar1 = new ArrayList<Double>();
		ArrayList<Double> timesSpentAStar2 = new ArrayList<Double>();

		ArrayList<Integer> nbNodesDFS = new ArrayList<Integer>();
		ArrayList<Integer> nbNodesBFS = new ArrayList<Integer>();
		ArrayList<Integer> nbNodesAStar1 = new ArrayList<Integer>();
		ArrayList<Integer> nbNodesAStar2 = new ArrayList<Integer>();

		for (int i = 11; i < 12; i++) {
			Resolution resolution = new Resolution(i);
			System.out.println("***********Board size : " + i + " ***********");

			State st_Astar1 = resolution.AStar1();
			System.out.println("AStar1 : " + st_Astar1.board + " " + resolution.nb_node_AStar1 + " "
					+ resolution.time_AStar1 + "ms");
			timesSpentAStar1.add(resolution.time_AStar1);
			nbNodesAStar1.add(resolution.nb_node_AStar1);

			State st_Astar2 = resolution.AStar2();
			System.out.println("AStar2 : " + st_Astar2.board + " " + resolution.nb_node_AStar2 + " "
					+ resolution.time_AStar2 + "ms");
			timesSpentAStar2.add(resolution.time_AStar2);
			nbNodesAStar2.add(resolution.nb_node_AStar2);

			State st_stack = resolution.DFS();
			System.out.println(
					"DFS : " + st_stack.board + " " + resolution.nb_node_dfs + " " + resolution.time_dfs + "ms");
			timesSpentDFS.add(resolution.time_dfs);
			nbNodesDFS.add(resolution.nb_node_dfs);

			// State st_queue = resolution.BFS();
			// System.out.println(
			// 		"BFS : " + st_queue.board + " " + resolution.nb_node_bfs + " " + resolution.time_bfs + "ms");
			// timesSpentBFS.add(resolution.time_bfs);
			// nbNodesBFS.add(resolution.nb_node_bfs);
		}
	System.out.println("***********Times spent***********");
	System.out.println("DFS : " + timesSpentDFS);
	System.out.println("BFS : " + timesSpentBFS);
	System.out.println("AStar1 : " + timesSpentAStar1);
	System.out.println("AStar2 : " + timesSpentAStar2);

	System.out.println("***********Number of nodes***********");
	System.out.println("DFS : " + nbNodesDFS);
	System.out.println("BFS : " + nbNodesBFS);
	System.out.println("AStar1 : " + nbNodesAStar1);
	System.out.println("AStar2 : " + nbNodesAStar2);
	}
}
