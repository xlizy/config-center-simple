/**
 * Created by xlizy on 2018/3/13.
 *
 */
//开启提示条
Ext.QuickTips.init();
//通用查询方法
Ext.queryEnh = function(store, params, page) {
    store.currentPage = 1;// 查询后默认显示第一页
    if(params == undefined || params == null){
        params = {}
    }
    if(page != undefined && page != null && page.limit != undefined && page.limit != null && page.start != undefined && page.start != null ){
        store.setConfig("pageSize",page.limit);
        store.load({
            params : page
        })
    }else{
        store.on("beforeload", function() {
            store.proxy.extraParams = {};
            Ext.apply(store.proxy.extraParams, {});
            Ext.apply(store.proxy.extraParams, params);
        });
        store.load();
    }
}

Ext.pageSizeTool = function (store) {
    return new Ext.toolbar.Paging({
        store: store,
        dock: "bottom",
        items: [
            '-',
            '每页显示条数:',
            {
                xtype : 'combo',
                width: 100,
                mode : 'local',
                value: store.config.pageSize,
                listWidth     : 100,
                triggerAction : 'all',
                displayField  : 'size',
                valueField    : 'size',
                editable      : false,
                forceSelection: true,
                store:{
                    type:"array",
                    fields:["size"],
                    data:[
                        [5],
                        [10],
                        [15],
                        [25],
                        [50],
                        [100],
                        [500],
                        [2000],
                        [5000],
                        [0x5F5E100]
                    ]
                },
                listeners : {
                    change : {
                        fn : function ( self,newValue,oldValue,eOpts) {
                            Ext.queryEnh(self.up("gridpanel").store,{},{"start":0,"limit":newValue});
                        }
                    }
                }
            }
        ],
        displayInfo: true
    })
}


/**
 * 加载等待效果
 */
var AV_MASK_LODING = null;
Ext.maskShow = function() {
    if(AV_MASK_LODING == null){
        AV_MASK_LODING = new Ext.LoadMask({
            msg : "正在处理中，请等待...",
            target : Ext.getCmp('MainPanel')
        });
    }
    AV_MASK_LODING.show();
}
Ext.maskHide = function() {
    if(AV_MASK_LODING != undefined && AV_MASK_LODING != null){
        AV_MASK_LODING.hide();
    }
}

/**
* 翻译数据
* 从store里筛选匹配数据
* */
Ext.translationFromStore = function (store,value) {
    var res = '数据翻译失败-原数据:'+value;

    try {
        if(store.loadCount < 1){
            Ext.sleep(1000)
            console.log("休息了")
            return Ext.translationFromStore(store,value)
        }
        var sd = []
        if (store.config.proxy){
            sd = store.data.items;
            for(var i = 0; i < sd.length; i++){
                var data = sd[i].data;
                if((value == data.value) || (value != undefined && value != null && value.toLowerCase() == data.value.toLowerCase())){
                    res = data.desc;
                    ok = true;
                    break;
                }
            }
        } else {
            sd = store.proxy.data.data
            for(var i = 0; i < sd.length; i++){
                var data = sd[i];
                if((value == data.value) || (value != undefined && value != null && value.toLowerCase() == data.value.toLowerCase())){
                    res = data.desc;
                    ok = true;
                    break;
                }
            }
        }
    } catch (e) {
        // console.error(e)
    }
    return res;
}

Ext.BasisGET = function (options){
    var config = {
        waitMsg : '正在进行处理,请稍后...',
        url : options.url,
        async : options.async!=false ? true : false,//false为同步
        method : "GET",
        timeout : 300000,
        success : function(response, opts) {
            options.success(response, opts)
        },
        failure : function(response, opts) {
            Ext.BasicFailure(options,response, opts);
        }
    }
    if(options.params != undefined && options.params != null){
        config.params = options.params;
    }
    if(options.clientValidation != undefined && options.clientValidation != null){
        config.clientValidation = options.clientValidation;
    }
    return config
}

/**
 * Ajax-POST提交
 * */
