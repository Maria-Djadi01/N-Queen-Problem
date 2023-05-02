import java.util.ArrayList;

public class Main {
	static double mean(ArrayList<Double> list) {
		double total = 0;
		for (double i : list) {
			total += i;
		}
		return total / list.size();
	}

	static void geneticsExperiments(int max_board_size, int max_population_size, int max_nb_generations) {
		// ArrayList<Integer> population_sizeArray = new ArrayList<Integer>();
		// ArrayList<Integer> nb_generationsArray = new ArrayList<Integer>();
		// ArrayList<Double> crossover_rateArray = new ArrayList<Double>();
		// ArrayList<Double> mutation_rateArray = new ArrayList<Double>();

		// ArrayList<Double> timesSpent = new ArrayList<Double>();
		// ArrayList<Integer> nb_bestSolutionsArray = new ArrayList<Integer>();
		// ArrayList<Double> evaluation_rateArray = new ArrayList<Double>();

		for (int population_size = 200; population_size <= max_population_size; population_size += 200) {
			System.out.println("\n\n\n***********Population size : " + population_size + " ***********\n\n\n");

			ArrayList<Integer> population_sizeArray = new ArrayList<Integer>();
			for (int nb_generations = 250; nb_generations <= max_nb_generations; nb_generations += 250) {
				System.out.println("\n\n***********Number of generations : " + nb_generations + " ***********\n\n");
				for (double crossover_rate = 0.6; crossover_rate <= 0.9; crossover_rate += 0.1) {
					System.out.println("\n***********Crossover rate : " + crossover_rate + " ***********\n");
					ArrayList<Integer> nb_bestSolutionsArray = new ArrayList<Integer>();
					ArrayList<Double> evaluation_rateArray = new ArrayList<Double>();
					ArrayList<Double> timesSpent = new ArrayList<Double>();
					for (double mutation_rate = 0.05; mutation_rate <= 0.3; mutation_rate += 0.05) {
						// System.out.println("***********Mutation rate : " + mutation_rate + " ***********");
						int nb_bestSolutions = 0;
						double evaluation_rate = 0;
						double mean_time_spent = 0;


						for(int board_size=4; board_size<=max_board_size; board_size++) {
							Resolution resolution = new Resolution(board_size);
							int selectionSize = population_size / 2;
							State solution = resolution.geneticAlgorithm(population_size, selectionSize, nb_generations, crossover_rate, mutation_rate);
							if(solution.evaluation() == 0) nb_bestSolutions++;
							evaluation_rate += (solution.evaluation()/ (double) board_size) / (double) (max_board_size-3);
							mean_time_spent += resolution.time_genetic / (max_board_size-3);

							population_sizeArray.add(population_size);
							// nb_generationsArray.add(nb_generations);
							// crossover_rateArray.add(crossover_rate);
							// mutation_rateArray.add(mutation_rate);
						}
						nb_bestSolutionsArray.add(nb_bestSolutions);
						evaluation_rateArray.add(evaluation_rate);
						timesSpent.add(mean_time_spent);
					}
				System.out.println("Number of best solutions: " + nb_bestSolutionsArray);
				System.out.println("Evaluation rate: " + evaluation_rateArray);
				System.out.println("Time spent: " + timesSpent);
				}
			}
			population_sizeArray.add(population_size);
		}

		// System.out.println("Population size: " + population_sizeArray);
		// System.out.println("Number of generations: " + nb_generationsArray);
		// System.out.println("Crossover rate: " + crossover_rateArray);
		// System.out.println("Mutation rate: " + mutation_rateArray);
	}

	static void PSOExperiments(int max_board_size, int max_swarm_size, int max_iter) {
		for(int swarm_size=250; swarm_size<=max_swarm_size; swarm_size+=250) {
			System.out.println("\n\n\n***********Swarm size : " + swarm_size + " ***********\n\n\n");
			for(int iter=200; iter<=max_iter; iter+=200) {
				System.out.println("\n\n***********Number of iterations : " + iter + " ***********\n\n");
				for(double weight=0.4; weight<=0.9; weight+=0.1) {
					System.out.println("\n***********Weight : " + weight + " ***********\n");
					ArrayList<Integer> nb_bestSolutionsArray = new ArrayList<Integer>();
					ArrayList<Double> evaluation_rateArray = new ArrayList<Double>();
					ArrayList<Double> timesSpent = new ArrayList<Double>();
					for(int c=0; c<=4; c++) {
						// System.out.println("***********Mutation rate : " + mutation_rate + " ***********");
						int nb_bestSolutions = 0;
						double evaluation_rate = 0;
						double mean_time_spent = 0;


						for(int board_size=4; board_size<=max_board_size; board_size++) {
							Resolution resolution = new Resolution(board_size);
							State solution = resolution.PSO(swarm_size, iter, weight, c, c);
							if(solution.evaluation() == 0) nb_bestSolutions++;
							evaluation_rate += (solution.evaluation()/ (double) board_size) / (double) (max_board_size-3);
							mean_time_spent += resolution.time_genetic / (max_board_size-3);

							// nb_generationsArray.add(nb_generations);
							// crossover_rateArray.add(crossover_rate);
							// mutation_rateArray.add(mutation_rate);
						}
						nb_bestSolutionsArray.add(nb_bestSolutions);
						evaluation_rateArray.add(evaluation_rate);
						timesSpent.add(mean_time_spent);
					}
					System.out.println("Number of best solutions: " + nb_bestSolutionsArray);
					System.out.println("Evaluation rate: " + evaluation_rateArray);
					System.out.println("Time spent: " + timesSpent);
				}
			}
		}
	}

