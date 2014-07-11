/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('Test.view.main.Main', {
	extend : 'Ext.container.Container',
	requires : ['Test.view.main.ItemTrendChart'],

	xtype : 'app-main',

	controller : 'main',
	viewModel : {
		type : 'main'
	},

	layout : {
		type : 'vbox',
		align : 'stretch'
	},

	initComponent : function () {
		var me = this;
		var queryParams = Ext.Object.fromQueryString(document.location.search); // returns {foo: 1, bar: 2}
		var itemName = queryParams.itemName;
		// do a request and get item details
		Ext.Ajax.request({
			method : 'GET',
			url : 'itemdtls.json?itemName=' + itemName,
			success : function (response) {
				var resObj = Ext.JSON.decode(response.responseText)[0];
				var contents = [{
						xtype : 'container',
						items : [{
								xtype : 'displayfield',
								fieldLabel : 'NAME',
								value : resObj.itemName
							}, {
								xtype : 'displayfield',
								fieldLabel : 'Description',
								value : resObj.itemDesc
							}, {
								xtype : 'displayfield',
								fieldLabel : 'Rank',
								value : resObj.rank
							}
						]
					}, {
						xtype : 'line-marked',
						itemName : itemName
					}
				];
				me.add(contents);
			},
			failure : function (response) {}
		});
		me.callParent(arguments);		
	},

});