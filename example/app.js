const win = Ti.UI.createWindow();
const view = Ti.UI.createView({
	top: 0,
	layout: "horizontal"
});
const documentScanner = require('ti.documentscanner');
const lbl = Ti.UI.createLabel({
	text: "Click to scan",
	touchEnabled: false
});
win.addEventListener("click", function() {
	documentScanner.scan();
})

documentScanner.addEventListener("done", function(e) {
	console.log(e.imagePaths);
	console.log(e.pdfPath);
	console.log(e.pdfPageCount);

	for (var i = 0; i < e.imagePaths.length; ++i) {
		var img = Ti.UI.createImageView({
			width: 100,
			height: Ti.UI.SIZE,
			image: e.imagePaths[i]
		});
		view.add(img);
	}

	var intent = Ti.Android.createIntent({
		action: Ti.Android.ACTION_SEND,
		type: 'application/pdf'
	});
	intent.putExtraUri(Ti.Android.EXTRA_STREAM, e.pdfPath);
	Ti.Android.currentActivity.startActivity(Ti.Android.createIntentChooser(intent, null));
});

win.add([lbl, view]);
win.open();
