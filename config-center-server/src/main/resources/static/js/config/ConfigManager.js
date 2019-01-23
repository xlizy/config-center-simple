/**
 * Created by xlizy on 2018/5/19.
 *
 */
Ext.onReady(function () {

    //region PV(page value)
    //endregion

    //region store(extjs 数据存储器)
    //endregion

    //region component(extjs win、panel等组件)
    var getPanel_forConfigPanel = function(){
        return new Ext.form.Panel({
            border: 0,
            width: '100%',
            region: "center",
            autoHeight: true,
            scrollable: true,
            defaults: {
                margin: "50 100 0 100",
            },
            items:[{
                xtype: 'fieldset',
                title: '系统设置',
                items:[{
                    layout: "column",
                    xtype:'form',
                    id:'systemConfigForm',
                    autoHeight: true,
                    border: 0,
                    items:[{
                        xtype: "button",
                        margin: "5 0 5 15",
                        iconCls: "msgp-delete",
                        text: "清空session缓存",
                        // hidden: Ext.hideBtnByPer("CC_configList@clearSessionCache"),
                        handler : function () {
                            Ext.Msg.confirm("提示", "确定要清空session缓存?", function (_btn) {
                                if (_btn == 'yes') {
                                    Ext.Ajax.request(Ext.BasisPOST({
                                        url: __ctxPath + "/config/clearSessionCache",
                                        success: function (response, opts) {
                                            var resObj = Ext.decode(response.responseText);
                                            if (resObj.success) {
                                                Ext.Msg.alert("提示", resObj.message);
                                            } else {
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
                    },{
                        xtype: "button",
                        margin: "5 0 5 15",
                        iconCls: "msgp-clear",
                        text: "清空数据缓存",
                        // hidden: Ext.hideBtnByPer("CC_configList@clearDataCache"),
                        handler : function () {
                            Ext.Msg.confirm("提示", "确定要清空数据缓存?", function (_btn) {
                                if (_btn == 'yes') {
                                    Ext.Ajax.request(Ext.BasisPOST({
                                        url: __ctxPath + "/config/clearDataCache",
                                        success: function (response, opts) {
                                            var resObj = Ext.decode(response.responseText);
                                            if (resObj.success) {
                                                Ext.Msg.alert("提示", resObj.message);
                                            } else {
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
                        /*,{
                            xtype: 'textfield',
                            margin: "5 0 5 15",
                            id: 'ipWhiteList',
                            fieldLabel: '白名单',
                            allowBlank: false,
                            emptyText:'\',\'分割',
                            labelAlign: "right",
                            labelSeparator: ":",
                            width:560,
                            labelWidth:45
                        },{
                            xtype: "button",
                            margin: "5 0 5 3",
                            iconCls: "msgp-save",
                            text: "保存",
                            handler : function () {
                                if (Ext.getCmp('systemConfigForm').isValid()) {
                                    Ext.Ajax.request(Ext.BasisPOST({
                                        url: __ctxPath + "/config/updateConfigCenterData",
                                        params:{ipWhiteList:Ext.getCmp('ipWhiteList').getValue()},
                                        success: function (response, opts) {
                                            var resObj = Ext.decode(response.responseText);
                                            if (resObj.success) {
                                                Ext.loadData()
                                                Ext.Msg.alert("提示", resObj.message);
                                            } else {
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
                        }*/]
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
        items: [getPanel_forConfigPanel()]
    });
    //endregion

})