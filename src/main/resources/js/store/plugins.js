import pluginApi from "../api/plugins";

const state = () => ({
    plugins: frontendData.algorithms
})

const mutations = {
    addPluginMutation(state, plugin) {
        state.plugins = [
            ...state.plugins,
            plugin
        ]
    },
    removePluginMutation(state, id) {
        const removeIndex = state.plugins.findIndex(item => item.id === id)
        if (removeIndex > -1) {
            state.plugins = [
                ...state.plugins.slice(0, removeIndex),
                ...state.plugins.slice(removeIndex + 1)
            ]
        }
    },
    editPluginMutation(state, plugin) {
        const index = state.plugins.findIndex(item => item.id === plugin.id)
        state.plugins[index].name = plugin.name
        state.plugins[index].description = plugin.description
    }
}
export default {
    namespaced: true,
    state,
    mutations
}