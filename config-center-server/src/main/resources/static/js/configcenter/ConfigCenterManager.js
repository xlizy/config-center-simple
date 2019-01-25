/**
 * Created by xlizy on 2018/5/19.
 *
 */
Ext.onReady(function () {

    //region PV(page value)
    //endregion

    //region store(extjs 数据存储器)
    //树store,应用环境树
    var store_forAppEnvTree = new Ext.data.TreeStore({
        proxy: {
            type: 'ajax',
            reader: 'json',
            url: __ctxPath + "/cc/tree",
        },
        autoLoad: true,
    })
    //表格store,配置列表
    var store_forPropertiesGrid = new Ext.data.Store({
        pageSize: 50,
        proxy: {
            type: 'ajax',
            url: __ctxPath + "/properties",
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
    //treePanel,应用环境树
    var getPanel_forTreePanel = function (){
        return new Ext.tree.Panel({
            region: "west",
            id:'appEnvTree',
            title: '应用环境管理',
            width: 270,
            border: 0,
            height: "100%",
            // tbar: ['->', ],
            store: store_forAppEnvTree,
            rootVisible: false,
            collapsible:true,//允许折叠
            listeners : {
                //点击空白区域清空选择
                containerclick : function ( self, e, eOpts )  {
                    this.getSelectionModel().deselectAll();
                    this.getSelectionModel().clearSelections();
                    Ext.getCmp('propertiesGrid').store.getProxy().url = __ctxPath + "/properties/list/" + -1;
                    Ext.queryEnh(store_forPropertiesGrid,{})
                },
                itemclick : function (self, record, item, index, e, eOpts) {
                    var id = record.data.dataId;
                    var type = record.data.type;
                    if('env' == type){
                        Ext.getCmp('propertiesGrid').store.getProxy().url = __ctxPath + "/properties/list/" + id;
                        Ext.queryEnh(store_forPropertiesGrid,{})
                    }else{
                        Ext.getCmp('propertiesGrid').store.getProxy().url = __ctxPath + "/properties/list/" + -1;
                        Ext.queryEnh(store_forPropertiesGrid,{})
                    }
                },
                itemcontextmenu : function (self, record, item, index, e, eOpts) {
                    e.stopEvent();
                    var treeMenu = Ext.create('Ext.menu.Menu',{
                        items: [{
                            text: "新增",
                            iconCls: "msgp-add",
                            // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                            handler: function () {
                                var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                                if(items != undefined && items.length == 1){
                                    if(items[0].data.type == 'app'){
                                        getWin_forEnvAddOrEdit(false).show()
                                        var form = Ext.getCmp('envForm').getForm();
                                        form.findField('appId').setValue(items[0].data.dataId)
                                    }else if(items[0].data.type == 'env'){
                                        Ext.MessageBox.show({
                                            title: '错误提示',
                                            msg: '该节点下不能继续添加节点!',
                                            buttons: Ext.MessageBox.OK,
                                            icon: 'x-message-box-warning'// x-message-box-error,x-message-box-info,x-message-box-question,x-message-box-warning
                                        });
                                    }
                                }else{
                                    getWin_forAppAddOrEdit(false).show()
                                }
                            }
                        },{
                            text: "修改",
                            iconCls: "msgp-edit",
                            // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@editPosition"),
                            handler: function () {
                                var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                                if(items != undefined && items.length == 1){
                                    if(items[0].data.type == 'app'){
                                        // getWin_forAppAddOrEdit(true).show()
                                        var win = getWin_forAppAddOrEdit(true);
                                        var form = Ext.getCmp('appForm').getForm();
                                        form.findField('isEdit').setValue("true");//因为新增修改公用一个form,打个标记
                                        form.load(Ext.BasisGET({
                                            url: __ctxPath + "/app/" + items[0].data.dataId,
                                            success: function (form, action) {
                                                var resObj = Ext.decode(action.response.responseText);
                                                win.show();
                                            },
                                            failure: function (form, action) {
                                                Ext.Msg.alert('提示', '数据加载失败');
                                            }
                                        }));
                                    }else if(items[0].data.type == 'env'){
                                        // getWin_forAppAddOrEdit(true).show()
                                        var win = getWin_forEnvAddOrEdit(true);
                                        var form = Ext.getCmp('envForm').getForm();
                                        form.findField('isEdit').setValue("true");//因为新增修改公用一个form,打个标记
                                        form.load(Ext.BasisGET({
                                            url: __ctxPath + "/env/" + items[0].data.dataId,
                                            success: function (form, action) {
                                                var resObj = Ext.decode(action.response.responseText);
                                                win.show();
                                            },
                                            failure: function (form, action) {
                                                Ext.Msg.alert('提示', '数据加载失败');
                                            }
                                        }));
                                    }
                                }else{
                                    Ext.MessageBox.show({
                                        title: '错误提示',
                                        msg: '请选中一条需要修改的数据!',
                                        buttons: Ext.MessageBox.OK,
                                        icon: 'x-message-box-warning'// x-message-box-error,x-message-box-info,x-message-box-question,x-message-box-warning
                                    });
                                }
                            }
                        },{
                            text: '删除',
                            iconCls: "msgp-delete",
                            handler: function() {
                                var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                                if(items != undefined && items.length == 1){
                                    Ext.Msg.confirm("提示", "确定要删除数据?", function (_btn) {
                                        if (_btn == 'yes') {
                                            var _ids = '';
                                            for (var i = 0; i < items.length; i++) {
                                                _ids += items[i].data.dataId + ",";
                                            }
                                            Ext.Ajax.request(Ext.BasisDELETE({
                                                url: __ctxPath + "/" + items[0].data.type + "/"+_ids,
                                                success: function (response, opts) {
                                                    var resObj = Ext.decode(response.responseText);
                                                    if (resObj.success) {
                                                        Ext.maskHide();
                                                        Ext.queryEnh(store_forAppEnvTree)
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
                                            }));
                                        }
                                    })
                                }
                            }
                        }]
                    });
                    treeMenu.showAt(e.getPoint());
                },
                containercontextmenu: function (self, e, eOpts) {
                    e.stopEvent();
                    var treeMenu = Ext.create('Ext.menu.Menu',{
                        items: [{
                            text: "新增",
                            iconCls: "msgp-add",
                            // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                            handler: function () {
                                var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                                if(items != undefined && items.length == 1){
                                    if(items[0].data.type == 'app'){
                                        getWin_forEnvAddOrEdit(false).show()
                                        var form = Ext.getCmp('envForm').getForm();
                                        form.findField('appId').setValue(items[0].data.dataId)
                                    }else if(items[0].data.type == 'env'){
                                        Ext.MessageBox.show({
                                            title: '错误提示',
                                            msg: '该节点下不能继续添加节点!',
                                            buttons: Ext.MessageBox.OK,
                                            icon: 'x-message-box-warning'// x-message-box-error,x-message-box-info,x-message-box-question,x-message-box-warning
                                        });
                                    }
                                }else{
                                    getWin_forAppAddOrEdit(false).show()
                                }
                            }
                        }]
                    });
                    treeMenu.showAt(e.getPoint());
                }
            }
        })
    }
    //配置信息Panel
    var getPanel_forPropertiesPanel = function (){
        return new Ext.panel.Panel({
            id: 'propertiesPanel',
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
                    id: 'propertiesSearchForm',
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
                        name: 'name',
                        maxLength : 50,
                        enforceMaxLength :  true,
                        fieldLabel: '配置名称'
                    },{
                        name: 'key',
                        maxLength : 50,
                        enforceMaxLength :  true,
                        fieldLabel: '配置key'
                    },{
                        name: 'enable',
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
                id:'propertiesGrid',
                store: store_forPropertiesGrid,
                forceFit: true,
                loadMask: true,
                columnLines: true,
                tbar: [{
                    text: "查询",
                    iconCls: "msgp-search",
                    handler: function () {
                        var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(items != undefined && items.length == 1 && items[0].data.type == 'env'){
                            var form = Ext.getCmp('propertiesSearchForm').getForm();
                            Ext.queryEnh(store_forPropertiesGrid, {
                                name: form.findField('name').getValue(),
                                key: form.findField('key').getValue(),
                                enable: form.findField('enable').getValue(),
                            })
                        }
                    }
                }, {
                    text: "重置",
                    iconCls: "msgp-reset",
                    handler: function () {
                        var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(items != undefined && items.length == 1 && items[0].data.type == 'env'){
                            Ext.getCmp('propertiesSearchForm').getForm().reset();
                            Ext.queryEnh(store_forPropertiesGrid, {})
                        }
                    }
                },{
                    text: "新增",
                    iconCls: "msgp-add",
                    // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                    handler: function () {
                        var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(items != undefined && items.length == 1 && items[0].data.type == 'env'){
                            getWin_forPropertiesAddOrEdit(false).show()
                            var form = Ext.getCmp('propertiesForm').getForm();
                            form.findField('envId').setValue(items[0].data.dataId)
                        }

                    }
                },{
                    text: "修改",
                    iconCls: "msgp-edit",
                    // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                    handler: function () {
                        var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(items != undefined && items.length == 1 && items[0].data.type == 'env'){
                            var items = Ext.getCmp("propertiesGrid").getSelectionModel().getSelection();
                            if (items != undefined && items.length == 1) {
                                var win = getWin_forPropertiesAddOrEdit(true);
                                var form = Ext.getCmp('propertiesForm').getForm();
                                form.findField('isEdit').setValue("true");//因为新增修改公用一个form,打个标记
                                form.load(Ext.BasisGET({
                                    url: __ctxPath + "/properties/" + items[0].data.id,
                                    success: function (form, action) {
                                        var resObj = Ext.decode(action.response.responseText);
                                        win.show();
                                    },
                                    failure: function (form, action) {
                                        Ext.Msg.alert('提示', '数据加载失败');
                                    }
                                }));
                            } else {
                                Ext.MessageBox.show({
                                    title: '错误提示',
                                    msg: '请选中一条需要修改的数据!',
                                    buttons: Ext.MessageBox.OK,
                                    icon: 'x-message-box-warning'// x-message-box-error,x-message-box-info,x-message-box-question,x-message-box-warning
                                });
                            }
                        }
                    }
                },{
                    text: "删除",
                    iconCls: "msgp-delete",
                    // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                    handler: function () {
                        var itemsTree = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(itemsTree != undefined && itemsTree.length == 1 && itemsTree[0].data.type == 'env'){
                            var items = Ext.getCmp("propertiesGrid").getSelectionModel().getSelection();
                            if (items != undefined && items.length >= 1) {
                                Ext.Msg.confirm("提示", "确定要删除数据?", function (_btn) {
                                    if (_btn == 'yes') {
                                        var _ids = '';
                                        for (var i = 0; i < items.length; i++) {
                                            _ids += items[i].data.id + ",";
                                        }
                                        Ext.Ajax.request(Ext.BasisDELETE({
                                            url: __ctxPath + "/properties/"+_ids,
                                            params:{envId:itemsTree[0].data.dataId},
                                            success: function (response, opts) {
                                                var resObj = Ext.decode(response.responseText);
                                                if (resObj.success) {
                                                    Ext.maskHide();
                                                    Ext.queryEnh(store_forPropertiesGrid)
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
                                        }));
                                    }
                                })
                            }
                        }
                    }
                },{
                    text: "启用",
                    iconCls: "msgp-issue",
                    // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                    handler: function () {
                        var itemsTree = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(itemsTree != undefined && itemsTree.length == 1 && itemsTree[0].data.type == 'env'){
                            var items = Ext.getCmp("propertiesGrid").getSelectionModel().getSelection();
                            if (items != undefined && items.length >= 1) {
                                Ext.Msg.confirm("提示", "确定要启用选中的配置?", function (_btn) {
                                    if (_btn == 'yes') {
                                        var _ids = '';
                                        for (var i = 0; i < items.length; i++) {
                                            _ids += items[i].data.id + ",";
                                        }
                                        Ext.Ajax.request(Ext.BasisPOST({
                                            url: __ctxPath + "/properties/setEnable",
                                            params:{envId:itemsTree[0].data.dataId,ids:_ids,enable:'YES'},
                                            success: function (response, opts) {
                                                var resObj = Ext.decode(response.responseText);
                                                if (resObj.success) {
                                                    Ext.maskHide();
                                                    Ext.queryEnh(store_forPropertiesGrid)
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
                                        }));
                                    }
                                })
                            }
                        }
                    }
                },{
                    text: "停用",
                    iconCls: "msgp-cancel-issue",
                    // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                    handler: function () {
                        var itemsTree = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(itemsTree != undefined && itemsTree.length == 1 && itemsTree[0].data.type == 'env'){
                            var items = Ext.getCmp("propertiesGrid").getSelectionModel().getSelection();
                            if (items != undefined && items.length >= 1) {
                                Ext.Msg.confirm("提示", "确定要停用选中的配置?", function (_btn) {
                                    if (_btn == 'yes') {
                                        var _ids = '';
                                        for (var i = 0; i < items.length; i++) {
                                            _ids += items[i].data.id + ",";
                                        }
                                        Ext.Ajax.request(Ext.BasisPOST({
                                            url: __ctxPath + "/properties/setEnable",
                                            params:{envId:itemsTree[0].data.dataId,ids:_ids,enable:'NO'},
                                            success: function (response, opts) {
                                                var resObj = Ext.decode(response.responseText);
                                                if (resObj.success) {
                                                    Ext.maskHide();
                                                    Ext.queryEnh(store_forPropertiesGrid)
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
                                        }));
                                    }
                                })
                            }
                        }
                    }
                },{
                    text: "上传properties",
                    iconCls: "msgp-upload",
                    // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                    handler: function () {
                        var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(items != undefined && items.length == 1 && items[0].data.type == 'env'){
                            getWin_forPropertiesUpload().show();
                            var form = Ext.getCmp('uploadForm').getForm();
                            form.findField('envId').setValue(items[0].data.dataId)
                        }
                    }
                },{
                    text: "下载properties",
                    iconCls: "msgp-download",
                    // hidden:Ext.hideBtnByPer("CMS_articleIssueManager@addPosition"),
                    handler: function () {
                        var items = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(items != undefined && items.length == 1 && items[0].data.type == 'env'){
                            var gitems = Ext.getCmp("propertiesGrid").getSelectionModel().getSelection();
                            console.log(gitems)
                            var _ids = '';
                            for (var i = 0; i < gitems.length; i++) {
                                _ids += gitems[i].data.id + ",";
                            }
                            Ext.downLoad({
                                url: __ctxPath + "/properties/downloadProperties",
                                //下载必须传个参(随便传)
                                params:{p:"",ids:_ids,envId:items[0].data.dataId}
                            })
                        }
                    }
                },{
                    text: "创建快照",
                    iconCls: "msgp-snapshot",
                    handler: function () {
                        var itemsTree = Ext.getCmp('appEnvTree').getSelectionModel().getSelection();
                        if(itemsTree != undefined && itemsTree.length == 1 && itemsTree[0].data.type == 'env'){
                            var winId = "snapshotWin";
                            var n = itemsTree[0];
                            var p = n.parentNode;
                            var text = p.data.text + '_' + n.data.text + '_' + Ext.Date.format(new Date(), "Y/m/d-H:i:s");
                            Ext.maskShow()
                            Ext.TextPrompt({
                                title:'快照名称',
                                id: winId,
                                text: text,
                                handler: function () {
                                    Ext.Ajax.request(Ext.BasisPOST({
                                        url: __ctxPath + "/snapshot",
                                        params:{
                                            envId : itemsTree[0].data.dataId,
                                            name : Ext.getCmp('TextPromptForm').getForm().findField("text").getValue()
                                        },
                                        success: function (response, opts) {
                                            var resObj = Ext.decode(response.responseText);
                                            if (resObj.success) {
                                                Ext.maskHide();
                                                Ext.getCmp(winId).close();
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
                                    }));
                                }
                            }).show()
                        }
                    }
                }],
                bbar: Ext.pageSizeTool(store_forPropertiesGrid),
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
                    dataIndex: 'name',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        if (value != undefined && value != null) {
                            metadata.tdAttr = 'data-qtip="' + value + '"';
                        }
                        return value;
                    }
                },{
                    text: '配置key',
                    dataIndex: 'key',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        if (value != undefined && value != null) {
                            metadata.tdAttr = 'data-qtip="' + value + '"';
                        }
                        return value;
                    }
                },{
                    text: '配置value',
                    dataIndex: 'value',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        if (value != undefined && value != null) {
                            metadata.tdAttr = 'data-qtip="' + value + '"';
                        }
                        return value;
                    }
                },{
                    text: '是否启用',
                    dataIndex: 'enable',
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
                    text: '备注',
                    hidden: true,
                    dataIndex: 'remark',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        if (value != undefined && value != null) {
                            metadata.tdAttr = 'data-qtip="' + value + '"';
                        }
                        return value;
                    }
                },{
                    text: '创建时间',
                    hidden: true,
                    dataIndex: 'createTime',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        try {
                            return value != null ? Ext.Date.format(new Date(value), "Y-m-d H:i:s") : '';
                        } catch (e) {
                            return ''
                        }
                    }
                }, {
                    text: '创建人',
                    hidden: true,
                    dataIndex: 'createUser'
                }, {
                    text: '最后修改时间',
                    hidden: true,
                    dataIndex: 'lastModifyTime',
                    renderer: function (value, metadata, record, rowIndex, columnIndex, store) {
                        try {
                            return value != null ? Ext.Date.format(new Date(value), "Y-m-d H:i:s") : '';
                        } catch (e) {
                            return ''
                        }
                    }
                }, {
                    text: '最后修改人',
                    hidden: true,
                    dataIndex: 'lastModifyUser',
                }]
            }]
        })
    }
    //弹窗,新增或修改环境
    var getWin_forEnvAddOrEdit = function (edit){
        return new Ext.Window({
            title: (edit ? '修改环境信息' : '新增环境'),
            iconCls: (edit ? 'msgp-edit' : 'msgp-add'),
            id: 'envFormWin',
            constrainHeader: true,// 限制窗口只能在容器里拖动
            resizable: false,
            width:350,
            autoHeight: true,
            closable: true,
            modal: true,
            items: [{
                xtype: 'panel',
                border: 0,
                items: [{
                    xtype: 'form',
                    id: 'envForm',
                    layout: "form",
                    border: 0,
                    buttonAlign: "center",
                    defaults: {
                        labelAlign: "right",
                        labelSeparator: ":",
                        labelWidth: 45,
                    },
                    defaultType: "textfield",
                    items:[{
                        name: 'name',
                        maxLength: 50,
                        allowBlank: false,
                        enforceMaxLength: true,
                        fieldLabel: '环境名称',
                    },{
                        name: 'env',
                        maxLength: 30,
                        allowBlank: false,
                        enforceMaxLength: true,
                        fieldLabel: 'env',
                    },{
                        name: 'version',
                        maxLength: 30,
                        allowBlank: false,
                        value:'1.0.0',
                        enforceMaxLength: true,
                        fieldLabel: 'version',
                    },{
                        name: 'cluster',
                        maxLength: 30,
                        allowBlank: false,
                        value:'default',
                        enforceMaxLength: true,
                        fieldLabel: 'cluster',
                    },{
                        xtype:'textarea',
                        name: 'remark',
                        maxLength: 300,
                        height : 30,
                        enforceMaxLength: true,
                        fieldLabel: '备注',
                    },{
                        name: 'id',
                        fieldLabel: 'id',
                        hidden: true
                    },{
                        name: 'appId',
                        fieldLabel: 'appId',
                        hidden: true
                    },{
                        name: 'isEdit',
                        value: 'false',
                        fieldLabel: 'isEdit',
                        hidden: true
                    }],
                    buttons: [{
                        xtype: "button",
                        iconCls: "msgp-save",
                        text: "提交",
                        handler: function () {
                            if (Ext.getCmp('envForm').isValid()) {
                                var form = Ext.getCmp("envForm").getForm()
                                var edit = form.findField('isEdit').getValue();
                                var method = "POST";
                                if ("true" == edit) {
                                    method = "PUT";
                                }
                                Ext.Ajax.request(Ext.BasisRestful({
                                    clientValidation: true,
                                    url: __ctxPath + "/env",
                                    params: form.getValues(),
                                    method:method,
                                    success: function (response, opts) {
                                        var resObj = Ext.decode(response.responseText);
                                        if (resObj.success) {
                                            Ext.maskHide();
                                            Ext.queryEnh(store_forAppEnvTree, {})
                                            Ext.Msg.alert("提示", resObj.message);
                                            Ext.getCmp('envFormWin').close()
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
                                }));
                            }
                        }
                    },{
                        xtype: "button",
                        iconCls: "msgp-cancel",
                        text: "取消",
                        handler: function () {
                            Ext.getCmp('envFormWin').close()
                        }
                    }]
                }]
            }]
        });
    }
    //弹窗,新增或修改应用
    var getWin_forAppAddOrEdit = function (edit){
        return new Ext.Window({
            title: (edit ? '修改应用信息' : '新增应用'),
            iconCls: (edit ? 'msgp-edit' : 'msgp-add'),
            id: 'appFormWin',
            constrainHeader: true,// 限制窗口只能在容器里拖动
            resizable: false,
            width:350,
            autoHeight: true,
            closable: true,
            modal: true,
            items: [{
                xtype: 'panel',
                border: 0,
                items: [{
                    xtype: 'form',
                    id: 'appForm',
                    layout: "form",
                    border: 0,
                    buttonAlign: "center",
                    defaults: {
                        labelAlign: "right",
                        labelSeparator: ":",
                        labelWidth: 45,
                    },
                    defaultType: "textfield",
                    items:[{
                        name: 'name',
                        maxLength: 500,
                        allowBlank: false,
                        enforceMaxLength: true,
                        fieldLabel: '应用名称',
                    },{
                        xtype:'textarea',
                        name: 'remark',
                        maxLength: 300,
                        height : 30,
                        enforceMaxLength: true,
                        fieldLabel: '备注',
                    },{
                        name: 'id',
                        fieldLabel: 'id',
                        hidden: true
                    },{
                        name: 'isEdit',
                        value: 'false',
                        fieldLabel: 'isEdit',
                        hidden: true
                    }],
                    buttons: [{
                        xtype: "button",
                        iconCls: "msgp-save",
                        text: "提交",
                        handler: function () {
                            if (Ext.getCmp('appForm').isValid()) {
                                var form = Ext.getCmp("appForm").getForm()
                                var edit = form.findField('isEdit').getValue();
                                var method = "POST";
                                if ("true" == edit) {
                                    method = "PUT";
                                }
                                Ext.Ajax.request(Ext.BasisRestful({
                                    clientValidation: true,
                                    url: __ctxPath + "/app",
                                    params: form.getValues(),
                                    method:method,
                                    success: function (response, opts) {
                                        var resObj = Ext.decode(response.responseText);
                                        if (resObj.success) {
                                            Ext.maskHide();
                                            Ext.queryEnh(store_forAppEnvTree, {})
                                            Ext.Msg.alert("提示", resObj.message);
                                            Ext.getCmp('appFormWin').close()
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
                                }));
                            }
                        }
                    },{
                        xtype: "button",
                        iconCls: "msgp-cancel",
                        text: "取消",
                        handler: function () {
                            Ext.getCmp('appFormWin').close()
                        }
                    }]
                }]
            }]
        });
    }
    //弹窗,新增或修改配置
    var getWin_forPropertiesAddOrEdit = function (edit){
        return new Ext.Window({
            title: (edit ? '修改配置信息' : '新增配置'),
            iconCls: (edit ? 'msgp-edit' : 'msgp-add'),
            id: 'propertiesFormWin',
            constrainHeader: true,// 限制窗口只能在容器里拖动
            resizable: false,
            width:350,
            autoHeight: true,
            closable: true,
            modal: true,
            items: [{
                xtype: 'panel',
                border: 0,
                items: [{
                    xtype: 'form',
                    id: 'propertiesForm',
                    layout: "form",
                    border: 0,
                    buttonAlign: "center",
                    defaults: {
                        labelAlign: "right",
                        labelSeparator: ":",
                        labelWidth: 45,
                    },
                    defaultType: "textfield",
                    items:[{
                        name: 'key',
                        maxLength: 500,
                        allowBlank: false,
                        enforceMaxLength: true,
                        fieldLabel: '配置key',
                    },{
                        name: 'value',
                        maxLength: 1000,
                        enforceMaxLength: true,
                        fieldLabel: '配置value',
                    },{
                        name: 'name',
                        maxLength: 300,
                        enforceMaxLength: true,
                        fieldLabel: '配置名称',
                    },{
                        name: 'enable',
                        fieldLabel: '是否启用',
                        xtype: 'combo',
                        readOnly: false,
                        displayField: "desc",
                        width: 200,
                        valueField: "value",
                        editable: false,//字面值不可编辑
                        store: store_forEnable,
                        allowBlank: false,
                        value:'NO',
                        triggerAction: 'all',
                    },{
                        xtype:'textarea',
                        name: 'remark',
                        maxLength: 300,
                        height : 30,
                        enforceMaxLength: true,
                        fieldLabel: '备注',
                    },{
                        name: 'id',
                        fieldLabel: 'id',
                        hidden: true
                    },{
                        name: 'envId',
                        fieldLabel: 'envId',
                        hidden: true
                    },{
                        name: 'isEdit',
                        value: 'false',
                        fieldLabel: 'isEdit',
                        hidden: true
                    }],
                    buttons: [{
                        xtype: "button",
                        iconCls: "msgp-save",
                        text: "提交",
                        handler: function () {
                            if (Ext.getCmp('propertiesForm').isValid()) {
                                var form = Ext.getCmp("propertiesForm").getForm()
                                var edit = form.findField('isEdit').getValue();
                                var method = "POST";
                                if ("true" == edit) {
                                    method = "PUT";
                                }
                                Ext.Ajax.request(Ext.BasisRestful({
                                    clientValidation: true,
                                    url: __ctxPath + "/properties",
                                    params: form.getValues(),
                                    method:method,
                                    success: function (response, opts) {
                                        var resObj = Ext.decode(response.responseText);
                                        if (resObj.success) {
                                            Ext.maskHide();
                                            Ext.queryEnh(store_forPropertiesGrid, {})
                                            Ext.Msg.alert("提示", resObj.message);
                                            Ext.getCmp('propertiesFormWin').close()
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
                                }));
                            }
                        }
                    },{
                        xtype: "button",
                        iconCls: "msgp-cancel",
                        text: "取消",
                        handler: function () {
                            Ext.getCmp('propertiesFormWin').close()
                        }
                    }]
                }]
            }]
        })
    }

    var getWin_forPropertiesUpload = function(){
        return new Ext.Window({
            title: '上传配置信息',
            iconCls: 'msgp-upload',
            id: 'uploadFormWin',
            constrainHeader: true,// 限制窗口只能在容器里拖动
            resizable: false,
            width:350,
            autoHeight: true,
            closable: true,
            modal: true,
            items: [{
                xtype: 'panel',
                border: 0,
                items: [{
                    xtype: 'form',
                    id: 'uploadForm',
                    layout: "form",
                    border: 0,
                    buttonAlign: "center",
                    defaults: {
                        labelAlign: "right",
                        labelSeparator: ":",
                        labelWidth: 45,
                    },
                    defaultType: "textfield",
                    items:[{
                        xtype:'filefield',
                        name: 'properties',
                        allowBlank: false,
                        enforceMaxLength: true,
                        fieldLabel: 'properties文件',
                    },{
                        name: 'envId',
                        fieldLabel: 'envId',
                        hidden: true
                    }],
                    buttons: [{
                        xtype: "button",
                        iconCls: "msgp-save",
                        text: "提交",
                        handler: function () {
                            if (Ext.getCmp('uploadForm').isValid()) {
                                var form = Ext.getCmp("uploadForm").getForm()
                                form.submit(Ext.BasisPOST({
                                    clientValidation: true,
                                    url: __ctxPath + "/properties/uploadProperties",
                                    params: form.getValues(),
                                    success: function (response, opts) {
                                        var resObj = opts.result;
                                        if (resObj.success) {
                                            Ext.maskHide();
                                            Ext.queryEnh(store_forPropertiesGrid, {})
                                            Ext.Msg.alert("提示", resObj.message);
                                            Ext.getCmp('uploadFormWin').close()
                                        } else {
                                            Ext.maskHide();
                                            Ext.MessageBox.show({
                                                title: '错误提示',
                                                msg: '上传失败',
                                                buttons: Ext.MessageBox.OK,
                                                icon: 'x-message-box-error'
                                            });
                                        }

                                    }
                                }));
                            }
                        }
                    },{
                        xtype: "button",
                        iconCls: "msgp-cancel",
                        text: "取消",
                        handler: function () {
                            Ext.getCmp('uploadFormWin').close()
                        }
                    }]
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
        items: [getPanel_forTreePanel(),getPanel_forPropertiesPanel()]
    });
    //endregion
})