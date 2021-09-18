import Vue from "vue"
import Vuex from "vuex"
import algorithms from "./algorithms";
import tasks from "./tasks";
import constructorGraph from "./constructorGraph";

Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        plugins: algorithms,
        tasks: tasks,
        constructorGraph: constructorGraph
    }
})