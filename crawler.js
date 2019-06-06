var crawler = function (ele) {
	this.ele = ele || [document.body];
	this.ele = this.ele.filter(function (item) { return item; });
}

crawler.prototype.$x = function (query) {
	var res = [];
	for (var i = 0, j = this.ele.length; i < j; i++) {
		res = res.concat(this.xpath(this.ele[i], query))
	}
	return new crawler(res);
}
crawler.prototype.$ = function (query, param) {
	var res = [];
	for (var i = 0, j = this.ele.length; i < j; i++) {
		res = res.concat(this.css(this.ele[i], query, param))
	}
	return new crawler(res);
}
crawler.prototype.filter = function (query) {
	var res = [];
	if (query.constructor == RegExp) {
	} else if (query.constructor == String) {
		query = new RegExp(query)
	} else {
		return new crawler(res);
	}

	for (var i = 0, j = this.ele.length; i < j; i++) {
		var data = this.ele[i];
		if (query.test(this.str(data))) {
			res.push(data);
		}
	}
	return new crawler(res);
}
crawler.prototype.regex = function (query, index) {
	var res = [];
	if (query.constructor == RegExp) {
	} else if (query.constructor == String) {
		query = new RegExp(query)
	} else {
		return new crawler(res);
	}

	for (var i = 0, j = this.ele.length; i < j; i++) {
		var data = this.str(this.ele[i]);
		var value = data.match(query);
		if (value.length > 0) {
			if (typeof (index) == "number") {
				if (value[index]) {
					res.push(value[index]);
				}
			} else if (index && index.constructor == Array && typeof (index[0]) == "number") {
				for (idx in index) {
					var dt = value[index[idx]];
					if (dt) {
						res.push(dt);
					}
				}
			} else {
				res = res.concat(value);
			}
		}
	}
	return new crawler(res);
}
crawler.prototype.replace = function (substr, replacement) {
	var res = [];
	if (substr.constructor != RegExp && substr.constructor != String) {
		return new crawler(res);
	}

	for (var i = 0, j = this.ele.length; i < j; i++) {
		var data = this.str(this.ele[i]);
		res.push(data.replace(substr, replacement));
	}
	return new crawler(res);
}
crawler.prototype.split = function (query) {
	var res = [];
	if (query.constructor != RegExp && query.constructor != String) {
		return new crawler(res);
	}

	for (var i = 0, j = this.ele.length; i < j; i++) {
		var data = this.str(this.ele[i]);
		res = res.concat(data.split(query));
	}
	return new crawler(res);
}
crawler.prototype.get = function () {
	var res = [];
	for (var i = 0, j = this.ele.length; i < j; i++) {
		res.push(this.str(this.ele[i]));
	}
	if (res.length == 1) {
		return res[0];
	}
	return res;
}


crawler.prototype.str = function (data) {
	if (data.nodeType && data.nodeType === Node.ELEMENT_NODE) {
		return data.outerHTML;
	} else {
		return data;
	}
}

crawler.prototype.xpath = function (ele, query) {
	var xpathResult = null;
	var str = null;
	var toHighlight = [];

	try {
		xpathResult = document.evaluate(query, ele, null, XPathResult.ANY_TYPE, null);
	} catch (e) {
		str = null;
	}

	if (!xpathResult) {
		return [];
	}

	if (xpathResult.resultType === XPathResult.BOOLEAN_TYPE) {
		str = xpathResult.booleanValue;
	} else if (xpathResult.resultType === XPathResult.NUMBER_TYPE) {
		str = xpathResult.numberValue;
	} else if (xpathResult.resultType === XPathResult.STRING_TYPE) {
		str = xpathResult.stringValue;
	} else if (xpathResult.resultType === XPathResult.UNORDERED_NODE_ITERATOR_TYPE) {
		for (var node = xpathResult.iterateNext(); node; node = xpathResult.iterateNext()) {
			if (node.nodeType === Node.ELEMENT_NODE) {
				toHighlight.push(node);
			} else {
				toHighlight.push(node.value || node.nodeValue);
			}
		}
		return toHighlight;
	}
	return [str];
};
crawler.prototype.css = function (ele, query, param) {
	var res = [];
	if (param) {
		ele.querySelectorAll(query).forEach(function (item) {
			var data = eval("item." + param + " || item.getAttribute('" + param + "')");
			if (data) {
				res.push(data);
			}
		});
	} else {
		ele.querySelectorAll(query).forEach(function (item) {
			res.push(item);
		});
	}
	return res;
}