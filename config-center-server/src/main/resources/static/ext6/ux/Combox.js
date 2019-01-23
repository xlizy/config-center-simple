/**
 * Created by xlizy on 2018/3/15.
 *
 */
Ext.define('Ext.ux.Combox', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.combox',
    constructor: function(config) {
        this.callParent(arguments);
    },
    triggers: {
        clear: {
            cls: 'x-form-clear-trigger',
            handler: function (){
                this.clearValue();
            },
            scope: 'controller'
        }
    },
})