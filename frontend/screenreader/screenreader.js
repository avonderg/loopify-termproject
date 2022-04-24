"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
/**
 * Class representing an element and its corresponding handler function.
 */
class ElementHandler {
    constructor(element, handler) {
        this.element = element;
        this.handler = handler;
    }
}
/**
 * map of html elements to their table respresentation
 */
let TABLES = new Map();
/**
 * given the current index, this map will tell you if you are inside a table, and if you are it returns the table
 */
let tableEltToTable = new Map();
// Look up the Web Speech API (https://developer.mozilla.org/en-US/docs/Web/API/SpeechSynthesis)
// Initialize this variable when the window first loads
let VOICE_SYNTH;
// The current speaking rate of the screen reader
let VOICE_RATE = 1;
// Stores elements and their handler functions
// Think of an appropriate data structure to do this
// Assign this variable in mapPage()
let ELEMENT_HANDLERS = [];
// Indicates the current element that the user is on
// You can decide the type of this variable
let current = 0;
// Non table elements
let onlyTableElts = [];
// current SpeechSynthesisUtterance
let currentSpeech;
// HTML Tags that the screen reader handles
let validTags = new Set(["TITLE", "H1", "H2", "H3", "H4", "H5", "H6", "P",
    "IMG", "A", "INPUT", "BUTTON", "TABLE", "CAPTION", "TD", "TFOOT", "TH", "TR"]);
//only changed to true when we arrive at a table
let readingTable = false;
/**
 * Speaks out text.
 * @param text the text to speak
 */
function speak(text) {
    return __awaiter(this, void 0, void 0, function* () {
        currentSpeech = new SpeechSynthesisUtterance(text);
        currentSpeech.rate = VOICE_RATE;
        VOICE_SYNTH.speak(currentSpeech);
        return yield new Promise((resolve, reject) => {
            currentSpeech.onstart = () => __awaiter(this, void 0, void 0, function* () {
                if (tableEltToTable.get(current) == null) {
                    highlightElement(current.toString());
                }
                // Once the speech starts, we have to wait until the end
                let end_promise = yield new Promise((resolve, reject) => {
                    // Only onend can resolve this promise
                    // changing onend somewhere else will result in this promise never resolving
                    currentSpeech.onend = () => { resolve(true); };
                });
                resolve(true);
            });
        });
    });
}
window.onload = () => {
    VOICE_SYNTH && VOICE_SYNTH.cancel();
    generateHandlers();
    document.body.innerHTML = `
      <div id="screenReader">
          <button onclick="start()">Start [Space]</button>
          <button onclick="pause()">Pause/Resume [P]</button>
          <button onclick="changeVoiceRate(1.1)";>Speed Up [Right Arrow]</button>
          <button onclick="changeVoiceRate(0.9);">Slow Down [Left Arrow]</button>
          <button onclick="previous();">Previous Element [Up Arrow]</button>
          <button onclick="next();">Next Element [Down Arrow]</button>
      </div>
  ` + document.body.innerHTML;
    VOICE_SYNTH = window.speechSynthesis;
    document.addEventListener("keydown", globalKeystrokes);
};
/**
 * Generates and stores the handlers for all supported HTML elements
 */
function generateHandlers() {
    let htmlElements = document.getElementsByTagName("*");
    let id = 0;
    //trying to decipher i and index
    for (let i = 0; i < htmlElements.length; i++) {
        // we should only add elements that have a valid tag
        if (validTags.has(htmlElements[i].tagName)) {
            let i2 = ELEMENT_HANDLERS.length;
            if (htmlElements[i].tagName == "TABLE") {
                htmlElements[i].id = String(i);
                let newTable = parseTable(htmlElements[i].id, i2);
                TABLES.set(htmlElements[i], newTable);
                console.log("table " + i2);
                let numChildren = htmlElements[i].getElementsByTagName("*").length;
                console.log(numChildren);
                for (let j = i2 + 1; j < i2 + numChildren; j++) {
                    onlyTableElts.push(j);
                    tableEltToTable.set(j, newTable);
                }
            }
            // Assign ID
            htmlElements[i].id = id.toString();
            id++;
            // Create handler
            ELEMENT_HANDLERS.push(getHandler(htmlElements[i]));
        }
    }
    console.log(onlyTableElts);
    console.log(ELEMENT_HANDLERS);
}
/**
 * Changes the speaking rate of the screen reader.
 * @param factor multiplier on the speaking rate
 */
