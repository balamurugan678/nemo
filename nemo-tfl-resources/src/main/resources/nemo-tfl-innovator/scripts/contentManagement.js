/*
 *  This library expects a variable, called content, to be set and contain a hash of the content.
 *  The Spring MVC controller, ContentForJavaScriptController, creates a dynamic JavaScript library that declares and
 *  initialises the content variable.
 */
function getMessage(code, arguments) {
    return applyMessageArguments(findMessage(content, code, getUserLocale(), getFallBackLocale()), arguments);
}

function getUserLocale() {
    if (content === undefined) {
        return "";
    }
    return content.userLocale;
}

function getFallBackLocale() {
    if (content === undefined) {
        return "";
    }
    return content.fallBackLocale;
}

function findMessage(content, code, locale, fallBackLocale) {
    if (content === undefined) {
        return "";
    }
    var message = findMessageInLocale(findAllMessagesForLocale(content, locale), code);
    if ((message === undefined || message === "") && (fallBackLocale != undefined && fallBackLocale != "")) {
        message = findMessageInLocale(findAllMessagesForLocale(content, fallBackLocale), code);
    }
    return (message != undefined) ? message : "";
}

function findAllMessagesForLocale(content, locale) {
    if (content === undefined || locale === undefined) {
        return;
    }
    for (i = 0; i < content.localeMessages.length; i++) {
        if (content.localeMessages[i].locale.toLowerCase() === locale.toLowerCase()) {
            return content.localeMessages[i];
        }
    }
    return;
}

function findMessageInLocale(localeMessages, code) {
    if (localeMessages === undefined || code === undefined) {
        return;
    }
    for (i = 0; i < localeMessages.messages.length; i++) {
        if (localeMessages.messages[i].code.toLowerCase() === code.toLowerCase()) {
            return localeMessages.messages[i].message;
        }
    }
    return;
}

function applyMessageArguments(message, arguments) {
    if (arguments === undefined || arguments.length < 1) {
        return message;
    }
    for (i = 0; i < arguments.length; i++) {
        message = replaceAll(message, '{' + i + '}', arguments[i]);
    }
    return message;
}

function replaceAll(str, target, value) {
    var newStr = str;
    while (newStr.indexOf(target) != -1) {
        newStr = newStr.replace(target, value);
    }
    return newStr;
}
