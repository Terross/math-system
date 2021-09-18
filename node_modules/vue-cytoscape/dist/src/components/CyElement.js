import { __decorate } from "tslib";
import { Component, Vue, Prop, Inject, Watch } from 'vue-property-decorator';
let CyElement = class CyElement extends Vue {
    constructor() {
        super();
        this.instance = undefined;
        this.selector = '';
        this.id = this.definition.data.id;
        if (this.id)
            this.selector = `#${this.id}`;
        this.cy.then(this.configure);
    }
    configure(cy) {
        this.instance = cy;
        const ele = this.add();
        if (!this.id) {
            this.id = ele.data().id;
            this.selector = `#${this.id}`;
        }
    }
    add() {
        const instance = this.instance;
        // register all the component events as cytoscape ones
        const register = (eventType, f) => instance.on(eventType, this.selector, f);
        for (const [eventType, callback] of Object.entries(this.$listeners)) {
            if (Array.isArray(callback))
                callback.map(f => register(eventType, f));
            else
                register(eventType, callback);
        }
        // if sync is on, track position
        if (this.sync) {
            instance.on('drag', this.selector, event => {
                /*  Note: Cytoscape behaves badly when ele.position is an observer object. The underlying
                    data may change, which adjust edge target coordinates, without re-drawing the node.
        
                    In the definition below, and in the position watcher, JSON.parse(JSON.stringify())
                    returns a raw object. Here, "definition.position" is an observer because of Vue, and
                    event.target.position() seems to be an observer also. Without this strip, we end up with
                    an observer of an observer after a drag event, one of which is stripped out in the
                    watcher, creating the same problem we had initially.
                */
                // strip observers from the event position
                const pos = JSON.parse(JSON.stringify(event.target.position()));
                // update definition object
                this.definition.position = pos;
            });
        }
        // strip observers from the original definition
        let def = JSON.parse(JSON.stringify(this.definition));
        // add the element to cytoscape
        const ele = instance.add(def)[0];
        return ele;
    }
    beforeDestroy() {
        const instance = this.instance;
        instance.remove(this.selector);
    }
    get eleData() {
        return this.definition.data;
    }
    get position() {
        return this.definition.position;
    }
    onDataChange(data) {
        const instance = this.instance;
        const ele = instance.getElementById(this.id);
        ele.data(data);
    }
    onPositionChange(position = null) {
        const instance = this.instance;
        const ele = instance.getElementById(this.id);
        ele.position(JSON.parse(JSON.stringify(position)));
    }
    render(h) {
        // do nothing
    }
};
__decorate([
    Prop()
], CyElement.prototype, "definition", void 0);
__decorate([
    Inject()
], CyElement.prototype, "cy", void 0);
__decorate([
    Prop({ default: false })
], CyElement.prototype, "sync", void 0);
__decorate([
    Watch('eleData', { deep: true })
], CyElement.prototype, "onDataChange", null);
__decorate([
    Watch('position', { deep: true })
], CyElement.prototype, "onPositionChange", null);
CyElement = __decorate([
    Component({})
], CyElement);
export default CyElement;
//# sourceMappingURL=CyElement.js.map