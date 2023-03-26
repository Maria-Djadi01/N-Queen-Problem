import java.util.ArrayList;

public class State {
	ArrayList<Integer> board;
	int h;
	
	public State() {
		this.board = new ArrayList<Integer>();
		this.h = 0;
	}
	
	public State(ArrayList<Integer> board, int h) {
		this.board = board;
		this.h = h;
	}
	
	public State copy() {
		ArrayList<Integer> board_copy = (ArrayList<Integer>) this.board.clone();
		State st_copy = new State(board_copy, this.h);
		return st_copy;
	}
	
	public boolean isSafe(int row, int col) {
	    if (row == 0) return true;
	    
	    // Check if the queen is safe vertically
	    if (this.board.contains(col)) return false;

	    // Check if the queen is safe diagonally
	    for (int r = 0; r < row; r++) {
	        int c = this.board.get(r);
	        if (Math.abs(row - r) == Math.abs(col - c) || row + col == r + c) {
	            return false;
	        }
	    }
	    return true;
	}

	public boolean boardValid() {
		for(int row=0; row<this.board.size(); row++) {
			int col = this.board.get(row);
			if(!this.isSafe(row, col)) {
				return false;
			}
		}
		return true;
	}
}
