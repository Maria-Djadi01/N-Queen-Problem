import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Resolution resolution = new Resolution(4);
		State st_stack = resolution.DFS();
		System.out.println(st_stack.board  + " " + resolution.nb_node_dfs);
		
		State st_queue = resolution.BFS();
		System.out.println(st_queue.board + " " + resolution.nb_node_bfs);

		State st_Astar1 = resolution.AStar1();
		System.out.println(st_Astar1.board + " " + resolution.nb_node_AStar1);

		
	}

}
