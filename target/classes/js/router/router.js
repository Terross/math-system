import Vue from "vue"
import VueRouter from "vue-router"
import TaskConstructor from "../pages/TaskConstructor.vue";
import TaskList from "../pages/TaskList.vue";
import PluginList from "../pages/PluginList.vue"
import Task from "../pages/Task.vue";
import ChangeTask from "../pages/ChangeTask.vue";
import Login from "../pages/Login.vue";
import Register from "../pages/Register.vue";

Vue.use(VueRouter)

const routes = [
    {path: '/', component: TaskConstructor},
    {path: '/tasks', component: TaskList},
    {path: '/plugins', component: PluginList},
    {path: '/task/:id', component: Task},
    {path: '/changeTask/:id', component: ChangeTask},
    {path: '*', component: TaskConstructor},
    {path: '/auth/login', component: Login},
    {path: '/auth/registration', component: Register}
]

export default new VueRouter({
   // mode: 'history',
    routes
})