export default {
    directed: [
        {
            selector: 'node[label]',
            style: {
                'content': 'data(label)',
            }
        },
        {
            "selector": ".multiline-manual",
            "style": {
                "text-wrap": "wrap",
                "text-valign": "center",
                "text-halign": "left"
            }
        },
        {
            selector: 'edge[label]',
            style: {
                'curve-style': 'bezier',
                'target-arrow-shape': 'triangle',
                'content': 'data(label)',
            }
        },
        {
            selector: 'node[color]',
            style: {
                'background-color': 'data(color)',
            }
        },
        {
            selector: 'edge',
            style: {
                'curve-style': 'bezier',
                'target-arrow-shape': 'triangle'
            }
        },
        {
            selector: 'edge[color]',
            style: {
                'curve-style': 'bezier',
                'target-arrow-shape': 'triangle',
                'line-color' : 'data(color)',
                'background-color' : 'data(color)',
                'target-arrow-color': 'data(color)',

            }
        },
        {
            selector: 'edge[label]',
            style: {
                "text-wrap": "wrap",
                "text-valign": "center",
                'content': 'data(label)'
            }
        },
        {
            selector: ':selected',
            style: {
                "border-width": 2,
                "border-color": "blue"
            }
        },
        {
            selector: '.eh-handle',
            style: {
                'background-color': 'red',
                'width': 12,
                'height': 12,
                'shape': 'ellipse',
                'overlay-opacity': 0,
                'border-width': 12, // makes the handle easier to hit
                'border-opacity': 0
            }
        },
        {
            selector: '.eh-hover',
            style: {
                'background-color': 'red'
            }
        },
        {
            selector: '.eh-source',
            style: {
                'border-width': 2,
                'border-color': 'red'
            }
        },
        {
            selector: '.eh-target',
            style: {
                'border-width': 2,
                'border-color': 'red'
            }
        },
        {
            selector: '.eh-preview, .eh-ghost-edge',
            style: {
                'background-color': 'red',
                'line-color': 'red',
                'target-arrow-color': 'red',
                'source-arrow-color': 'red'
            }
        },
        {
            selector: '.eh-ghost-edge.eh-preview-active',
            style: {
                'opacity': 0
            }
        }],
    undirected: [
        {
            selector: 'node[name]',
            style: {
                'content': 'data(name)',
            }
        },
        {
            selector: 'node[color]',
            style: {
                'background-color': 'data(color)',
            }
        },
        {
            selector: 'edge',
            style: {
                'curve-style': 'bezier',
            }
        },
        {
            selector: 'edge[weight]',
            style: {
                'curve-style': 'bezier',
                'content': 'data(weight)',
            }
        },
        {
            selector: ':selected',
            style: {
                "border-width": 2,
                "border-color": "blue"
            }
        },
        {
            selector: '.eh-handle',
            style: {
                'background-color': 'red',
                'width': 12,
                'height': 12,
                'shape': 'ellipse',
                'overlay-opacity': 0,
                'border-width': 12, // makes the handle easier to hit
                'border-opacity': 0
            }
        },
        {
            selector: '.eh-hover',
            style: {
                'background-color': 'red'
            }
        },
        {
            selector: '.eh-source',
            style: {
                'border-width': 2,
                'border-color': 'red'
            }
        },
        {
            selector: '.eh-target',
            style: {
                'border-width': 2,
                'border-color': 'red'
            }
        },
        {
            selector: '.eh-preview, .eh-ghost-edge',
            style: {
                'background-color': 'red',
                'line-color': 'red',
            }
        },
        {
            selector: '.eh-ghost-edge.eh-preview-active',
            style: {
                'opacity': 0
            }
        }]
}