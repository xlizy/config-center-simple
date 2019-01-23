/**
 * Created by xlizy on 2018/5/29.
 *
 */
Ext.onReady(function () {

    //region PV(page value)
    //endregion

    //region store(extjs 数据存储器)
    var store_forClientTree = new Ext.data.TreeStore({
        proxy: {
            type: 'ajax',
            reader: 'json',
            url: __ctxPath + "/client/tree",
        },
        autoLoad: true,
    })
    //endregion

    //region component(extjs win、panel等组件)
    var getPanel_forClientPanel = function (){
        return new Ext.tree.Panel({
            region: "center",
            id:'clientTree',
            title: '客户端连接详情',
            border: 0,
            height: "100%",
            tbar: [{
                text: "查看客户端信息",
                iconCls: "msgp-search",
                handler: function () {
                    Ext.queryEnh(store_forClientTree, {})
                }
            }],
            store: store_forClientTree,
            rootVisible: false,
            collapsible:false,//允许折叠
        })
    }
    //endregion

    //region common func for page(本页面公用方法)
    //endregion

    //region Ext.container.Viewport
    Ext.create('Ext.container.Viewport', {
        layout: 'border',
        width: "100%",
        id: 'MainPanel',
        height: "100%",
        items: [getPanel_forClientPanel()]
    });
    //endregion

})