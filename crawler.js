var crawler = function (ele) {
	this.ele = ele || [document.body];
	if(this.ele.constructor != Array && this.ele.constructor != NodeList){
		this.ele = [ele];
	}
	if(this.ele.constructor == Array){
		this.ele = this.ele.filter(function (item) {
			return item;
		});
	}
}
crawler.prototype.$j = function (query, param) {
	var res = [];
	return new crawler(this.jsonPath(this.ele, query, param));
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
crawler.prototype.jsonPath = function (obj, expr, arg) {
	var P = {
		resultType: arg || "VALUE",
		result: [],
		normalize: function (expr) {
			var subx = [];
			return expr.replace(/[\['](\??\(.*?\))[\]']/g, function ($0, $1) { return "[#" + (subx.push($1) - 1) + "]"; })
				.replace(/'?\.'?|\['?/g, ";")
				.replace(/;;;|;;/g, ";..;")
				.replace(/;$|'?\]|'$/g, "")
				.replace(/#([0-9]+)/g, function ($0, $1) { return subx[$1]; });
		},
		asPath: function (path) {
			var x = path.split(";"), p = "$";
			for (var i = 1, n = x.length; i < n; i++)
				p += /^[0-9*]+$/.test(x[i]) ? ("[" + x[i] + "]") : ("['" + x[i] + "']");
			return p;
		},
		store: function (p, v) {
			if (p) P.result[P.result.length] = P.resultType == "PATH" ? P.asPath(p) : v;
			return !!p;
		},
		trace: function (expr, val, path) {
			if (expr) {
				var x = expr.split(";"), loc = x.shift();
				x = x.join(";");
				if (val && val.hasOwnProperty(loc))
					P.trace(x, val[loc], path + ";" + loc);
				else if (loc === "*")
					P.walk(loc, x, val, path, function (m, l, x, v, p) { P.trace(m + ";" + x, v, p); });
				else if (loc === "..") {
					P.trace(x, val, path);
					P.walk(loc, x, val, path, function (m, l, x, v, p) { typeof v[m] === "object" && P.trace("..;" + x, v[m], p + ";" + m); });
				}
				else if (/,/.test(loc)) { // [name1,name2,...]
					for (var s = loc.split(/'?,'?/), i = 0, n = s.length; i < n; i++)
						P.trace(s[i] + ";" + x, val, path);
				}
				else if (/^\(.*?\)$/.test(loc)) // [(expr)]
					P.trace(P.eval(loc, val, path.substr(path.lastIndexOf(";") + 1)) + ";" + x, val, path);
				else if (/^\?\(.*?\)$/.test(loc)) // [?(expr)]
					P.walk(loc, x, val, path, function (m, l, x, v, p) { if (P.eval(l.replace(/^\?\((.*?)\)$/, "$1"), v[m], m)) P.trace(m + ";" + x, v, p); });
				else if (/^(-?[0-9]*):(-?[0-9]*):?([0-9]*)$/.test(loc)) // [start:end:step]  phyton slice syntax
					P.slice(loc, x, val, path);
			}
			else
				P.store(path, val);
		},
		walk: function (loc, expr, val, path, f) {
			if (val instanceof Array) {
				for (var i = 0, n = val.length; i < n; i++)
					if (i in val)
						f(i, loc, expr, val, path);
			}
			else if (typeof val === "object") {
				for (var m in val)
					if (val.hasOwnProperty(m))
						f(m, loc, expr, val, path);
			}
		},
		slice: function (loc, expr, val, path) {
			if (val instanceof Array) {
				var len = val.length, start = 0, end = len, step = 1;
				loc.replace(/^(-?[0-9]*):(-?[0-9]*):?(-?[0-9]*)$/g, function ($0, $1, $2, $3) { start = parseInt($1 || start); end = parseInt($2 || end); step = parseInt($3 || step); });
				start = (start < 0) ? Math.max(0, start + len) : Math.min(len, start);
				end = (end < 0) ? Math.max(0, end + len) : Math.min(len, end);
				for (var i = start; i < end; i += step)
					P.trace(i + ";" + expr, val, path);
			}
		},
		eval: function (x, _v, _vname) {
			try { return $ && _v && eval(x.replace(/@/g, "_v")); }
			catch (e) { throw new SyntaxError("jsonPath: " + e.message + ": " + x.replace(/@/g, "_v").replace(/\^/g, "_a")); }
		}
	};

	var $ = obj;
	if (expr && obj && (P.resultType == "VALUE" || P.resultType == "PATH")) {
		P.trace(P.normalize(expr).replace(/^\$;/, ""), obj, "$");
		return P.result;
	}
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
