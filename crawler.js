var crawler = function (ele) {
    this.ele = ele || [document];

    if (this.ele.constructor == Array) {
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
    return this.exeQuery("xpath", query);
}
crawler.prototype.$ = function (query, param) {
    return this.exeQuery("css", query, param);
}
crawler.prototype.filter = function (query) {
    var res = [];
    if (query.constructor == RegExp) {} else if (query.constructor == String) {
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
    if (query.constructor == RegExp) {} else if (query.constructor == String) {
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
crawler.prototype.mix = function (expression) {
    return eval('this.' + expression);
}
crawler.prototype.set = function (attr, value) {
    for (var i = 0, j = this.ele.length; i < j; i++) {
        var data = this.ele[i];
        if (data.nodeType) {
            try {
                eval("data." + attr + '="' + value + '" || data.' + attr + '=' + value + ';');
            } catch (err) {
                eval('data.setAttribute("' + attr + '","' + value + '");');
            }
        }
    }
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
crawler.prototype.toString = function () {
    return this.get();
}


crawler.prototype.str = function (data) {
    if (data.nodeType && data.nodeType === Node.ELEMENT_NODE) {
        return data.outerHTML;
    } else if (data.nodeType && data.nodeType === Node.DOCUMENT_NODE) {
        return data.body.parentNode.outerHTML;
    } else {
        return data;
    }
}
crawler.prototype.exeQuery = function (flag, query, param) {
    if (/iframe/img.test(query)) {
        var temp = query.match(/.*?iframe(\[.*?\]|[#.:,].*?[\s>+~])?/img);
        for (tmp in temp) {
            var item = temp[tmp];
            item = item.replace(/[\s>+~]*$/img, "").replace(/^[\s>+~]*/img, "");
            this.ele = this.manyQuery(flag, item, param).ele;
        }
        query = query.replace(/.*?iframe(\[.*?\]|[#.:,].*?[\s>+~])?/img, "").replace(/[\s>+~]*$/img, "").replace(
            /^[\s>+~]*/img, "");
        if (!query) {
            return this;
        }
        return this.manyQuery(flag, query, param);
    } else {
        return this.manyQuery(flag, query, param);
    }
}
crawler.prototype.manyQuery = function (flag, query, param) {
    var res = [];
    for (var i = 0, j = this.ele.length; i < j; i++) {
        if (flag == "css") {
            res = res.concat(this.css(this.ele[i], query, param));
        } else if (flag == "xpath") {
            res = res.concat(this.xpath(this.ele[i], query))
        }
    }
    return new crawler(res);
}
crawler.prototype.getXpath = function (flag) {
    var res = [];
    for (var i = 0, j = this.ele.length; i < j; i++) {
        var data = this.ele[i];
        if (data.nodeType && data.nodeType === Node.ELEMENT_NODE) {
            if (flag) {
                res.push(this.getFullSelector(data, true));
            } else {
                res.push(this.getSelector(data, true));
            }
        }
    }
    if (res.length == 1) {
        return res[0];
    }
    return res;
}
crawler.prototype.getCss = function (flag) {
    var res = [];
    for (var i = 0, j = this.ele.length; i < j; i++) {
        var data = this.ele[i];
        if (data.nodeType && data.nodeType === Node.ELEMENT_NODE) {
            if (flag) {
                res.push(this.getFullSelector(data));
            } else {
                res.push(this.getSelector(data));
            }
        }
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
            return expr.replace(/[\['](\??\(.*?\))[\]']/g, function ($0, $1) {
                    return "[#" + (subx.push($1) - 1) + "]";
                })
                .replace(/'?\.'?|\['?/g, ";")
                .replace(/;;;|;;/g, ";..;")
                .replace(/;$|'?\]|'$/g, "")
                .replace(/#([0-9]+)/g, function ($0, $1) {
                    return subx[$1];
                });
        },
        asPath: function (path) {
            var x = path.split(";"),
                p = "$";
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
                var x = expr.split(";"),
                    loc = x.shift();
                x = x.join(";");
                if (val && val.hasOwnProperty(loc))
                    P.trace(x, val[loc], path + ";" + loc);
                else if (loc === "*")
                    P.walk(loc, x, val, path, function (m, l, x, v, p) {
                        P.trace(m + ";" + x, v, p);
                    });
                else if (loc === "..") {
                    P.trace(x, val, path);
                    P.walk(loc, x, val, path, function (m, l, x, v, p) {
                        typeof v[m] === "object" && P.trace("..;" + x, v[m], p + ";" + m);
                    });
                } else if (/,/.test(loc)) { // [name1,name2,...]
                    for (var s = loc.split(/'?,'?/), i = 0, n = s.length; i < n; i++)
                        P.trace(s[i] + ";" + x, val, path);
                } else if (/^\(.*?\)$/.test(loc)) // [(expr)]
                    P.trace(P.eval(loc, val, path.substr(path.lastIndexOf(";") + 1)) + ";" + x, val, path);
                else if (/^\?\(.*?\)$/.test(loc)) // [?(expr)]
                    P.walk(loc, x, val, path, function (m, l, x, v, p) {
                        if (P.eval(l.replace(/^\?\((.*?)\)$/, "$1"), v[m], m)) P.trace(m + ";" + x, v,
                            p);
                    });
                else if (/^(-?[0-9]*):(-?[0-9]*):?([0-9]*)$/.test(loc)) // [start:end:step]  phyton slice syntax
                    P.slice(loc, x, val, path);
            } else
                P.store(path, val);
        },
        walk: function (loc, expr, val, path, f) {
            if (val instanceof Array) {
                for (var i = 0, n = val.length; i < n; i++)
                    if (i in val)
                        f(i, loc, expr, val, path);
            } else if (typeof val === "object") {
                for (var m in val)
                    if (val.hasOwnProperty(m))
                        f(m, loc, expr, val, path);
            }
        },
        slice: function (loc, expr, val, path) {
            if (val instanceof Array) {
                var len = val.length,
                    start = 0,
                    end = len,
                    step = 1;
                loc.replace(/^(-?[0-9]*):(-?[0-9]*):?(-?[0-9]*)$/g, function ($0, $1, $2, $3) {
                    start = parseInt($1 || start);
                    end = parseInt($2 || end);
                    step = parseInt($3 || step);
                });
                start = (start < 0) ? Math.max(0, start + len) : Math.min(len, start);
                end = (end < 0) ? Math.max(0, end + len) : Math.min(len, end);
                for (var i = start; i < end; i += step)
                    P.trace(i + ";" + expr, val, path);
            }
        },
        eval: function (x, _v, _vname) {
            try {
                return $ && _v && eval(x.replace(/@/g, "_v"));
            } catch (e) {
                throw new SyntaxError("jsonPath: " + e.message + ": " + x.replace(/@/g, "_v").replace(/\^/g,
                    "_a"));
            }
        }
    };

    var $ = obj;
    if (expr && obj && (P.resultType == "VALUE" || P.resultType == "PATH")) {
        P.trace(P.normalize(expr).replace(/^\$;/, ""), obj, "$");
        return P.result;
    }
}
crawler.prototype.xpath = function (ele, query) {
    if (!query) {
        return [];
    }
    var xpathResult = null;
    var str = null;
    var toHighlight = [];
    ele = ele.contentDocument || ele;
    var dom = ele.contentDocument || ele.ownerDocument || document;
    try {
        xpathResult = dom.evaluate("." + query, ele, null, XPathResult.ANY_TYPE, null);
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
    if (!query) {
        return [];
    }
    var res = [];
    ele = ele.contentDocument || ele;
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


crawler.prototype.getSelector = function (elm, xp) {
    var fex = xp ? "[@" : "[";
    for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode) {
        var attrs = elm.attributes;
        var flag = true;
        for (var i = 0, j = attrs.length; i < j; i++) {
            var exp = attrs[i].localName.toLowerCase() + "=\"" + attrs[i].value + "\"]";
            var uniqueIdCount = elm.ownerDocument.querySelectorAll(elm.localName.toLowerCase() + "[" + exp).length;
            if (uniqueIdCount == 1) {
                segs.unshift(elm.localName.toLowerCase() + fex + exp);
                flag = false;
                break;
            }
        }
        if (flag) {
            for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
                if (sib.localName == elm.localName) i++;
            };
            if (xp) {
                segs.unshift(elm.localName.toLowerCase() + (i > 1 ? '[' + i + ']' : ''));
            } else {
                segs.unshift(elm.localName.toLowerCase() + (i > 1 ? ':nth-of-type(' + i + ')' : ''));
            }
        } else {
            break;
        };
        if (elm.localName.toLowerCase() == "body") {
            break;
        }
    };
    return segs.length ? (xp ? '//' + segs.join('/') : segs.join('>')) : null;
}
crawler.prototype.getFullSelector = function (elm, xp) {
    for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode) {
        for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
            if (sib.localName == elm.localName) i++;
        };
        if (xp) {
            segs.unshift(elm.localName.toLowerCase() + (i > 1 ? '[' + i + ']' : ''));
        } else {
            segs.unshift(elm.localName.toLowerCase() + (i > 1 ? ':nth-of-type(' + i + ')' : ''));
        }
        if (elm.localName.toLowerCase() == "body") {
            break;
        }
    };
    return segs.length ? (xp ? '//' + segs.join('/') : segs.join('>')) : null;
}
var crawler = function (ele) {
    this.ele = ele || [document];

    if (this.ele.constructor == Array) {
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
    return this.exeQuery("xpath", query);
}
crawler.prototype.$ = function (query, param) {
    return this.exeQuery("css", query, param);
}
crawler.prototype.filter = function (query) {
    var res = [];
    if (query.constructor == RegExp) {} else if (query.constructor == String) {
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
    if (query.constructor == RegExp) {} else if (query.constructor == String) {
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
crawler.prototype.mix = function (expression) {
    return eval('this.' + expression);
}
crawler.prototype.set = function (attr, value) {
    for (var i = 0, j = this.ele.length; i < j; i++) {
        var data = this.ele[i];
        if (data.nodeType) {
            try {
                eval("data." + attr + '="' + value + '" || data.' + attr + '=' + value + ';');
            } catch (err) {
                eval('data.setAttribute("' + attr + '","' + value + '");');
            }
        }
    }
}
crawler.prototype.func = function (funcName) {
    for (var i = 0, j = this.ele.length; i < j; i++) {
        var data = this.ele[i];
        if (data.nodeType) {
            try {
                eval("data." + funcName + '();');
            } catch (err) {
                console.error(err);
            }
        }
    }
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
crawler.prototype.toString = function () {
    return this.get();
}


crawler.prototype.str = function (data) {
    if (data.nodeType && data.nodeType === Node.ELEMENT_NODE) {
        return data.outerHTML;
    } else if (data.nodeType && data.nodeType === Node.DOCUMENT_NODE) {
        return data.body.parentNode.outerHTML;
    } else {
        return data;
    }
}
crawler.prototype.exeQuery = function (flag, query, param) {
    if (/iframe/img.test(query)) {
        var temp = query.match(/.*?iframe(\[.*?\]|[#.:,].*?[\s>+~])?/img);
        for (tmp in temp) {
            var item = temp[tmp];
            item = item.replace(/[\s>+~]*$/img, "").replace(/^[\s>+~]*/img, "");
            this.ele = this.manyQuery(flag, item, param).ele;
        }
        query = query.replace(/.*?iframe(\[.*?\]|[#.:,].*?[\s>+~])?/img, "").replace(/[\s>+~]*$/img, "").replace(
            /^[\s>+~]*/img, "");
        if (!query) {
            return this;
        }
        return this.manyQuery(flag, query, param);
    } else {
        return this.manyQuery(flag, query, param);
    }
}
crawler.prototype.manyQuery = function (flag, query, param) {
    var res = [];
    for (var i = 0, j = this.ele.length; i < j; i++) {
        if (flag == "css") {
            res = res.concat(this.css(this.ele[i], query, param));
        } else if (flag == "xpath") {
            res = res.concat(this.xpath(this.ele[i], query))
        }
    }
    return new crawler(res);
}
crawler.prototype.getXpath = function (flag) {
    var res = [];
    for (var i = 0, j = this.ele.length; i < j; i++) {
        var data = this.ele[i];
        if (data.nodeType && data.nodeType === Node.ELEMENT_NODE) {
            if (flag) {
                res.push(this.getFullSelector(data, true));
            } else {
                res.push(this.getSelector(data, true));
            }
        }
    }
    if (res.length == 1) {
        return res[0];
    }
    return res;
}
crawler.prototype.getCss = function (flag) {
    var res = [];
    for (var i = 0, j = this.ele.length; i < j; i++) {
        var data = this.ele[i];
        if (data.nodeType && data.nodeType === Node.ELEMENT_NODE) {
            if (flag) {
                res.push(this.getFullSelector(data));
            } else {
                res.push(this.getSelector(data));
            }
        }
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
            return expr.replace(/[\['](\??\(.*?\))[\]']/g, function ($0, $1) {
                    return "[#" + (subx.push($1) - 1) + "]";
                })
                .replace(/'?\.'?|\['?/g, ";")
                .replace(/;;;|;;/g, ";..;")
                .replace(/;$|'?\]|'$/g, "")
                .replace(/#([0-9]+)/g, function ($0, $1) {
                    return subx[$1];
                });
        },
        asPath: function (path) {
            var x = path.split(";"),
                p = "$";
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
                var x = expr.split(";"),
                    loc = x.shift();
                x = x.join(";");
                if (val && val.hasOwnProperty(loc))
                    P.trace(x, val[loc], path + ";" + loc);
                else if (loc === "*")
                    P.walk(loc, x, val, path, function (m, l, x, v, p) {
                        P.trace(m + ";" + x, v, p);
                    });
                else if (loc === "..") {
                    P.trace(x, val, path);
                    P.walk(loc, x, val, path, function (m, l, x, v, p) {
                        typeof v[m] === "object" && P.trace("..;" + x, v[m], p + ";" + m);
                    });
                } else if (/,/.test(loc)) { // [name1,name2,...]
                    for (var s = loc.split(/'?,'?/), i = 0, n = s.length; i < n; i++)
                        P.trace(s[i] + ";" + x, val, path);
                } else if (/^\(.*?\)$/.test(loc)) // [(expr)]
                    P.trace(P.eval(loc, val, path.substr(path.lastIndexOf(";") + 1)) + ";" + x, val, path);
                else if (/^\?\(.*?\)$/.test(loc)) // [?(expr)]
                    P.walk(loc, x, val, path, function (m, l, x, v, p) {
                        if (P.eval(l.replace(/^\?\((.*?)\)$/, "$1"), v[m], m)) P.trace(m + ";" + x, v,
                            p);
                    });
                else if (/^(-?[0-9]*):(-?[0-9]*):?([0-9]*)$/.test(loc)) // [start:end:step]  phyton slice syntax
                    P.slice(loc, x, val, path);
            } else
                P.store(path, val);
        },
        walk: function (loc, expr, val, path, f) {
            if (val instanceof Array) {
                for (var i = 0, n = val.length; i < n; i++)
                    if (i in val)
                        f(i, loc, expr, val, path);
            } else if (typeof val === "object") {
                for (var m in val)
                    if (val.hasOwnProperty(m))
                        f(m, loc, expr, val, path);
            }
        },
        slice: function (loc, expr, val, path) {
            if (val instanceof Array) {
                var len = val.length,
                    start = 0,
                    end = len,
                    step = 1;
                loc.replace(/^(-?[0-9]*):(-?[0-9]*):?(-?[0-9]*)$/g, function ($0, $1, $2, $3) {
                    start = parseInt($1 || start);
                    end = parseInt($2 || end);
                    step = parseInt($3 || step);
                });
                start = (start < 0) ? Math.max(0, start + len) : Math.min(len, start);
                end = (end < 0) ? Math.max(0, end + len) : Math.min(len, end);
                for (var i = start; i < end; i += step)
                    P.trace(i + ";" + expr, val, path);
            }
        },
        eval: function (x, _v, _vname) {
            try {
                return $ && _v && eval(x.replace(/@/g, "_v"));
            } catch (e) {
                throw new SyntaxError("jsonPath: " + e.message + ": " + x.replace(/@/g, "_v").replace(/\^/g,
                    "_a"));
            }
        }
    };

    var $ = obj;
    if (expr && obj && (P.resultType == "VALUE" || P.resultType == "PATH")) {
        P.trace(P.normalize(expr).replace(/^\$;/, ""), obj, "$");
        return P.result;
    }
}
crawler.prototype.xpath = function (ele, query) {
    if (!query) {
        return [];
    }
    var xpathResult = null;
    var str = null;
    var toHighlight = [];
    ele = ele.contentDocument || ele;
    var dom = ele.contentDocument || ele.ownerDocument || document;
    try {
        xpathResult = dom.evaluate("." + query, ele, null, XPathResult.ANY_TYPE, null);
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
    if (!query) {
        return [];
    }
    var res = [];
    ele = ele.contentDocument || ele;
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


crawler.prototype.getSelector = function (elm, xp) {
    var fex = xp ? "[@" : "[";
    for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode) {
        var attrs = elm.attributes;
        var flag = true;
        for (var i = 0, j = attrs.length; i < j; i++) {
            var exp = attrs[i].localName.toLowerCase() + "=\"" + attrs[i].value + "\"]";
            var uniqueIdCount = elm.ownerDocument.querySelectorAll(elm.localName.toLowerCase() + "[" + exp).length;
            if (uniqueIdCount == 1) {
                segs.unshift(elm.localName.toLowerCase() + fex + exp);
                flag = false;
                break;
            }
        }
        if (flag) {
            for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
                if (sib.localName == elm.localName) i++;
            };
            if (xp) {
                segs.unshift(elm.localName.toLowerCase() + (i > 1 ? '[' + i + ']' : ''));
            } else {
                segs.unshift(elm.localName.toLowerCase() + (i > 1 ? ':nth-of-type(' + i + ')' : ''));
            }
        } else {
            break;
        };
        if (elm.localName.toLowerCase() == "body") {
            break;
        }
    };
    return segs.length ? (xp ? '//' + segs.join('/') : segs.join('>')) : null;
}
crawler.prototype.getFullSelector = function (elm, xp) {
    for (var segs = []; elm && elm.nodeType == 1; elm = elm.parentNode) {
        for (i = 1, sib = elm.previousSibling; sib; sib = sib.previousSibling) {
            if (sib.localName == elm.localName) i++;
        };
        if (xp) {
            segs.unshift(elm.localName.toLowerCase() + (i > 1 ? '[' + i + ']' : ''));
        } else {
            segs.unshift(elm.localName.toLowerCase() + (i > 1 ? ':nth-of-type(' + i + ')' : ''));
        }
        if (elm.localName.toLowerCase() == "body") {
            break;
        }
    };
    return segs.length ? (xp ? '//' + segs.join('/') : segs.join('>')) : null;
}
