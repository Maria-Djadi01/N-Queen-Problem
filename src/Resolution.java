import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Resolution { 
	int boardSize;
	int nb_node_dfs = 0;
	int nb_node_bfs = 0;
	int nb_node_AStar1 = 0;
	int nb_node_AStar2 = 0;
	int nb_node_genetic = 0;
	int nb_node_pso = 0;

	double time_dfs = 0;
	double time_bfs = 0;
	double time_AStar1 = 0;
	double time_AStar2 = 0;
	double time_genetic = 0;
	double time_pso = 0;

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
			if(current_state.board.size() == this.boardSize && current_state.evaluation() == 0) {
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
			if(current_state.board.size() == this.boardSize && current_state.evaluation() == 0) {
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
			if(current_state.board.size() == this.boardSize && current_state.evaluation() == 0) {
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

	State generateRandomBoard() {
		State st = new State();
		for(int i=0; i<this.boardSize; i++) {
			int col = (int) (Math.random() * this.boardSize) + 1;
			st.board.add(col);
		}
		return st;
	}

	PriorityQueue<State> generateRandomPopulation(int size) {
		PriorityQueue<State> population = new PriorityQueue<State>(new Comparator<State>() {
			@Override
			public int compare(State s1, State s2) {
				return Integer.compare(s1.evaluation(), s2.evaluation());
			}
		});

		for(int i=0; i<size; i++) {
			State st = generateRandomBoard();
			population.add(st);
		}
		return population;
	}

	PriorityQueue<State> selection(PriorityQueue<State> population, int selectionSize) {
		PriorityQueue<State> selected = new PriorityQueue<State>(new Comparator<State>() {
			@Override
			public int compare(State s1, State s2) {
				return Integer.compare(s1.evaluation(), s2.evaluation());
			}
		});

		for(int i=0; i<selectionSize; i++) {
			selected.add(population.poll());
		}
		return selected;
	}

	ArrayList<State> crossover(PriorityQueue<State> selected, double crossOverRate) {
		ArrayList<State> children = new ArrayList<State>();
		ArrayList<State> selected_ArrayList = new ArrayList<State>(selected);
		int nb_pairs = (int)(selected.size()/2);
		for(int i=0; i<nb_pairs; i++) {
			State parent1 = selected_ArrayList.get((int) (Math.random() * selected.size()));
			State parent2 = selected_ArrayList.get((int) (Math.random() * selected.size()));
			if(Math.random() < crossOverRate) {
				State child1 = new State();
				State child2 = new State();

				int cross_point = (int) (Math.random() * this.boardSize);

				for(int j=0; j<cross_point; j++) {
					child1.board.add(parent1.board.get(j));
					child2.board.add(parent2.board.get(j));
				}

				for(int j=cross_point; j<this.boardSize; j++) {
					child1.board.add(parent2.board.get(j));
					child2.board.add(parent1.board.get(j));
				}

				children.add(child1);
				children.add(child2);
			}
			else {
				children.add(parent1);
				children.add(parent2);
			}
		}
		return children;
	}

	ArrayList<State> mutation(ArrayList<State> children, double mutationRate) {
		ArrayList<State> mutated = new ArrayList<State>();
		for(State child : children) {
			if(Math.random() < mutationRate) {
				int row = (int) (Math.random() * this.boardSize);
				int col = (int) (Math.random() * this.boardSize) + 1;
				child.board.set(row, col);
			}
			mutated.add(child);
		}
		return mutated;
	}

	public State geneticAlgorithm(int populationSize, int selectionSize, int nb_generations, double crossOverRate, double mutationRate) {
		double start = System.currentTimeMillis();
		// generate a random population
		PriorityQueue<State> population = generateRandomPopulation(populationSize);
		// keep track of best solution found
		State bestSolution = population.peek();
		// repeat until the population is empty
		for(int i=0 ; i<nb_generations ; i++) {
			// select the best individuals from the population
			PriorityQueue<State> selected = selection(population, selectionSize);
			// generate the children from the selected individuals
			ArrayList<State> children = crossover(selected, crossOverRate);
			// mutate the children
			ArrayList<State> mutated = mutation(children, mutationRate);
			// remove the selected individuals from the population
			population.removeAll(selected);
			// add the mutated children to the population
			population.addAll(mutated);
			// check if one of the individuals in the population is a solution
			if(bestSolution.evaluation() > population.peek().evaluation()) {
				bestSolution = population.peek();
			}
			if(bestSolution.evaluation() == 0) {
				double end = System.currentTimeMillis();
				this.time_genetic = end - start;
				this.nb_node_genetic = nb_generations * populationSize;
				return bestSolution;
			}
		}
		double end = System.currentTimeMillis();
		this.time_genetic = end - start;
		this.nb_node_genetic = nb_generations * populationSize;
		return bestSolution;
	}

	public State selectGBest(ArrayList<State> swarm) {
		State Gbest = swarm.get(0);
		for(int i=1; i<swarm.size(); i++) {
			if(swarm.get(i).evaluation() < Gbest.evaluation()) {
				Gbest = swarm.get(i);
			}
		}
		return Gbest;
	}

	public State PSO(int swarm_size, int max_iter, double weight, double c1, double c2) {
		double start = System.currentTimeMillis();
		// initialize swarm
		ArrayList<State> swarm = new ArrayList<State>(generateRandomPopulation(swarm_size));
		ArrayList<State> PbestArray = new ArrayList<State>();
		ArrayList<ArrayList<Double>> velocityArray = new ArrayList<ArrayList<Double>>();
		// select Gbest
		State Gbest = selectGBest(swarm);
		// initialize Pbest 
		for(int i=0; i<swarm_size; i++) {
			PbestArray.add(swarm.get(i));
		}

		// initialize velocity
		for(int i=0; i<swarm_size; i++) {
			ArrayList<Double> velocity = new ArrayList<Double>();
			for(int j=0; j<this.boardSize; j++) {
				double v = (int) (Math.random() * boardSize) + 1;
				double velocit = weight * v + c1 * Math.random() * (PbestArray.get(i).board.get(j) - swarm.get(i).board.get(j)) + c2 * Math.random() * (Gbest.board.get(j) - swarm.get(i).board.get(j));
				velocity.add(velocit);
			}
			velocityArray.add(velocity);
		}

		for(int i=0; i<max_iter; i++) {
			for(int j=0; j<swarm_size; j++) {
				State Pbest = PbestArray.get(j);
				State particle = swarm.get(j);
				// update velocity
				for(int k=0; k<this.boardSize; k++) {
					double velocity = weight * velocityArray.get(j).get(k) + c1 * Math.random() * (Pbest.board.get(k) - particle.board.get(k)) + c2 * Math.random() * (Gbest.board.get(k) - particle.board.get(k));
					velocityArray.get(j).set(k, velocity);
					int position = (int) (particle.board.get(k) + velocity);
					if(position <= this.boardSize && position >= 1) {
						particle.board.set(k, position);
					}
					else if(position > this.boardSize) {
						particle.board.set(k, this.boardSize);
					}
					else if(position < 1) {
						particle.board.set(k, 1);
					}
				}
				// update Pbest
				if(Pbest.evaluation() > particle.evaluation()) {
					PbestArray.set(j, particle);
				}
			}
			// update Gbest
			Gbest = selectGBest(PbestArray);
		}
		
		this.nb_node_pso = swarm_size;
		this.time_pso = System.currentTimeMillis() - start;
		return Gbest;
	}
}