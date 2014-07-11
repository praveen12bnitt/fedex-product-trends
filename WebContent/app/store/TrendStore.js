Ext.define('Test.store.TrendStore', {
	extend : 'Ext.data.Store',
	requires : ['Test.model.Trend'],
	model : 'Test.model.Trend',
	autoLoad: false,
	proxy : {
		type : 'ajax',
		url : 'graph.json',
		reader : {
			type : 'json',
			rootProperty : ''
		}
	}	
});