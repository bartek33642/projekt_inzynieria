//setting up canvas element
let canvasElement = document.querySelector('#mapCanvas')
let mapCanvas = canvasElement.getContext('2d');

let mapWidth = window.innerWidth * (40 / 100)
let mapHeight = window.innerWidth * (25 / 100)

mapCanvas.canvas.width = mapWidth;
mapCanvas.canvas.height = mapHeight;

document.querySelector("#cityImage").width = mapWidth;
document.querySelector("#cityImage").height = mapHeight;


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
    drawNetOfHexagons(listOfZonesCoordinates);
}

const canvasEventListener = (e) => {
    let {x, y} = getMousePositionFromCanvas(e);
    let idOfClickedZone = getIdOfClickedZone(x, y, listOfZonesCoordinates);
    setSelectedZoneInfo(idOfClickedZone);
    drawNetOfHexagons(listOfZonesCoordinates);
}
canvasElement.addEventListener("click", canvasEventListener);


//send form and
document.querySelector('#submitButton').addEventListener('click', () => {
    let dataToSend = {
        haveSpaceForHandicapped: document.getElementsByName('form-disabledParking')[0].checked,
        isGuarded: document.getElementsByName('form-guardedParking')[0].checked,
        isPaid: document.getElementsByName('form-paidParking')[0].checked,
        atLeast15FreePlaces: document.getElementsByName('form-hugeParking')[0].checked,
        isPrivate: document.getElementsByName('form-privateParking')[0].checked,
        haveSpacesForElectrics: document.getElementsByName('form-electricCarParking')[0].checked,
        zoneCordX: selectedZoneData.x,
        zoneCordY: selectedZoneData.y,
    };
    let dataToSendAsJson = JSON.stringify(dataToSend);
    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8081/requiredparkinglot";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(dataToSendAsJson);

    // let wasFunctionBelowCalledBefore = false;

    xhr.onreadystatechange = (e) => {
        console.log(e.target.readyState);
        if (e.target.readyState === 4) {


            // if(wasFunctionBelowCalledBefore)
            //     return 0;
            // else
            //     wasFunctionBelowCalledBefore = true;
            //hide unnecessary elements
            document.getElementById("formContainer").style.display = "none";
            document.getElementById("mapHeaderContent").style.display = "none";
            document.getElementById("submitButton").style.display = "none";
            canvasElement.removeEventListener('click', canvasEventListener)

            //show new element to display results
            document.getElementById("resultContainer").style.display = "flex";

            let results = JSON.parse(xhr.responseText);
            console.log(results);
            //highlight neighbour zones around selected zone on canvas
            for (const highlightedHexagon of results.resultZoneDtos) {
                const id = highlightedHexagon.zoneId - 1;
                if (id !== selectedZoneData.id - 1)
                    drawHexagon(
                        listOfZonesCoordinates[id].x,
                        listOfZonesCoordinates[id].y,
                        listOfZonesCoordinates[id].radius,
                        '#000000',
                        true,
                        'rgba(38,161,199,0.35)'
                    )
            }

            //display point on canvas on best parking lot

            console.log(results)

            const parkingTable = document.querySelector("#parkingLots");
            const zoneTable = document.querySelector("#zones");

            let bestparkingZone = 9999;

            let rowIndex = 1;
            for (const zone of results.resultZoneDtos) {
                zoneTable.insertRow(rowIndex).innerHTML = getRowWithZoneData(zone);

                for (let i = 0; i < zone.resultParkingLotDtos.length - 1; i++) {
                    zoneTable.insertRow(rowIndex + i + 1).innerHTML = "<tr><td class='invisibleCell'>a</td></tr>";
                }
                for (const parkingLot of zone.resultParkingLotDtos) {
                    parkingTable.insertRow(rowIndex).innerHTML = getRowWithParkingLotData(parkingLot);
                    if (results.bestParkingLotDto.parkingLotId === parkingLot.parkingLotId)
                        bestparkingZone = zone.zoneId - 1;
                    rowIndex++;
                }
                rowIndex++;
                zoneTable.insertRow(rowIndex - 1).innerHTML = "<tr><td class='bufferCell' colspan='4'></td></tr>";
                parkingTable.insertRow(rowIndex - 1).innerHTML = "<tr><td class='bufferCell' colspan='4'></td></tr>";

            }

            //display point on canvas on best parking lot
            let scale = listOfZonesCoordinates[bestparkingZone].radius / 10

            mapCanvas.beginPath();
            mapCanvas.arc(
                listOfZonesCoordinates[bestparkingZone].x + ((Math.random()-1)*7*scale),
                listOfZonesCoordinates[bestparkingZone].y + ((Math.random()-1)*6*scale),
                scale,
                0,
                2 * Math.PI
            );
            mapCanvas.fillStyle = '#ff0000';
            mapCanvas.fill();

        }

    }

})

//end of "main"


function getRowWithParkingLotData(parkingLot) {
    //let result = "<tr>";
    let result = "";
    result += "<td>" + String(parkingLot.parkingLotId) + "</td>";
    result += "<td>" + String(parkingLot.freeSpaces) + "</td>";
    result += "<td>" + String(parkingLot.points) + "</td>";
    result += "<td>" + String(parkingLot.score) + "</td>";
    //result += "</tr>";
    return result;
}

function getRowWithZoneData(zone) {
    //let result = "<tr>";
    let result = "";
    result += "<td class='highlightedCell'>" + String(zone.zoneId) + "</td>";
    result += "<td class='highlightedCell'>" + String(zone.accessibilityFactor) + "</td>";
    result += "<td class='highlightedCell'>" + String(zone.attractivenessFactor) + "</td>";
    result += "<td class='highlightedCell'>" + String(zone.demandFactor) + "</td>";
    //result += "</tr>";
    return result;
}

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

function drawNetOfHexagons(listOfCoordinates) {
    mapCanvas.clearRect(0, 0, mapWidth, mapHeight);
    for (let i = 0; i < listOfCoordinates.length; i++)
        drawHexagon(listOfCoordinates[i].x, listOfCoordinates[i].y, listOfCoordinates[i].radius);
    if (selectedZoneData.id != null) {
        drawHexagon(
            listOfCoordinates[selectedZoneData.id - 1].x,
            listOfCoordinates[selectedZoneData.id - 1].y,
            listOfCoordinates[selectedZoneData.id - 1].radius,
            '#000000',
            true,
            'rgba(37,182,61,0.78)'
        )
    }

}

function drawHexagon(posX, posY, radius, borderColor = "#000000", isFilled = false, fillColor = "#ffffff") {
    let pi = Math.PI;

    mapCanvas.lineWidth = 1;

    mapCanvas.fillStyle = fillColor;
    mapCanvas.strokeStyle = borderColor;

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
        drawHexagon(posX, posY, radius, borderColor, 0);
    } else {
        mapCanvas.stroke();
    }
}

