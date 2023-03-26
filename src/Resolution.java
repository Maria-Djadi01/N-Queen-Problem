import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;

public class Resolution { 
	int boardSize;
	int nb_node_dfs = 0;
	int nb_node_bfs = 0;
	int nb_node_AStar1 = 0;
	
	public Resolution(int boardSize) {
		this.boardSize = boardSize;
	}
	
	
	public State DFS() {
		Stack<State> stack = new Stack<State>();
		State init_state = new State();
		ArrayList<State> closed = new ArrayList<State>();
		
		stack.push(init_state);
		while(!stack.isEmpty()) {
			State current_state = stack.pop();
			closed.add(current_state);
			
			if(current_state.board.size() == this.boardSize) return current_state;
			else {
				int row = current_state.board.size();
				for(int col=this.boardSize; col>0; col--) {
					State st_cp = current_state.copy();
					this.nb_node_dfs++;
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
		
		queue.add(init_state);
		while(!queue.isEmpty()) {
			State current_state = queue.remove();
			closed.add(current_state);
			
			if(current_state.board.size() == this.boardSize) return current_state;
			else {
				int row = current_state.board.size();
				for(int col=1; col<=this.boardSize; col++) {
					State st_cp = current_state.copy();
					this.nb_node_bfs++;
					if(st_cp.isSafe(row, col)) {
						st_cp.board.add(col);
						queue.add(st_cp);
					}
				}
			}
		}
		return null;
	}



	public State AStar1() {
		LinkedList<State> open = new LinkedList<State>();
		ArrayList<State> closed = new ArrayList<State>();
		State init_state = new State();
		
		open.add(init_state);
		while(!open.isEmpty()) {
			Collections.sort(open, new Comparator<State>() {
				@Override
				public int compare(State s1, State s2) {
					return Integer.compare(s1.h, s2.h);
				}
			});

			State current_state = open.remove();
			closed.add(current_state);
			
			if(current_state.board.size() == this.boardSize) return current_state;
			else {
				int row = current_state.board.size();
				for(int col=1; col<=this.boardSize; col++) {
					State st_cp = current_state.copy();
					this.nb_node_AStar1++;
					if(!st_cp.isSafe(row, col)) st_cp.h++;
					st_cp.board.add(col);
					open.add(st_cp);
				}
			}
		}
		return null;
	}
}
