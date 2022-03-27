export default function(elem) {
    return [
        {
            fillColor: 'red',
            content: '',
            select: (ele) => {
                elem.changeElemColor(ele, 'red')
            }
        },
        {
            fillColor: 'pink',
            content: '',
            select: (ele) => {
                elem.changeElemColor(ele, 'pink')
            }
        },
        {
            fillColor: 'blue',
            content: '',
            select: (ele) => {
                elem.changeElemColor(ele, 'blue')
            }
        },
        {
            fillColor: 'green',
            content: '',
            select: (ele) => {
                elem.changeElemColor(ele, 'green')
            }
        },
        {
            fillColor: 'yellow',
            content: '',
            select: (ele) => {
                elem.changeElemColor(ele, 'yellow')
            }
        },
        {
            fillColor: 'brown',
            content: '',
            select: (ele) => {
                elem.changeElemColor(ele, 'brown')
            }
        },
        {
            fillColor: 'gray',
            content: '',
            select: (ele) => {
                elem.changeElemColor(ele, 'gray')
            }
        },
    ]
}