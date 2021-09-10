import Vue from "vue";

const graph = Vue.resource('/graph/api{/id}')

export default {
    add: vertex => graph.save(({}, vertex)),
    update: vertex => graph.update({id: vertex.id}, vertex),
    remove: vertex => graph.remove({id: vertex.id})
}