Ext.BasisPOST = function (options){
    var config = {
        waitMsg : '正在进行处理,请稍后...',
        url : options.url,
        async : options.async!=false ? true : false,//false为同步
        method : "POST",
        timeout : 10000,
        success : function(response, opts) {
            options.success(response, opts)
        },
        failure : function(response, opts) {
            Ext.BasicFailure(options,response, opts);
        }
    }
    if(options.params != undefined && options.params != null){
        config.params = options.params;
    }
    if(options.clientValidation != undefined && options.clientValidation != null){
        config.clientValidation = options.clientValidation;
    }
    return config
}

/**
 * Ajax-PUT提交
 * */
Ext.BasisPUT = function (options){
    var config = {
        waitMsg : '正在进行处理,请稍后...',
        url : options.url,
        async : options.async!=false ? true : false,//false为同步
        method : "PUT",
        timeout : 10000,
        success : function(response, opts) {
            options.success(response, opts)
        },
        failure : function(response, opts) {
            Ext.BasicFailure(options,response, opts);
        }
    }
    if(options.params != undefined && options.params != null){
        config.params = options.params;
    }
    if(options.clientValidation != undefined && options.clientValidation != null){
        config.clientValidation = options.clientValidation;
    }
    return config
}

/**
 * Ajax-DELETE提交
 * */
Ext.BasisDELETE = function (options){
    var config = {
        waitMsg : '正在进行处理,请稍后...',
        url : options.url,
        async : options.async!=false ? true : false,//false为同步
        method : "DELETE",
        timeout : 10000,
        success : function(response, opts) {
            options.success(response, opts)
        },
        failure : function(response, opts) {
            Ext.BasicFailure(options,response, opts);
        }
    }
    if(options.params != undefined && options.params != null){
        config.params = options.params;
    }
    if(options.clientValidation != undefined && options.clientValidation != null){
        config.clientValidation = options.clientValidation;
    }
    return config
}

/**
 * Ajax-提交,必须指定method
 * */
Ext.BasisRestful = function (options){
    var config = {
        waitMsg : '正在进行处理,请稍后...',
        url : options.url,
        async : options.async!=false ? true : false,//false为同步
        method : options.method,
        timeout : 10000,
        success : function(response, opts) {
            options.success(response, opts)
        },
        failure : function(response, opts) {
            Ext.BasicFailure(options,response, opts);
        }
    }
    if(options.params != undefined && options.params != null){
        config.params = options.params;
    }
    if(options.clientValidation != undefined && options.clientValidation != null){
        config.clientValidation = options.clientValidation;
    }
    return config
}

Ext.BasicFailure = function (options,response,opts) {
    if(response != undefined && 403 == response.status){
        Ext.Msg.show({
            title : '错误提示',
            msg : '您无权获取或修改相关数据',
            buttons : Ext.MessageBox.OK,
            icon: Ext.MessageBox.ERROR
        });
        Ext.maskHide();
    }else if(404 == response.status){
        try{
            console.error("找不到"+options.url)
        }catch (e){}
        Ext.Msg.show({
            title : '错误提示',
            msg : '找不到指定资源,请联系管理员',
            buttons : Ext.MessageBox.OK,
            icon: Ext.MessageBox.ERROR
        });
        Ext.maskHide();
    }else{
        try {
            console.log(response)
            var resObj = Ext.decode(response.responseText);
            var msg = "系统繁忙请稍后再试.....";
            var btn = Ext.MessageBox.OK;
            var icon = "x-message-box-warning";
            if(resObj != undefined){
                if(resObj.result == 'systemError'){
                    msg = resObj.msg;
                    icon= Ext.MessageBox.ERROR
                }else if(resObj.success == false){
                    msg = resObj.message
                }
            }else if(opts.result != undefined){
                if(opts.result.result == 'systemError'){
                    msg = resObj.msg;
                    icon= Ext.MessageBox.ERROR
                }else if(opts.result.success == false){
                    msg = opts.result.message
                }
            }else if(opts.response.responseText != undefined){
                var t = Ext.decode(opts.response.responseText);
                msg = t.msg;
                icon= Ext.MessageBox.ERROR
            }
            console.log(resObj)
            Ext.maskHide();
            Ext.MessageBox.show({
                title: '错误提示',
                msg: msg,
                buttons: btn,
                icon: icon
            });

        }catch (e){
            console.error(e)
            console.error(options)
            console.error(response)
            console.error(opts)
            if(options.failure != undefined){
                options.failure(response, opts)
            }else{
                Ext.Msg.show({
                    title : '错误提示',
                    msg : '系统繁忙请稍后再试...',
                    buttons : Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                });
            }
        }
    }
    Ext.maskHide();
}