function changeVoiceRate(factor) {
    VOICE_RATE *= factor;
    if (VOICE_RATE > 4) {
        VOICE_RATE = 4;
    }
    else if (VOICE_RATE < 0.25) {
        VOICE_RATE = 0.25;
    }
}
/**
 * Removes all highlighting,
 * Stops speaking, removes the highlight from the element we were looking at and resumes voice synth
 */
function clean() {
    return __awaiter(this, void 0, void 0, function* () {
        for (let i = 0; i < ELEMENT_HANDLERS.length; i++) {
            removeHighlight(i.toString());
        }
        ;
        if (VOICE_SYNTH.speaking) {
            VOICE_SYNTH.cancel();
            if (currentSpeech) {
                // the promise in speak will never resolve
                currentSpeech.onend = () => 1;
            }
            return true;
        }
        return false;
    });
}
/**
 * Reads element at current and automatically moves to the next HTML element in the DOM
 * until we get to the last HTML element
 */
function automatic_next() {
    removeHighlight((current - 1).toString());
    let eltHandler = ELEMENT_HANDLERS[current];
    eltHandler.handler().then(() => {
        current = (current + 1) % ELEMENT_HANDLERS.length;
        if (current == 0) {
            removeHighlight((ELEMENT_HANDLERS.length - 1).toString());
        }
        else {
            automatic_next();
        }
    });
}
/**
 * Moves and reads the next HTML element in the DOM.
 */
function next() {
    return __awaiter(this, void 0, void 0, function* () {
        yield clean();
        // Handle the next HTML element
        current = (current + 1) % ELEMENT_HANDLERS.length;
        if (tableEltToTable.get(current) != null) { //if the previous would be a table element we skip to the next non-table elt
            console.log("possible issue");
            for (let i = 0; i < ELEMENT_HANDLERS.length; i++) {
                if (!(onlyTableElts.includes(i)) && i > current) {
                    console.log(i);
                    console.log(onlyTableElts);
                    console.log(current);
                    current = i - 1;
                    break;
                }
                if (i === ELEMENT_HANDLERS.length - 1) {
                    console.log("cleaning");
                    clean();
                }
            }
            // @ts-ignore
            // current = tableEltToTable.get(current).startIdx
        }
        let eltHandler = ELEMENT_HANDLERS[current];
        yield eltHandler.handler();
    });
}
/**
 * Moves to the previous HTML element in the DOM.
 */
function previous() {
    return __awaiter(this, void 0, void 0, function* () {
        yield clean();
        // Get previous element (if we are at the first element we loop around)
        let len = ELEMENT_HANDLERS.length;
        current = ((current - 1) % len + len) % len; // This is a way to make the modulo always positive
        if (tableEltToTable.get(current) != null) { //if the previous would be a table element we skip to a non table elt
            console.log("possible issue");
            // @ts-ignore
            current = tableEltToTable.get(current).startIdx;
        }
        // Handle the element
        let eltHandler = ELEMENT_HANDLERS[current];
        yield eltHandler.handler();
    });
}
/**
 * Starts reading the page continuously at the given index.
 */
function startAt(i) {
    return __awaiter(this, void 0, void 0, function* () {
        yield clean();
        current = i;
        automatic_next();
    });
}
/**
 * Starts reading the page continuously.
 */
function start() {
    startAt(0);
}
/**
 * Pauses the reading of the page.
 */
function pause() {
    VOICE_SYNTH.pause();
}
/**
 * Resumes the reading of the page.
 */
function resume() {
    VOICE_SYNTH.resume();
}
/**
 * Listens for keydown events.
 * @param event keydown event
 */
