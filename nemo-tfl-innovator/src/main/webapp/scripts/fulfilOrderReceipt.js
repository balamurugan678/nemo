var receiptWindow = null;

function showReceiptPopUp(){
	var left = (screen.width/2)-(900/2);
	var top = (screen.height/2)-(750/2);
	if(receiptWindow == null || (receiptWindow != null && receiptWindow.closed))
		receiptWindow = window.open('./FulfilOrderReceipt.htm', '_blank', 'width=842,height=595,scrollbars=yes,status=yes,left='+left+',top='+top);
	receiptWindow.focus();
	receiptWindow.print();
}