Ext.getDateTime = function () {
    return new Date().getMilliseconds()
}

Ext.NumPrompt = function (config) {
    return new Ext.Window({
        title: config.title,
        id: config.id,
        constrainHeader: true,// 限制窗口只能在容器里拖动
        resizable: false,
        width:223,
        closable: true,
        modal: true,
        items: [{
            xtype:'form',
            id:'NumPromptForm',
            border: 0,
            padding:'5 5 5 5',
            buttonAlign: "center",
            bodyStyle: "background:transparent",
            items:[{
                name: 'number',
                maxLength : 10,
                width:200,
                value:config.number,
                allowBlank: true,
                enforceMaxLength : true,
                xtype:'numberfield'
            }],
            buttons:[{
                xtype: "button",
                iconCls: "msgp-save",
                text: "提交",
                handler: function () {
                    config.handler()
                }
            },{
                xtype: "button",
                iconCls: "msgp-cancel",
                text: "取消",
                handler: function () {
                    Ext.getCmp(config.id).close()
                }
            }]
        }]
    });
}


Ext.TextPrompt = function (config) {
    return new Ext.Window({
        title: config.title,
        id: config.id,
        constrainHeader: true,// 限制窗口只能在容器里拖动
        resizable: false,
        width:400,
        closable: true,
        modal: true,
        items: [{
            xtype:'form',
            id:'TextPromptForm',
            border: 0,
            padding:'5 5 5 5',
            buttonAlign: "center",
            bodyStyle: "background:transparent",
            items:[{
                name: 'text',
                maxLength : 200,
                width:400,
                value:config.text,
                allowBlank: true,
                enforceMaxLength : true,
                xtype:'textfield'
            }],
            buttons:[{
                xtype: "button",
                iconCls: "msgp-save",
                text: "提交",
                handler: function () {
                    config.handler()
                }
            },{
                xtype: "button",
                iconCls: "msgp-cancel",
                text: "取消",
                handler: function () {
                    Ext.maskHide();
                    Ext.getCmp(config.id).close()
                }
            }]
        }]
    });
}

/**
 * 下载文件
 * 统一使用POST提交
 * */
Ext.downLoad = function (config) {
    // Ext.maskShow();

    if (!Ext.fly('downForm')) {
        //下面代码是在创建一个表单以及添加相应的一些属性
        var downForm = document.createElement('form');  //创建一个form表单
        downForm.id = 'downForm'; 　　//该表单的id为downForm
        downForm.name = 'downForm';  //该表单的name属性为downForm
        downForm.className = 'x-hidden'; //该表单为隐藏的
        downForm.action = config.url; //表单的提交地址
        downForm.method = 'post';  //表单的提交方法

        for (var key in config.params) {
            var data = document.createElement('input');  //创建一个input节点
            data.type = 'hidden';　　//隐藏域
            data.name = key;　　　//需要传递给后台的参数名
            data.value = config.params[key];    //参数值
            downForm.appendChild(data); //将input节点追加到form表单里面
        }
        console.log(config.params)
        console.log(downForm)
        document.body.appendChild(downForm); //讲form表单追加到body里面
    }

    Ext.fly('downForm').dom.submit(); //调用form表单的submit方法，提交表单，从而开始下载文件

    //因为表单已经提交了，文件也开始下载了，所以过河拆桥，把表单移除掉
    //如果存在id为downForm的表单，则将它移除掉
    if (Ext.fly('downForm')){
        document.body.removeChild(downForm );
        // Ext.maskHide()
    }
}
/**
 * 校验按钮权限
 * 没权限返回true
 * 有权限返回false
 *
 * */
Ext.hideBtnByPer = function(btnID){
    if(Ext.getDom(btnID))
        return false;
    return true;
}