function globalKeystrokes(event) {
    // can change and add key mappings as needed
    if (event.key === " ") {
        event.preventDefault();
        start();
    }
    else if (event.key === "ArrowRight") {
        event.preventDefault();
        changeVoiceRate(1.1);
    }
    else if (event.key === "ArrowLeft") {
        event.preventDefault();
        changeVoiceRate(0.9);
    }
    else if (event.key === "ArrowUp") {
        event.preventDefault();
        previous();
    }
    else if (event.key === "ArrowDown") {
        event.preventDefault();
        next();
    }
    else if (event.key === "p" || event.key === "P") {
        event.preventDefault();
        if (VOICE_SYNTH.paused) {
            resume();
        }
        else {
            pause();
        }
    }
    else if (event.key === "e") {
        console.log(current);
        if (onlyTableElts.includes(current)) {
            readingTable == false;
            event.preventDefault();
            for (let i = 0; i < ELEMENT_HANDLERS.length; i++) {
                if (!(onlyTableElts.includes(i)) && i > current) {
                    console.log(i);
                    console.log(onlyTableElts);
                    console.log(current);
                    startAt(i - 1).then((r) => {
                    });
                    break;
                }
                if (i === ELEMENT_HANDLERS.length - 1) {
                    console.log("cleaning");
                    clean();
                }
            }
        }
    }
}
/**
 * Returns the correct html handler function
 *
 * @param element
 * @return handler function
 */
function getHandlerFunction(element) {
    switch (element.tagName) {
        case "TITLE": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    yield speak("Title: " + element.textContent);
                });
            };
        }
        case "H1": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    yield speak("Header 1: " + element.textContent);
                });
            };
        }
        case "H2": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    yield speak("Header 2: " + element.textContent);
                });
            };
        }
        case "H3": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    yield speak("Header 3: " + element.textContent);
                });
            };
        }
        case "H4": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    yield speak("Header 4: " + element.textContent);
                });
            };
        }
        case "H5": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    yield speak("Header 5: " + element.textContent);
                });
            };
        }
        case "H6": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    yield speak("Header 6: " + element.textContent);
                });
            };
        }
        case "P": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    yield speak("Paragraph: " + element.textContent);
                });
            };
        }
        case "IMG": {
            return function () {
                return __awaiter(this, void 0, void 0, function* () {
                    let img = element;
                    yield speak("Image: ");
                    if (img.alt != null) {
                        yield speak(img.alt);
                    }
                });
            };
        }
        case "A": {
            return () => linkHandler(element);
        }
        case "INPUT": {
            return () => inputHandler(element);
        }
        case "BUTTON": {
            return () => buttonHandler(element);
        }
        case "TABLE": {
            //this takes care of all child elts + extra table behavior
            return () => tableArriveHandler(element);
        }
    }
    return function () {
        return __awaiter(this, void 0, void 0, function* () { });
    };
}
/**
 * Returns the Handler of a given HTML Element
 *
 * @param element
 * @return handler data structure
 */
function getHandler(element) {
    let handler = new ElementHandler(element, getHandlerFunction(element));
    return handler;
}
/**
 * Highglights the HTML Element with the given id
 *
 * @param id
 */
function highlightElement(id) {
    let element = document.getElementById(id);
    if (element) {
        element.style.backgroundColor = "#e9ff32";
    }
}
/**
 * removes the highlight of the HTML Element with the given id
 *
 * @param id
 */
function removeHighlight(id) {
    let element = document.getElementById(id);
    if (element) {
        element.style.backgroundColor = "transparent";
    }
}
/**
 * Creates an handler for an interactive Element elt
 *
 * @param elt
 * @param speech
 * @param enter_fun Function executed when we press enter
 */
function interactiveHandler(elt, speech, enterListener) {
    return __awaiter(this, void 0, void 0, function* () {
        let resolve_fun;
        function arrowDownListener(event) {
            return __awaiter(this, void 0, void 0, function* () {
                if (event.key === "ArrowDown") {
                    event.preventDefault();
                    resolve_fun();
                }
            });
        }
        document.removeEventListener("keydown", globalKeystrokes);
        yield speak(speech);
        yield speak("...Press the down arrow to resume or Enter to interact with it");
        yield (new Promise((resolve) => __awaiter(this, void 0, void 0, function* () {
            resolve_fun = resolve;
            document.addEventListener("keydown", enterListener);
            document.body.addEventListener("keydown", arrowDownListener);
        })));
        document.removeEventListener("keydown", arrowDownListener);
        document.removeEventListener("keydown", enterListener);
        document.addEventListener("keydown", globalKeystrokes);
    });
}
/**
 * Creates a handler of the elements of type Input
 * @param elt
 */
