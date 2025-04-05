# ti.documentscanner - Titanium document scanner module for Android using ML Kit

## Scans documents without any camera permission!

Based on the <a href="https://developers.google.com/ml-kit/vision/doc-scanner/">MLKit Google document scanner scanner (Beta)</a>. You don't need any camera permission to use this module and scan documents to images and a PDF file.

## Example

```js
const documentScanner = require('ti.documentscanner');
documentScanner.scan();
documentScanner.addEventListener("done", function(e) {}
```

## Events
* <b>done</b> -> event.imagePaths (string array), event.pdfPath, event.pdfPageCount

## Methods
* <b>scan()</b>

## Author

- Michael Gangolf ([@MichaelGangolf](https://twitter.com/MichaelGangolf) / [Web](http://migaweb.de))

<span class="badge-buymeacoffee"><a href="https://www.buymeacoffee.com/miga" title="donate"><img src="https://img.shields.io/badge/buy%20me%20a%20coke-donate-orange.svg" alt="Buy Me A Coke donate button" /></a></span>
