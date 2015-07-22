angular.module('MainApp',[])
	.controller('MainCtrl',[function(){
		var self=this;
		self.menu='menu.html';
		self.test="中文字"; 
	}])
	.controller('MenuCtrl',[function(){
		
		setTabHeight();
		
		$(window).resize(function(){ 
			
			setTabHeight();
		});
		
		function setTabHeight(){
			var infoSize=+$(".info").height();
			var mainSize=+$(".main").height();
			$(".detail").css('height', mainSize-infoSize);
		};
		
		
		var self=this;
		self.selectedTab=0;
		self.goCats=true;
		self.custInfo={
			Serviceid:'12345',
			TWNcode:'886*********',
			S2Tcode:'852*********',
			Name:'王小明',
			IDCardNumber:'B100*****6',
			Birthday:'70/01/01',
			ContactPhone:'04-12341234',
			EntCharge:'王大明',
			DAffaires:'北部代辦處',
			RegisterAddr:'台北市**區**路1**-8*2樓',
			BillAddr:'同戶籍地址',
			Email:'a12335489215@gmai.com',
			Remark:'備註'
		};
		self.tabs=[
	           {title:'費用紀錄',content:'費用紀錄',active:false,disabled:false},
	           {title:'租退記錄',content:'租退記錄',active:true,disabled:false},
	           {title:'使用記錄',content:'使用記錄',active:false,disabled:false},
	           {title:'申訴紀錄',content:'申訴紀錄',active:false,disabled:true}
	           ];
		self.selectTab=function(index){
			self.selectedTab=index;
		};
	}]);