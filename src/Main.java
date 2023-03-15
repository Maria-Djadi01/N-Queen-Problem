import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Resolution resolution = new Resolution(4);
		State st_stack = resolution.DFS();
		System.out.println(st_stack.board);
		
		State st_queue = resolution.BFS();
		System.out.println(st_queue.board);
	}

}
