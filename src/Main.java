
public class Main {

	public static void main(String[] args) {
		Resolution resolution = new Resolution(5);

		State st_Astar1 = resolution.AStar1();
		System.out.println("AStar1 : " + st_Astar1.board + " " + resolution.nb_node_AStar1 + " " + resolution.time_AStar1 + "ms");

		State st_Astar2 = resolution.AStar2();
		System.out.println("AStar2 : " + st_Astar2.board + " " + resolution.nb_node_AStar2 + " " + resolution.time_AStar2 + "ms");

		State st_stack = resolution.DFS();
		System.out.println("DFS : " + st_stack.board  + " " + resolution.nb_node_dfs + " " + resolution.time_dfs + "ms");

		State st_queue = resolution.BFS();
		System.out.println("BFS : " + st_queue.board + " " + resolution.nb_node_bfs + " " + resolution.time_bfs + "ms");
	}
}
