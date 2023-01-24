//setting up canvas element
let canvasElement = document.querySelector('#mapCanvas')
let mapCanvas = canvasElement.getContext('2d');

let mapWidth = window.innerWidth
let mapHeight = window.innerHeight

mapCanvas.canvas.width = mapWidth;
mapCanvas.canvas.height = mapHeight;

document.querySelector("#cityImage").width = mapWidth * 0.99;
document.querySelector("#cityImage").height = mapHeight * 0.99;

const result = localStorage.getItem("disabledParking");
console.log(result);

const Http = new XMLHttpRequest();
Http.open('GET', 'http://localhost:8081/zones');
Http.send();

let Zones = [];
let listOfZonesCoordinates;

let selectedZoneData = {
    id: null,
    x: null,
    y: null
};

//async function called when JSON is received
Http.onreadystatechange = (e) => {
    Zones = JSON.parse(Http.responseText);
    listOfZonesCoordinates = getListOfZonesCoordinates(Zones);
    drawNetOfHexagons(mapCanvas, listOfZonesCoordinates);
}

const canvasEventListener = (e) => {
    let {x, y} = getMousePositionFromCanvas(e);
    let idOfClickedZone = getIdOfClickedZone(x, y, listOfZonesCoordinates);
    setSelectedZoneInfo(idOfClickedZone);
    drawNetOfHexagons(mapCanvas, listOfZonesCoordinates);
}
canvasElement.addEventListener("click", canvasEventListener);



//end of "main"

getMousePositionFromCanvas = (e) => {
    let rect = canvasElement.getBoundingClientRect();
    return {
        x: e.clientX - rect.left,
        y: e.clientY - rect.top
    };
}

setSelectedZoneInfo = (idOfClickedZone) => {
    let zone = Zones[idOfClickedZone - 1];

    selectedZoneData.id = idOfClickedZone;
    selectedZoneData.x = zone.cordX;
    selectedZoneData.y = zone.cordY;
    localStorage.setItem("zoneCordX",selectedZoneData.x);
    localStorage.setItem("zoneCordY",selectedZoneData.y);
    localStorage.setItem("selectedZoneId",selectedZoneData.id);
    let zonesJson = JSON.stringify(Zones);
    localStorage.setItem("zones",zonesJson);
    window.location.href = 'final_page.html';
}

function getIdOfClickedZone(mouseX, mouseY, zonesCoords) {
    let bestCandidateId;
    let bestCandidateDistance = 9999;

    for (let i = 0; i < zonesCoords.length; i++) {
        const distance = Math.sqrt((mouseX - zonesCoords[i].x) ** 2 + (mouseY - zonesCoords[i].y) ** 2);
        if (distance < bestCandidateDistance && distance <= zonesCoords[i].radius) {
            bestCandidateDistance = distance;
            bestCandidateId = zonesCoords[i].zoneId;
        }

    }
    return bestCandidateId;
}

function getListOfZonesCoordinates(zones) {
//const getListOfZonesCoordinates = (zones) => {
    let widthOfNet = 0, heightOfNet = 0;

    for (let i = 0; i < zones.length; i++) {
        if (zones[i].cordY > heightOfNet)
            heightOfNet = zones[i].cordY;
        if (zones[i].cordX > widthOfNet)
            widthOfNet = zones[i].cordX;
    }

    const margin = 0;

    const x0 = mapWidth / 6;
    const y0 = mapHeight / 9;

    //todo: dać równe marginesy dla siatki hexagonów, tzn. żeby na dole była taka przerwa jak na górze

    const a = (mapWidth - margin * widthOfNet - x0) / widthOfNet + 1;
    const b = (mapHeight - margin * heightOfNet - y0) / heightOfNet + 1;
    let c = a < b ? a : b;
    const hexagonRadius = c / 2 + 1;

    let listOfCoordinatesOfHexagons = new Array(zones.length);

    for (let i = 0; i < zones.length; i++) {
        const cordX = zones[i].cordX;
        const cordY = zones[i].cordY;

        const oddRowAdjustment = cordX % 2 ? hexagonRadius * Math.sin(Math.PI / 3) : 0;

        const x = (cordX * hexagonRadius) * (1 + Math.cos(Math.PI / 3)) + x0 + margin * cordX;
        const y = mapHeight - ((cordY * 2 * hexagonRadius) * Math.sin(Math.PI / 3) + oddRowAdjustment + y0 + margin * cordY);
        const zoneId = zones[i].zoneId

        listOfCoordinatesOfHexagons[i] = {
            x: x,
            y: y,
            zoneId: zoneId,
            radius: hexagonRadius
        };
    }
    return listOfCoordinatesOfHexagons;
}

function drawNetOfHexagons(mapCanvas, listOfCoordinates) {
    mapCanvas.clearRect(0, 0, mapWidth, mapHeight);
    for (let i = 0; i < listOfCoordinates.length; i++)
        drawHexagon(mapCanvas, listOfCoordinates[i].x, listOfCoordinates[i].y, listOfCoordinates[i].radius, '#ffffff');
    if (selectedZoneData.id != null) {
        drawHexagon(mapCanvas,
            listOfCoordinates[selectedZoneData.id - 1].x,
            listOfCoordinates[selectedZoneData.id - 1].y,
            listOfCoordinates[selectedZoneData.id - 1].radius,
            '#ffffff',
            true,
            'rgba(37,182,61,0.78)'
        )
    }

}

function drawHexagon(mapCanvas, posX, posY, radius, borderColor = "#ffffff", isFilled = false, fillColor = "#ffffff") {
    let pi = Math.PI;

    mapCanvas.lineWidth = 1;

    mapCanvas.fillStyle = fillColor;
    mapCanvas.strokeStyle = borderColor;
    mapCanvas.lineWidth = 3;

    mapCanvas.beginPath();
    mapCanvas.moveTo(posX + radius * Math.cos(pi / 3), posY + radius * Math.sin(pi / 3));
    mapCanvas.lineTo(posX + radius, posY);
    mapCanvas.lineTo(posX + radius * Math.cos(5 * pi / 3), posY + radius * Math.sin(5 * pi / 3));
    mapCanvas.lineTo(posX + radius * Math.cos(4 * pi / 3), posY + radius * Math.sin(4 * pi / 3));
    mapCanvas.lineTo(posX - radius, posY);
    mapCanvas.lineTo(posX + radius * Math.cos(2 * pi / 3), posY + radius * Math.sin(2 * pi / 3));
    mapCanvas.lineTo(posX + radius * Math.cos(pi / 3), posY + radius * Math.sin(pi / 3));
    mapCanvas.lineTo(posX + radius, posY);

    if (isFilled) {
        mapCanvas.fill();
        drawHexagon(mapCanvas, posX, posY, radius, borderColor, 0);
    } else {
        mapCanvas.stroke();
    }
}

