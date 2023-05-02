import matplotlib.pyplot as plt
import numpy as np

dfs_time = [1.0, 1.0, 12.0, 35.0, 667.0, 7871.0]
bfs_time = [1.0, 2.0, 32.0, 627.0]
A_Star1_time = [2.0, 0.0, 4.0, 54.0, 212.0, 5963.0, 127083.0, 5714525]
A_Star2_time = [2.0, 1.0, 30.0, 100.0, 421.0, 6567.0]

dfs_nb_nodes = [160, 460, 15330, 52934, 1485576, 14226723]
bfs_nb_nodes = [796, 5695, 132546, 1278256]
A_Star1_nb_nodes = [60, 220, 894, 3584, 15720, 72378, 348150, 1806706]
A_Star2_nb_nodes = [100, 210, 12750, 29225, 427888, 7517808]

fig = plt.figure()
ax = fig.add_subplot()  
ax.plot(np.arange(4, len(dfs_time)+4), dfs_time, color='red')
ax.plot(np.arange(4, len(bfs_time)+4), bfs_time, color='blue')
ax.plot(np.arange(4, len(A_Star1_time)+4), A_Star1_time, color='green')
ax.plot(np.arange(4, len(A_Star2_time)+4), A_Star2_time, color='yellow')
ax.legend(['DFS', 'BFS', 'AStar1', 'AStar2'])
ax.set_xlabel("Taille de l'echiquier")
plt.xticks(np.arange(1, 15, 1))
plt.yticks(np.arange(0, 5714526, 1000000))

plt.show()

