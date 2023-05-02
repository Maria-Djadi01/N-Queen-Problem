import java.util.ArrayList;

public class State {
	// the board is presented by an array where the index is the row and the value is the column
	ArrayList<Integer> board;
	// the heuristic value of the state
	int h;
	
	// initialization constructor
	public State() {
		this.board = new ArrayList<Integer>();
		this.h = 0;
	}
	
	// constructor
	public State(ArrayList<Integer> board, int h) {
		this.board = board;
		this.h = h;
	}
	

	// method that returns a copy of this state
	public State copy() {
		ArrayList<Integer> board_copy = (ArrayList<Integer>) this.board.clone();
		State st_copy = new State(board_copy, this.h);
		return st_copy;
	}

	// method that returns if the new added queen is safe or not
	public boolean isSafe(int row, int col) {
	    if (row == 0) return true;
	    
	    // Check if the queen is safe vertically
	    if (this.board.contains(col)) return false;

	    // Check if the queen is safe diagonally
	    for (int r = 0; r < row; r++) {
	        int c = this.board.get(r);
	        if (Math.abs(row - r) == Math.abs(col - c) || row + col == r + c) return false;
	    }
	    return true;
	}


	// method that checks if this board is a correct solution
	public int evaluation() {
		int nb_queens_under_attack = 0;
		for(int row=1; row<=this.board.size(); row++) {
			int col = this.board.get(row-1);
			ArrayList<Integer> subBoard = new ArrayList<Integer>(this.board.subList(0, row-1));
			if(subBoard.contains(col)) nb_queens_under_attack++;
			else {
				for(int r=row+1; r<=this.board.size(); r++) {
					int c = this.board.get(r-1);
					if(Math.abs(row - r) == Math.abs(col - c) || row + col == r + c) nb_queens_under_attack++;
				}
			}
		}
		return nb_queens_under_attack;
	}

	// method that returns the euclidean distance between the new added query and the last one
	public int computeEuclideanDistance(int row, int col) {
		int distance;
		if(this.board.size() == 0) distance = (int) (Math.sqrt(Math.pow(row - 1, 2) + Math.pow(col - 1, 2)) * (-1));
		else distance = (int) (Math.sqrt(Math.pow(row - this.board.size(), 2) + Math.pow(col - this.board.get(this.board.size()-1), 2)) * (-1));
		return distance;
	}
}