	static void rum_comparaisons(int max_board_size) {
		ArrayList<Double> timesSpentDFS = new ArrayList<Double>();
		ArrayList<Double> timesSpentBFS = new ArrayList<Double>();
		ArrayList<Double> timesSpentAStar1 = new ArrayList<Double>();
		ArrayList<Double> timesSpentAStar2 = new ArrayList<Double>();
		ArrayList<Double> timesSpentGeneticAlgorithm = new ArrayList<Double>();
		ArrayList<Double> timesSpentPSO = new ArrayList<Double>();

		ArrayList<Integer> nbNodesDFS = new ArrayList<Integer>();
		ArrayList<Integer> nbNodesBFS = new ArrayList<Integer>();
		ArrayList<Integer> nbNodesAStar1 = new ArrayList<Integer>();
		ArrayList<Integer> nbNodesAStar2 = new ArrayList<Integer>();
		ArrayList<Integer> nbNodesGeneticAlgorithm = new ArrayList<Integer>();
		ArrayList<Integer> nbNodesPSO = new ArrayList<Integer>();

		for (int i = 4; i <= max_board_size; i++) {
			Resolution resolution = new Resolution(i);
			System.out.println("***********Board size : " + i + " ***********");

			State st_Astar1 = resolution.AStar1();
			System.out.println("AStar1 : " + st_Astar1.board + " " + resolution.nb_node_AStar1 + " "
					+ resolution.time_AStar1 + "ms");
			timesSpentAStar1.add(resolution.time_AStar1);
			nbNodesAStar1.add(resolution.nb_node_AStar1);

			State st_Astar2 = resolution.AStar2();
			System.out.println("AStar2 : " + st_Astar2.board + " " + resolution.nb_node_AStar2 + " "
					+ resolution.time_AStar2 + "ms");
			timesSpentAStar2.add(resolution.time_AStar2);
			nbNodesAStar2.add(resolution.nb_node_AStar2);

			State st_stack = resolution.DFS();
			System.out.println(
					"DFS : " + st_stack.board + " " + resolution.nb_node_dfs + " " + resolution.time_dfs + "ms");
			timesSpentDFS.add(resolution.time_dfs);
			nbNodesDFS.add(resolution.nb_node_dfs);

			State st_queue = resolution.BFS();
			System.out.println(
					"BFS : " + st_queue.board + " " + resolution.nb_node_bfs + " " + resolution.time_bfs + "ms");
			timesSpentBFS.add(resolution.time_bfs);
			nbNodesBFS.add(resolution.nb_node_bfs);

			State st_genetic = resolution.geneticAlgorithm(1000, 500, 100, 0.8, 0.05);
			System.out.println("Genetic : " + st_genetic.board + " " + st_genetic.evaluation() + " "
					+ resolution.time_genetic + "ms");
			timesSpentGeneticAlgorithm.add(resolution.time_genetic);
			nbNodesGeneticAlgorithm.add(resolution.nb_node_genetic);

			State st_pso = resolution.PSO(1000, 100, 1, 1, 1);
			System.out.println("PSO : " + st_pso.board + " " + st_pso.evaluation() + " " + resolution.time_pso + "ms");
			timesSpentPSO.add(resolution.time_pso);
			nbNodesPSO.add(resolution.nb_node_pso);
		}
		System.out.println("***********Times spent***********");
		System.out.println("DFS : " + timesSpentDFS);
		System.out.println("BFS : " + timesSpentBFS);
		System.out.println("AStar1 : " + timesSpentAStar1);
		System.out.println("AStar2 : " + timesSpentAStar2);
		System.out.println("Genetic Algorithm : " + timesSpentGeneticAlgorithm);
		System.out.println("PSO : " + timesSpentPSO);

		System.out.println("***********Number of nodes***********");
		System.out.println("DFS : " + nbNodesDFS);
		System.out.println("BFS : " + nbNodesBFS);
		System.out.println("AStar1 : " + nbNodesAStar1);
		System.out.println("AStar2 : " + nbNodesAStar2);
		System.out.println("Genetic Algorithm : " + nbNodesGeneticAlgorithm);
		System.out.println("PSO : " + nbNodesPSO);
	}

	public static void main(String[] args) {
		// ArrayList<ArrayList<Double>> nb_generations_exp =
		// run_experiments_on_nb_generations(1000, 100, 10);
		// System.out.println("Number of generations list : " +
		// nb_generations_exp.get(0));
		// System.out.println("Times spent : " + nb_generations_exp.get(1));
		// System.out.println("Evaluations rate : " + nb_generations_exp.get(2));

		// rum_comparaisons(6);
		// geneticsExperiments(1000, 5000, 10000);
		PSOExperiments(10, 5000, 1000);
	}
}
