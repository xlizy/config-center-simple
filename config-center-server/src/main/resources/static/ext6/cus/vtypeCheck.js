Ext.apply(Ext.form.VTypes,
{
    /**
	 * 日期验证器 用于验证起始日期必须早于结束日期
	 * 
	 * <pre>
	 * <code>
	 * //例子:
items:[
	{xtype:"datefield",id:"startDate",fieldLabel:"开始时间",name:"start",vtype:"daterange",endDateField:"endDate"},
	{xtype:"datefield",id:"endDate",fieldLabel:"结束时间",name:"end",vtype:"daterange",startDateField:"startDate"},
]
    	</code>
    	</pre>
	 * @param {Date} val 日期
	 * @param {Date} field Ext.form.DateField
	 */
    daterange : function(val, field) {
        var date = field.parseDate(val);
        if (!date) {
            return;
        }
        if (field.startDateField
                && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax
                        .getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } else if (field.endDateField
                && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin
                        .getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        return true;
    },
    daterangeText:'起始日期必须早于结束日期！',
  //检查号码是否符合规范，包括长度，类型
    idCard:function(card)
   {
        //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
        var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
        if(reg.test(card) == false)
        {
            return false;
        }

        return true;
   },
  idCardText: '您输入的身份证号码不正确,请重新输入！',
  password: function(val, field)
  {
        if (field.initialPassField)
        {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
  },
  passwordText: '两次输入的密码不一致！',

  chinese:function(val,field)
  {
        var reg = /^[\u4e00-\u9fa5]+$/i;
        if(!reg.test(val))
        {
            return false;
        }
        return true;
  },
  chineseText:'请输入中文',

  age:function(val,field)
  {
        try
        {
            if(parseInt(val) >= 18 && parseInt(val) <= 100)
                return true;
            return false;
        }
        catch(err)
        {
            return false;
        }
  },
  ageText:'年龄输入有误',

  alphanum:function(val,field)
  {
        try
        {
            if(!/\W/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  alphanumText:'请输入英文字母或是数字,其它字符是不允许的.',

  url:function(val,field)
  {
        try
        {
            if(/^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  urlText:'请输入有效的URL地址.',

  max:function(val,field)
  {
        try
        {
            if(parseFloat(val) <= parseFloat(field.max))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  maxText:'超过最大值',

  min:function(val,field)
  {
        try
        {
            if(parseFloat(val) >= parseFloat(field.min))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  minText:'小于最小值',

  datecn:function(val,field)
  {
        try
        {
            var regex = /^(\d{4})-(\d{2})-(\d{2})$/;
            if(!regex.test(val)) return false;
            var d = new Date(val.replace(regex, '$1/$2/$3'));
            return (parseInt(RegExp.$2, 10) == (1+d.getMonth())) && (parseInt(RegExp.$3, 10) == d.getDate())&&(parseInt(RegExp.$1, 10) == d.getFullYear());
        }
        catch(e)
        {
            return false;
        }
  },
  datecnText:'请使用这样的日期格式: yyyy-mm-dd. 例如:2008-06-20.',

  integer:function(val,field)
  {
        try
        {
            if(/^[-+]?[\d]+$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  integerText:'请输入正确的整数',

  minlength:function(val,field)
  {
        try
        {
            if(val.length >= parseInt(field.minlen))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  minlengthText:'长度过小',

  maxlength:function(val,field)
  {
     try
     {
        if(val.length <= parseInt(field.maxlen))
            return true;
        return false;
     }
     catch(e)
     {
        return false;
     }
  },
  maxlengthText:'长度过大',

  ip:function(val,field)
  {
        try
        {
            if((/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(val)))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  ipText:'请输入正确的IP地址',

  phone:function(val,field)
  {
        try
        {
            if(/^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  phoneText:'请输入正确的电话号码,如:0920-29392929',

  mobilephone:function(val,field)
  {
        try
        {
            if(/^1[3|5|7|8|][0-9]{9}$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  mobilephoneText:'请输入正确的手机号码',
    //EXT
    onlyEXT:function(val){
            var pattern = /[^EXT]/g;
            return !pattern.test(val);
    },
    onlyEXTText:'exttttttliu',
    onlyEXTMask:/[EXT]/,
     //Date
     mydate:function(val) {
            var dt = Date.parseDate(val,'Y:m:d',true);
            return dt ? val : false;
     },
    mydateText:'rturtutrrtrrerrr',
    mydateMask:/[0-9:]/,

  alpha:function(val,field)
  {
        try
        {
            if( /^[a-zA-Z]+$/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
  },
  alphaText:'请输入英文字母',
    money:function (val,field) {
        try
        {
            if(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(val))
                return true;
            return false;
        }
        catch(e)
        {
            return false;
        }
    },
    moneyText:'请输入正确的金额',
});
Ext.form.VTypes["datetimeFieldMax"]=function(val,field){
    Ext.form.VTypes["datetimeFieldMaxText"] = "该输入项的日期必须在 "+Ext.Date.format(new Date(field.dMaxValue),"Y-m-d H:i:s")+" 之前"
    var date = field.parseDate(val);
    try {
        if(field.dMaxValue >= date)
            return true;
        return false;
    } catch(e) {
        return false;
    }
}