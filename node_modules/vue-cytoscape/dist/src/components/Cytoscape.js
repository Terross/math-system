import { __decorate } from "tslib";
import { Component, Vue, Prop, Provide } from 'vue-property-decorator';
import cytoscape from 'cytoscape';
let VueCytoscape = class VueCytoscape extends Vue {
    constructor() {
        super(...arguments);
        this.instance = undefined;
        this.resolve = undefined;
        this.reject = undefined;
        this.cy = new Promise((resolve, reject) => {
            this.resolve = resolve;
            this.reject = reject;
        });
    }
    mounted() {
        // create a vue independent element
        const el = document.createElement('div');
        el.setAttribute('id', 'cytoscape-div');
        el.setAttribute('width', '100%');
        el.setAttribute('style', 'min-height: 600px;');
        // add it as a child of the vue managed one
        this.$el.appendChild(el);
        // apply lifecycle hooks
        if (this.preConfig)
            this.preConfig(cytoscape);
        // create cytoscape instance
        const instance = cytoscape({ container: el, ...this.config });
        // register all the component events as cytoscape ones
        const register = (eventType, f) => instance.on(eventType, f);
        for (const [eventType, callback] of Object.entries(this.$listeners)) {
            if (Array.isArray(callback))
                callback.map(f => register(eventType, f));
            else
                register(eventType, callback);
        }
        this.instance = instance;
        // resolve the promise with the object created
        this.resolve(instance);
        if (this.afterCreated)
            this.afterCreated(instance);
    }
    render(h, context) {
        return h('div', this.$slots.default);
    }
};
__decorate([
    Prop()
], VueCytoscape.prototype, "config", void 0);
__decorate([
    Prop({ default: (x) => { } })
], VueCytoscape.prototype, "preConfig", void 0);
__decorate([
    Prop({ default: (x) => { } })
], VueCytoscape.prototype, "afterCreated", void 0);
__decorate([
    Provide()
], VueCytoscape.prototype, "cy", void 0);
VueCytoscape = __decorate([
    Component({})
], VueCytoscape);
export default VueCytoscape;
//# sourceMappingURL=Cytoscape.js.map