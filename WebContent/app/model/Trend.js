Ext.define('Test.model.Trend', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'time',
			type : 'auto'
		}, {
			name : 'positiveTweetsSize',
			type : 'auto'
		}, {
			name : 'negativeTweetsSize',
			type : 'auto'
		}
	]

});