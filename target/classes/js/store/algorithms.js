import pluginApi from "../api/plugins";

const state = () => ({
    plugins: frontendData.algorithms,
    answers: []
})

const mutations = {
    addAnswerMutation(state, answers) {
        state.answers = answers
    },
    addPluginMutation(state, plugin) {
        state.plugins = [
            ...state.plugins,
            plugin
        ]
    },
    removePluginMutation(state, plugin) {
        const removeIndex = state.plugins.findIndex(item => item.id === plugin.id)

        if (removeIndex > -1) {
            state.plugins = [
                ...state.plugins.slice(0, removeIndex),
                ...state.plugins.slice(removeIndex + 1)
            ]
        }
    }
}

const actions = {
    async addPluginAction({commit, state}, plugin) {
        const result = await pluginApi.add(plugin)
        const data = await result.json()
        const index = state.plugins.findIndex()

        if (index > -1) {
            console.log(1234556)
        } else {
            commit('addPluginMutation', data)
        }
    }
}

export default {
    namespaced: true,
    state,
    actions,
    mutations
}