function inputHandler(elt) {
    return __awaiter(this, void 0, void 0, function* () {
        let inputElt = elt;
        const inputType = inputElt.type;
        function enterListener(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                elt.focus();
                if (inputType == "text") {
                    let element = document.getElementById(current.toString());
                    if (element != null) {
                        if (element.onfocus)
                            element.blur();
                        else
                            element.focus();
                    }
                }
                else if (inputType == "submit") {
                    document.getElementById(current.toString()).click();
                }
            }
        }
        let speech = "Input with type " + inputType;
        yield interactiveHandler(elt, speech, enterListener);
    });
}
/**
 * Creates a handler of the elements of type Button
 * @param elt
 */
function buttonHandler(elt) {
    return __awaiter(this, void 0, void 0, function* () {
        let buttonElt = elt;
        function enterListener(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                document.getElementById(current.toString()).click();
            }
        }
        ;
        VOICE_SYNTH.cancel();
        let speech = "Button with " + (buttonElt.value ? "no description" : "the description " + buttonElt.value);
        yield interactiveHandler(elt, speech, enterListener);
    });
}
/**
 * Creates a handler of the elements of type Link
 * @param elt
 */
function linkHandler(elt) {
    return __awaiter(this, void 0, void 0, function* () {
        function enterListener(event) {
            if (event.key === "Enter") {
                event.preventDefault();
                document.getElementById(current.toString()).click();
            }
        }
        yield interactiveHandler(elt, "Hyperlink: " + elt.textContent, enterListener);
    });
}
let curTable;
let curTableRow = 0;
let curTableCol = 0;
/**
 * a function which takes a table as an HTML element and converts it to a table object
 * @param eltId the table id
 */
function parseTable(eltId, start) {
    // initialize base values
    let tbl = {
        startIdx: start,
        eltId: eltId,
        caption: "",
        numRows: 0,
        numCols: 0,
        headers: []
    };
    let elt = document.getElementById(eltId);
    for (let i = 0; i < elt.children.length; i++) {
        // if elt is caption set its innerHTML to caption in table
        if (elt.children[i].tagName == "CAPTION") {
            tbl.caption = elt.children[i].innerHTML;
            // if elt is table header group get its children
        }
        else if (elt.children[i].tagName == "THEAD") {
            for (let j = 0; j < elt.children[i].children.length; j++) {
                // if elt is table row get its children
                if (elt.children[i].children[j].tagName == "TR") {
                    for (let k = 0; k < elt.children[i].children[j].children.length; k++) {
                        // if elt is table header add it to headers array in table and increment the number of columns
                        if (elt.children[i].children[j].children[k].tagName == "TH") {
                            tbl.numCols += 1;
                            tbl.headers.push(elt.children[i].children[j].children[k]);
                        }
                    }
                }
            }
            // if elt is table body get its children
        }
        else if (elt.children[i].tagName == "TBODY") {
            for (let j = 0; j < elt.children[i].children.length; j++) {
                // if elt is table row get its children and increment num rows by one
                if (elt.children[i].children[j].tagName == "TR") {
                    tbl.numRows += 1;
                    let curRow = 0;
                    for (let k = 0; k < elt.children[i].children[j].children.length; k++) {
                        // if elt is table cell give it an id for easy access later
                        if (elt.children[i].children[j].children[k].tagName == "TD") {
                            elt.children[i].children[j].children[k].id = eltId + "-" + String(tbl.numRows - 1) +
                                String(curRow);
                            curRow += 1;
                        }
                    }
                }
            }
        }
    }
    return tbl;
}
/**
 * handler function for link elements ONLY INSIDE TABLE
 * @param elt
 */
function tableLinkHandler(elt) {
    return __awaiter(this, void 0, void 0, function* () {
        // add event listener for enter key
        document.body.addEventListener("keydown", function (event) {
            // Number 13 is the "Enter" key on the keyboard
            if (event.key === "Enter") {
                // Cancel the default action, if needed
                event.preventDefault();
                // Open the link
                window.open(elt.href);
            }
        });
        yield speak("link: " + elt.textContent +
            " Click Enter to open the link. Continue navigating to resume.");
    });
}
/**
 * Generates handler functions for table elements
 * @param elt:  HTMLElement input
 */
