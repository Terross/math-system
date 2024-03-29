import Vue from "vue";
import {HTTP} from "../axios/http-common";
const tasks = Vue.resource('/api/v1/all/task/{/id}')

export default {
    add: task => tasks.save(({}, task)),
    update: task => tasks.update(({id: task.id}, task)),
    remove: id => tasks.remove({id : id})
}