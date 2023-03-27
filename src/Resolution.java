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
	int nb_node_AStar2 = 0;

	double time_dfs = 0;
	double time_bfs = 0;
	double time_AStar1 = 0;
	double time_AStar2 = 0;

	public Resolution(int boardSize) {
		this.boardSize = boardSize;
	}

	public State DFS() {
		double start = System.currentTimeMillis();
		// DFS stack that contains the nodes to explore
		Stack<State> stack = new Stack<State>();
		// closed list that contains the explored nodes
		ArrayList<State> closed = new ArrayList<State>();
		// initial state that is empty
		State init_state = new State();
		
		// push the initial state to the stack
		stack.push(init_state);
		// repeat until the stack is empty
		while(!stack.isEmpty()) {
			// pop the state from the stack
			State current_state = stack.pop();
			// the popped node will be added to the close list
			closed.add(current_state);
			
			// check if the current state is a solution by checking if its size is equal to the board size we want to build 
			// and if the board is valid (no queens can attack each other)
			if(current_state.board.size() == this.boardSize && current_state.boardValid()) {
				double end = System.currentTimeMillis();
				this.time_dfs = end - start;
				return current_state;
			}
			// else if the current state is not a solution, we will add all the possible states (succcessors) to the stack
			else if(current_state.board.size() != this.boardSize) {
				for(int col=this.boardSize; col>0; col--) {
					State st_cp = current_state.copy();
					this.nb_node_dfs++;
					// if(st_cp.isSafe(row, col)) {
						st_cp.board.add(col);
						stack.push(st_cp);
					// }
				}
			}
		}
		return null;
	}


	public State BFS() {
		double start = System.currentTimeMillis();
		// BFS queue that contains the nodes to explore
		LinkedList<State> queue = new LinkedList<State>();
		// closed list that contains the explored nodes
		State init_state = new State();
		// initial state that is empty
		ArrayList<State> closed = new ArrayList<State>();
		
		// add the initial state to the queue
		queue.add(init_state);
		// repeat until the queue is empty
		while(!queue.isEmpty()) {
			// remove the state from the queue
			State current_state = queue.remove();
			// the removed node will be added to the closed list
			closed.add(current_state);
			
			// check if the current state is a solution by checking if its size is equal to the board size we want to build
			// and if the board is valid (no queens can attack each other)
			if(current_state.board.size() == this.boardSize && current_state.boardValid()) {
				double end = System.currentTimeMillis();
				this.time_bfs = end - start;
				return current_state;
			}
			// else if the current state is not a solution, we will add all the possible states (succcessors) to the queue
			else {
				for(int col=1; col<=this.boardSize; col++) {
					State st_cp = current_state.copy();
					this.nb_node_bfs++;
					// if(st_cp.isSafe(row, col)) {
						st_cp.board.add(col);
						queue.add(st_cp);
					// }
				}
			}
		}
		return null;
	}


	public State AStar1() {
		double start = System.currentTimeMillis();
		// open list (priority queue) that contains the nodes to explore
		LinkedList<State> open = new LinkedList<State>();
		// closed list that contains the explored nodes
		ArrayList<State> closed = new ArrayList<State>();
		// initial state that is empty
		State init_state = new State();
		
		// add the initial state to the open list
		open.add(init_state);
		// repeat until the open list is empty
		while(!open.isEmpty()) {
			// each time before removing the head from the queue, we sort the queue
			// in order to ensure that the head is the one with the lowest heuristic value
			Collections.sort(open, new Comparator<State>() {
				@Override
				public int compare(State s1, State s2) {
					return Integer.compare(s1.h, s2.h);
				}
			});

			// remove the state with the lowest heuristic from the queue
			State current_state = open.remove();
			// the removed node will be added to the closed list
			closed.add(current_state);

			// if the current state is a solution, we return it
			if(current_state.board.size() == this.boardSize) {
				double end = System.currentTimeMillis();
				this.time_AStar1 = end - start;
				return current_state;
			}
			// else if the current state is not a solution, we will add all the possible states (succcessors) to the queue
			else {
				int row = current_state.board.size();
				for(int col=1; col<=this.boardSize; col++) {
					State st_cp = current_state.copy();
					this.nb_node_AStar1++;
					// if the queen is not safe, we increment the heuristic value for the new successor
					if(!st_cp.isSafe(row, col)) st_cp.h++;
					st_cp.board.add(col);
					open.add(st_cp);
				}
			}
		}
		return null;
	}


	public State AStar2() {
		double start = System.currentTimeMillis();	
		// open list (priority queue) that contains the nodes to explore
		LinkedList<State> open = new LinkedList<State>();
		// closed list that contains the explored nodes
		ArrayList<State> closed = new ArrayList<State>();
		// initial state that is empty
		State init_state = new State();
		
		// add the initial state to the open list
		open.add(init_state);
		// repeat until the open list is empty
		while(!open.isEmpty()) {
			// each time before removing the head from the queue, we sort the queue
			// in order to ensure that the head is the state with the lowest heuristic value
			Collections.sort(open, new Comparator<State>() {
				@Override
				public int compare(State s1, State s2) {
					return Integer.compare(s1.h, s2.h);
				}
			});

			// remove the state with the lowest heuristic value from the queue
			State current_state = open.remove();
			// add the state to the closed list
			closed.add(current_state);

			// check if the state is a solution by checking if its size is equal to the board size we want to build
			// and if the board is valid (no queens can attack each other) because it's a non admissible heuristic
			if(current_state.board.size() == this.boardSize && current_state.boardValid()) {
				double end = System.currentTimeMillis();
				this.time_AStar2 = end - start;
				return current_state;
			}
			// else if the current state is not a solution, we will add all the possible states (succcessors) to the queue
			else if(current_state.board.size() != this.boardSize) {
				int row = current_state.board.size();
				for(int col=1; col<=this.boardSize; col++) {
					State st_cp = current_state.copy();
					this.nb_node_AStar2++;
					// the heuristic value will be incremented by the euclidean distance between the new query and the old one
					// this heuristic is always negative so the node with the biggest distance will be prioritized over the others
					st_cp.h = st_cp.h + st_cp.computeEuclideanDistance(row, col);
					st_cp.board.add(col);
					open.add(st_cp);
				}
			}
		}
		return null;
	}
}
