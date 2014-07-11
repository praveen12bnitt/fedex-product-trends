/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('Test.view.main.TestChart', {
    extend: 'Ext.Panel',
    xtype: 'line-marked',
	requires : ['Test.model.Trend'],
    width: 650,
    initComponent: function() {
        var me = this;
        me.myDataStore = Ext.create('Test.store.TrendStore');
        me.myDataStore.load({params: {itemName: 'anotherValue'}});
		me.items = [{
            xtype: 'cartesian',
            width: '100%',
            height: 500,
            legend: {
                docked: 'right'
            },
            store: this.myDataStore,
            insetPadding: 40,
            interactions: 'itemhighlight',
            sprites: [{
                type: 'text',
                text: 'Trend',
                font: '22px Helvetica',
                width: 100,
                height: 30,
                x: 40, // the sprite x position
                y: 20  // the sprite y position
            }],
            axes: [{
                type: 'numeric',
                fields: ['positiveTweetsSize', 'negativeTweetsSize'],
                position: 'left',
                grid: true,
                minimum: 0
            }, {
                type: 'category',
                fields: 'time',
                position: 'bottom',
                grid: true,
                label: {
                    rotate: {
                        degrees: -45
                    }
                }
            }],
            series: [{
                type: 'line',
                axis: 'left',
                title: 'Positive Tweets',
                xField: 'time',
                yField: 'positiveTweetsSize',
                style: {
                    lineWidth: 4,
					stroke: "green"
                },
                marker: {
                    radius: 4
                },
                highlight: {
                    fillStyle: '#000',
                    radius: 5,
                    lineWidth: 2,
                    strokeStyle: '#fff'
                },
                tooltip: {
                    trackMouse: true,
                    style: 'background: #fff',
                    renderer: function(storeItem, item) {
                        var title = item.series.getTitle();
                        this.setHtml(title + ' for ' + storeItem.get('time') + ': ' + storeItem.get(item.series.getYField()));
                    }
                }
            }, {
                type: 'line',
                axis: 'left',
                title: 'Negative Tweets',
                xField: 'time',
                yField: 'negativeTweetsSize',
                style: {
                    lineWidth: 4,
					stroke: "red"
                },
                marker: {
                    radius: 4
                },
                highlight: {
                    fillStyle: '#000',
                    radius: 5,
                    lineWidth: 2,
                    strokeStyle: '#fff'
                },
                tooltip: {
                    trackMouse: true,
                    style: 'background: #fff',
                    renderer: function(storeItem, item) {
                        var title = item.series.getTitle();
                        this.setHtml(title + ' for ' + storeItem.get('time') + ': ' + storeItem.get(item.series.getYField()));
                    }
                }
            }]
        }];

        this.callParent();
    }
});