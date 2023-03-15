import java.util.ArrayList;

public class State {
	ArrayList<Integer> board;
	
	public State() {
		this.board = new ArrayList<Integer>();
	}
	
	public State(ArrayList<Integer> board) {
		this.board = board;
	}
	
	public State copy() {
		ArrayList<Integer> board_copy = (ArrayList<Integer>) this.board.clone();
		State st_copy = new State(board_copy);
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
}