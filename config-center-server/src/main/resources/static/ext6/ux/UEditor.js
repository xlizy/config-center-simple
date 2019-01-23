/**
 * Created by xlizy on 2018/3/15.
 *
 */
Ext.define('Ext.ux.UEditor', {
    extend: 'Ext.form.field.Base',
    alias:'widget.ueditor',
    ueditor:null,
    ueditorConfig: {},
    anchor: '100%',
    config:{
    },
    initComponent:function(){
        var me = this;
        Ext.apply(this,{
            //fieldSubTpl:'<div id="'+me.id+'ueditor" name="'+me.name+'" ></div>',
            fieldSubTpl: '<script id="'+me.id+'ueditor" type="text/plain" name="' + me.name + '"></script>',
            items:[
                {
                    xtype: 'hiddenfield',
                    name: me.name
                }
            ],
            listeners:{
                scope:me,
                render:function(){
                    var config = {initialFrameWidth: (me.width || '100%'),initialFrameHeight:me.height};
                    Object.assign(me.ueditorConfig,config);
                    me.ueditor = UE.getEditor(me.id+'ueditor',config);
                    me.ueditor.ready(function(){
                        me.ueditor.addListener('contentChange',function(){
                            me.fireEvent('change', me);
                            me.setValue(me.ueditor.getContent(),true);
                        });
                    });
                }
            }
        });
        this.callParent();
    },
    onDestroy:function () {
        if(this.ueditor){
            this.ueditor.destroy();
        }
    },
    getValue:function(){
        var me = this,
            value;
        if(me.ueditor){
            me.ueditor.ready(function(){
                me.value = me.ueditor.getContent();
            });
        }
        value = me.value;
        return value;
    },
    setValue:function(value,isChange){
        var me = this;
        if(value === null || value === undefined){
            value = "";
        }
        me.callParent(arguments);
        if(!isChange){
            if(me.ueditor){
                me.ueditor.ready(function(){
                    me.ueditor.setContent(value);
                });
            }
        }
        return me;
    }
});