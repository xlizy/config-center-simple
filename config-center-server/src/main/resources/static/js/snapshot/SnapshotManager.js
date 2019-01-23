/**
 * Created by xlizy on 2018/6/6.
 *
 */
Ext.onReady(function () {

    //region PV(page value)
    //endregion

    //region store(extjs 数据存储器)
    //树store,应用环境快照树
    var store_forAppEnvSnapTree = new Ext.data.TreeStore({
        proxy: {
            type: 'ajax',
            reader: 'json',
            url: __ctxPath + "/snapshot/tree",
        },
        autoLoad: true,
    })
    //表格store,配置列表
    var store_forSnapshotGrid = new Ext.data.Store({
        pageSize: 50,
        proxy: {
            type: 'ajax',
            url: __ctxPath + "/snapshot",
            reader: {
                type: "json",
                root: "rows",
                totalProperty: "totalCount"
            }
        },
    })
    //下拉框store,账户子类型
    var store_forEnable = new Ext.data.Store({
        pageSize: 50,
        proxy: {
            type: 'ajax',
            url: __ctxPath + "/properties/getEnables",
            reader: {
                type: 'json',
                root: 'data'
            }
        },
        autoLoad: true,
    });
    //endregion

    //region component(extjs win、panel等组件)
    //treePanel,应用环境快照树
    var getPanel_forTreePanel = function (){
        return new Ext.tree.Panel({
            region: "west",
            id:'appSnapTree',
            title: '快照管理',
            width: 370,
            border: 0,
            height: "100%",
            // tbar: ['->', ],
            store: store_forAppEnvSnapTree,
            rootVisible: false,
            collapsible:true,//允许折叠
            listeners : {
                //点击空白区域清空选择
                containerclick : function ( self, e, eOpts )  {
                    this.getSelectionModel().deselectAll();
                    this.getSelectionModel().clearSelections();
                    Ext.getCmp('snapshotGrid').store.getProxy().url = __ctxPath + "/snapshot/list/" + -1;
                    Ext.queryEnh(store_forSnapshotGrid,{})
                },
                itemclick : function (self, record, item, index, e, eOpts) {
                    var id = record.data.dataId;
                    var type = record.data.type;
                    if('snap' == type){
                        Ext.getCmp('snapshotGrid').store.getProxy().url = __ctxPath + "/snapshot/list/" + id;
                        Ext.queryEnh(store_forSnapshotGrid,{})
                    }else{
                        Ext.getCmp('snapshotGrid').store.getProxy().url = __ctxPath + "/snapshot/list/" + -1;
                        Ext.queryEnh(store_forSnapshotGrid,{})
                    }
                },
                itemcontextmenu : function (self, record, item, index, e, eOpts) {
                    var id = record.data.dataId;
                    var type = record.data.type;
                    if('snap' == type){
                        var env = record.parentNode;
                        e.stopEvent();
                        var treeMenu = Ext.create('Ext.menu.Menu',{
                            items:[{
                                text: "恢复",
                                iconCls: "msgp-reset",
                                handler : function () {
                                    Ext.Msg.confirm("提示", "确定要用此快照恢复数据?", function (_btn) {
                                        if (_btn == 'yes') {
                                            Ext.Ajax.request(Ext.BasisPOST({
                                                url: __ctxPath + "/snapshot/restoreSnapshot",
                                                params:{id:id,envId:env.data.dataId},
                                                success: function (response, opts) {
                                                    var resObj = Ext.decode(response.responseText);
                                                    if (resObj.success) {
                                                        Ext.maskHide();
                                                        Ext.Msg.alert("提示", resObj.message);
                                                    } else {
                                                        Ext.maskHide();
                                                        Ext.MessageBox.show({
                                                            title: '错误提示',
                                                            msg: resObj.message,
                                                            buttons: Ext.MessageBox.OK,
                                                            icon: 'x-message-box-error'
                                                        });
                                                    }
                                                }
                                            }))
                                        }
                                    })
                                }
                            },{
                                text: "删除",
                                iconCls: "msgp-delete",
                                handler : function () {
                                    Ext.Msg.confirm("提示", "确定要删除数据?", function (_btn) {
                                        if (_btn == 'yes') {
                                            Ext.Ajax.request(Ext.BasisDELETE({
                                                url: __ctxPath + "/snapshot/"+id,
                                                success: function (response, opts) {
                                                    var resObj = Ext.decode(response.responseText);
                                                    if (resObj.success) {
                                                        Ext.maskHide();
                                                        Ext.queryEnh(store_forAppEnvSnapTree)
                                                        Ext.Msg.alert("提示", resObj.message);
                                                    } else {
                                                        Ext.maskHide();
                                                        Ext.MessageBox.show({
                                                            title: '错误提示',
                                                            msg: resObj.message,
                                                            buttons: Ext.MessageBox.OK,
                                                            icon: 'x-message-box-error'
                                                        });
                                                    }
                                                }
                                            }))
                                        }
                                    })
                                }
                            }]
                        })
                        treeMenu.showAt(e.getPoint());
                    }
                }
            }
        })
    }
    //快照明细Panel
    var getPanel_forSnapshotPanel = function () {
        return new Ext.panel.Panel({
            id: 'snapshotPanel',
            region: 'center',
            width: "100%",
            height: "100%",
            border: 0,
            layout: 'border',
            items:[{
                xtype:'panel',
                height: 70,
                defaults: {
                    margin: "10 0 0 10",
                    selectOnFocus: true
                },
                region: 'north',
                title:'查询条件',
                // border: 0,
                items: [{
                    layout: "column",
                    xtype: "form",
                    id: 'snapshotSearchForm',
                    width: "95%",
                    defaults: {
                        labelAlign: "right",
                        columnWidth: .33,
                        labelSeparator: ":",
                        width: 100,
                    },
                    defaultType: "textfield",
                    bodyStyle: "background:transparent",// 设置为透明,不不妨碍更换主题了
                    border: 0,
                    items:[{
                        name: 'propertiesName',
                        maxLength : 50,
                        enforceMaxLength :  true,
                        fieldLabel: '配置名称'
                    },{
                        name: 'propertiesKey',
                        maxLength : 50,
                        enforceMaxLength :  true,
                        fieldLabel: '配置key'
                    },{
                        name: 'propertiesEnable',
                        fieldLabel: '是否启用',
                        xtype: 'combo',
                        readOnly: false,
                        displayField: "desc",
                        width: 200,
                        valueField: "value",
                        editable: false,//字面值不可编辑
                        store: store_forEnable,
                        triggerAction: 'all',
                    }]
                }]
            },{
                xtype:'gridpanel',
                region: 'center',
                // border: 0,
                id:'snapshotGrid',
                store: store_forSnapshotGrid,
                forceFit: true,
                loadMask: true,
                columnLines: true,
                tbar: [{
                    text: "查询",
                    iconCls: "msgp-search",
                    handler: function () {
                        var items = Ext.getCmp('appSnapTree').getSelectionModel().getSelection();
                        if(items != undefined && items.length == 1 && items[0].data.type == 'snap'){
                            var form = Ext.getCmp('snapshotSearchForm').getForm();
                            Ext.queryEnh(store_forSnapshotGrid, {
                                propertiesName: form.findField('propertiesName').getValue(),
                                propertiesKey: form.findField('propertiesKey').getValue(),
                                propertiesEnable: form.findField('propertiesEnable').getValue(),
                            })
                        }
                    }
                },{
                    text: "重置",
                    iconCls: "msgp-reset",
                    handler: function () {
                        var items = Ext.getCmp('appSnapTree').getSelectionModel().getSelection();
                        if(items != undefined && items.length == 1 && items[0].data.type == 'snap'){
                            Ext.getCmp('snapshotSearchForm').getForm().reset();
                            Ext.queryEnh(store_forSnapshotGrid, {})
                        }
                    }
                }],
                bbar: Ext.pageSizeTool(store_forSnapshotGrid),
                multiSelect: true,
                selModel: {
                    selType: "checkboxmodel"
                },
                viewConfig: {
                    enableTextSelection: true,
                },
                columns:[{
                    hidden: true,
                    text: '主键',
                    dataIndex: 'id'
                },{
                    text: '配置名称',
                    dataIndex: 'propertiesName',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        if (value != undefined && value != null) {
                            metadata.tdAttr = 'data-qtip="' + value + '"';
                        }
                        return value;
                    }
                },{
                    text: '配置key',
                    dataIndex: 'propertiesKey',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        if (value != undefined && value != null) {
                            metadata.tdAttr = 'data-qtip="' + value + '"';
                        }
                        return value;
                    }
                },{
                    text: '配置value',
                    dataIndex: 'propertiesValue',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        if (value != undefined && value != null) {
                            metadata.tdAttr = 'data-qtip="' + value + '"';
                        }
                        return value;
                    }
                },{
                    text: '是否启用',
                    dataIndex: 'propertiesEnable',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        if(value == '启用'){
                            return '<span style="color: #135f10">' + value +'</span>';
                        }else if(value == '未启用'){
                            return '<span style="color: #d18944">' + value +'</span>';
                        }else{
                            return value
                        }
                    }
                },{
                    text: '创建时间',
                    dataIndex: 'createTime',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        try {
                            return value != null ? Ext.Date.format(new Date(value), "Y-m-d H:i:s") : '';
                        } catch (e) {
                            return ''
                        }
                    }
                },{
                    text: '创建人',
                    dataIndex: 'createUser'
                },{
                    text: '最后修改时间',
                    dataIndex: 'lastModifyTime',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        try {
                            return value != null ? Ext.Date.format(new Date(value), "Y-m-d H:i:s") : '';
                        } catch (e) {
                            return ''
                        }
                    }
                },{
                    text: '最后修改人',
                    dataIndex: 'lastModifyUser',
                }]
            }]
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
        items: [getPanel_forTreePanel(),getPanel_forSnapshotPanel()]
    });
    //endregion
})
