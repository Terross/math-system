import tasksApi from "../api/tasks";

const state = () => ({
    tasks: frontendData.tasks
})

const getters = {
    findTaskById: (state) => (id) => {
        return state.tasks.find(task => task.id.toString() === id.toString())
    },
    findGraphDataByTaskId: (state) => (task) => {
        let elements = [];
        if (task.graph != null) {
            let adj = task.graph.vertexes;
            for (let i = 0; i < adj.length; i++) {
                elements.push(
                    {
                        group: 'nodes',
                        data: { label: '', id: i, name: adj[i].name}
                    }
                )
            }
            let idForEdge = adj.length
            for (let i = 0; i < adj.length; i++) {
                let edge = adj[i].incomingEdges;
                for (let j = 0; j < edge.length; j++) {
                    let edgeSource = elements.find(v => v.data.name === edge[j].fromV && v.group === 'nodes')
                    let edgeTarget = elements.find(v => v.data.name === edge[j].toV  && v.group === 'nodes')
                    if (edgeSource !== null && edgeTarget !== null) {

                        elements.push(
                            {
                                group: 'edges',
                                data: { id: idForEdge, source: edgeSource.data.id, target: edgeTarget.data.id, name:edge[j].weight}
                            }
                        )
                        idForEdge++;
                    }

                }
            }
        }
        return elements;
    }
}

const mutations = {
    addTaskMutation(state, task) {
        state.tasks =[
            ...state.tasks,
            task
        ]
    },
    removeTaskMutation(state, task) {
        const removeIndex = state.tasks.findIndex(item => item.id === task.id)

        if (removeIndex > -1) {
            state.tasks = [
                ...state.tasks.slice(0, removeIndex),
                ...state.tasks.slice(removeIndex + 1)
            ]
        }
    },
    updateTaskMutation(state, task) {

    }
}

const actions = {
    async removeTaskAction({commit}, task) {
        const result = await tasksApi.remove(task.id)

        if (result.ok) {
            commit('removeTaskMutation', task)
        }
    },
    async addTaskAction({commit, state}, task) {

        const result = await tasksApi.add(task)
        const data = await result.json()
        const index = state.tasks.findIndex(item => item.id === data.id)

        if (index > -1) {
            commit('updateTaskMutation', data)
        } else {
            commit('addTaskMutation', data)
        }

    },
    async updateTaskAction({commit}, task) {
        const result = await tasksApi.update(task)
        const data = await result.json()
        commit('updateTaskMutation', data)
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}