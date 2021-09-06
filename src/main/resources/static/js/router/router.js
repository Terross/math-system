import Vue from "vue"
import VueRouter from "vue-router"
import TaskConstructor from "../pages/TaskConstructor.vue";
import TaskList from "../pages/TaskList.vue";
import PluginList from "../pages/PluginList.vue"
import Task from "../pages/Task.vue";

Vue.use(VueRouter)

const routes = [
    {path: '/', component: TaskConstructor},
    {path: '/tasks', component: TaskList},
    {path: '/plugins', component: PluginList},
    {path: '/task/:id', component: Task},
    {path: '*', component: TaskConstructor}
]

export default new VueRouter({
    mode: 'history',
    routes
})