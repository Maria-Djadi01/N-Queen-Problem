import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Resolution { 
	int boardSize;
	
	public Resolution(int boardSize) {
		this.boardSize = boardSize;
	}
	
	
	public State DFS() {
		Stack<State> stack = new Stack<State>();
		State init_state = new State();
		ArrayList<State> closed = new ArrayList<State>();
		int nb_state_generated = 0;
		
		stack.push(init_state);
		while(!stack.isEmpty()) {
			State current_state = stack.pop();
			closed.add(current_state);
//			System.out.println(current_state.board);
			
			if(current_state.board.size() == this.boardSize) return current_state;
			else {
				int row = current_state.board.size();
				for(int col=this.boardSize; col>0; col--) {
					State st_cp = current_state.copy();
					nb_state_generated++;
//					System.out.println(st_cp.board);
					if(st_cp.isSafe(row, col)) {
						st_cp.board.add(col);
						stack.push(st_cp);
					}
				}
			}
		}
		return null;
	}
	
	
	
	public State BFS() {
		LinkedList<State> queue = new LinkedList<State>();;
		State init_state = new State();
		ArrayList<State> closed = new ArrayList<State>();
		int nb_state_generated = 0;
		
		queue.add(init_state);
		while(!queue.isEmpty()) {
			State current_state = queue.remove();
			closed.add(current_state);
//			System.out.println(current_state.board);
			
			if(current_state.board.size() == this.boardSize) return current_state;
			else {
				int row = current_state.board.size();
				for(int col=1; col<=this.boardSize; col++) {
					State st_cp = current_state.copy();
					nb_state_generated++;
//					System.out.println(st_cp.board);
					if(st_cp.isSafe(row, col)) {
						st_cp.board.add(col);
						queue.add(st_cp);
					}
				}
			}
		}
		return null;
	}
}