function tableArriveHandler(elt) {
    return __awaiter(this, void 0, void 0, function* () {
        readingTable = true;
        let columns = elt.rows[0].cells.length;
        let rows = elt.rows.length;
        yield speak("Reached a table with " + rows + " rows and " + columns + " columns");
        yield speak("Press w, s, a, and d to navigate. Press r to read. Press l for location. Press e to exit when you are inside the table. Press the down arrow to move on to the next element");
        // modality for table
        let current_row = 0;
        let current_col = 0;
        current += 1;
        return new Promise((resolve) => {
            document.body.addEventListener("keydown", function (event) {
                return __awaiter(this, void 0, void 0, function* () {
                    let table = elt;
                    if (event.key === "l" && readingTable == true) {
                        VOICE_SYNTH.cancel();
                        yield speak("Currently at row " + current_row + " and column " + current_col);
                    }
                    if (event.key === "r" && readingTable == true) {
                        let cell = table.rows[current_row].cells[current_col];
                        tableCellHandler(cell);
                    }
                    if (event.key === "d" && readingTable == true) {
                        VOICE_SYNTH.cancel();
                        if (current_col < table.rows[current_row].cells.length - 1) {
                            current_col = current_col + 1;
                            let cell = table.rows[current_row].cells[current_col];
                            tableCellHandler(cell);
                        }
                    }
                    else if (event.key === "a" && readingTable == true) {
                        VOICE_SYNTH.cancel();
                        if (current_col > 0) {
                            current_col = current_col - 1;
                            let cell = table.rows[current_row].cells[current_col];
                            tableCellHandler(cell);
                        }
                    }
                    else if (event.key == "w" && readingTable == true) {
                        VOICE_SYNTH.cancel();
                        if (current_row > 0) {
                            current_row = current_row - 1;
                            let cell = table.rows[current_row].cells[current_col];
                            tableCellHandler(cell);
                        }
                    }
                    else if (event.key == "s" && readingTable == true) {
                        VOICE_SYNTH.cancel();
                        if (current_row < rows - 1) {
                            current_row = current_row + 1;
                            let cell = table.rows[current_row].cells[current_col];
                            tableCellHandler(cell);
                        }
                    }
                });
            });
        });
    });
}
let textTags = ["P", "H1", "H2", "H3", "H4", "H5", "H6", "LABEL", "TITLE"];
/**
 * Generates handler functions for text elements
 * @param elt: HTMLElement input
 */
function textOnlyHandler(elt) {
    return __awaiter(this, void 0, void 0, function* () {
        if (elt.tagName == "TITLE") {
            yield speak("Title " + elt.textContent);
        }
        else if (elt.tagName == "LABEL") {
            yield speak("Label " + elt.textContent);
        }
        else {
            yield speak(elt.textContent);
        }
    });
}
/**
 * Generates handler functions for image elements
 * @param elt: HTMLElement input
 */
function imgHandlers(e) {
    return __awaiter(this, void 0, void 0, function* () {
        if (e.alt != "") {
            yield speak("This is a picture of " + e.alt);
        }
        else {
            yield speak("There is a picture here");
        }
    });
}
/**
 * used to handle elements that are cells of a table
 * @param htmlElt: HTMLElement input
 */
function singleElementHandler(htmlElt) {
    console.log("Handled elt " + htmlElt.tagName);
    if (textTags.indexOf(htmlElt.tagName) > -1) {
        textOnlyHandler(htmlElt);
    }
    else if (htmlElt.tagName == "IMG") {
        imgHandlers(htmlElt);
    }
    else if (htmlElt.tagName == "A") {
        tableLinkHandler(htmlElt);
    }
    else if (htmlElt.tagName == "INPUT") {
        inputHandler(htmlElt);
    }
    else if (htmlElt.tagName == "BUTTON") {
        buttonHandler(htmlElt);
    }
    else if (htmlElt.tagName == "TABLE") {
        console.log("GOT HERE");
        tableArriveHandler(htmlElt);
    }
    else {
        return false;
    }
    return true;
}
/**
 * Generates handler functions for table cell elements. Handles
 * DOM children elements
 * @param cell:  HTMLTableCellElement input
 */
function tableCellHandler(cell) {
    return __awaiter(this, void 0, void 0, function* () {
        let children = cell.children;
        clean();
        highlightElement(cell.id);
        console.log("cell id" + cell.id);
        console.log("current" + current);
        for (let child of children) {
            singleElementHandler(child);
        }
        if (children.length == 0) {
            yield speak(cell.textContent);
            // only reads textContent if not have children (otherwise double read)
        }
        removeHighlight(cell.id);
    });
}
