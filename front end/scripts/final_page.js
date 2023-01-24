//setting up canvas element
let canvasElement = document.querySelector('#mapCanvas');
let mapCanvas = canvasElement.getContext('2d');

let mapWidth = window.innerWidth * (60 / 100);
let mapHeight = window.innerWidth * (40 / 100);

document.querySelector("#cityImage").width = mapWidth;
document.querySelector("#cityImage").height = mapHeight;

mapCanvas.canvas.width = mapWidth;
mapCanvas.canvas.height = mapHeight;

let dataToSend = {
    haveSpaceForHandicapped: localStorage.getItem("disabledParking"),
    isGuarded: localStorage.getItem("guardedParking"),
    isPaid: localStorage.getItem("paidParking"),
    atLeast15FreePlaces: localStorage.getItem("hugeParking"),
    isPrivate: localStorage.getItem("privateParking"),
    haveSpacesForElectrics: localStorage.getItem("electricCarParking"),
    zoneCordX: localStorage.getItem("zoneCordX"),
    zoneCordY: localStorage.getItem("zoneCordY"),
};
let zonesJson = localStorage.getItem("zones");
let zones = JSON.parse(zonesJson);
let listOfZonesCoordinates = getListOfZonesCoordinates(zones);
let selectedZoneId = localStorage.getItem("selectedZoneId");
let dataToSendAsJson = JSON.stringify(dataToSend);
let xhr = new XMLHttpRequest();
let url = "http://localhost:8081/requiredparkinglot";
xhr.open("POST", url, true);
xhr.setRequestHeader("Content-Type", "application/json");
xhr.send(dataToSendAsJson);

xhr.onreadystatechange = (e) => {
    console.log(e.target.readyState);
    if (e.target.readyState === 4) {
        //hide unnecessary elements
        //document.getElementById("formContainer").style.display = "none";
        // showFinalCanvas();

        //show new element to display results
        document.getElementById("resultContainer").style.display = "flex";

        let results = JSON.parse(xhr.responseText);

        drawNetOfHexagons(mapCanvas, listOfZonesCoordinates);
        //highlight neighbour zones around selected zone on canvas
        for (const highlightedHexagon of results.resultZoneDtos) {
            const id = highlightedHexagon.zoneId - 1;
            if (id !== selectedZoneId - 1)
                drawHexagon(mapCanvas,
                    listOfZonesCoordinates[id].x,
                    listOfZonesCoordinates[id].y,
                    listOfZonesCoordinates[id].radius,
                    '#ffffff',
                    true,
                    'rgba(38,161,199,0.35)'
                )
            mapCanvas.fillStyle = "#222222";
            mapCanvas.font = "18px Arial";
            mapCanvas.fillText(
                String(id+1),
                listOfZonesCoordinates[id].x,
                listOfZonesCoordinates[id].y,
            )
        }

        //display point on canvas on best parking lot

        console.log(results)
        console.log(listOfZonesCoordinates);

        const parkingTable = document.querySelector("#parkingLots");
        const zoneTable = document.querySelector("#zones");

        let bestparkingZone = 9999;

        let rowIndex = 1;
        for (const zone of results.resultZoneDtos) {
            zoneTable.insertRow(rowIndex).innerHTML = getRowWithZoneData(zone);

            for (let i = 0; i < zone.resultParkingLotDtos.length - 1; i++) {
                zoneTable.insertRow(rowIndex + i + 1).innerHTML = "<tr><td class='invisibleCell'>-</td></tr>";
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
        //
        document.getElementById("bestParkingResult").innerHTML = results.bestParkingLotDto.parkingLotId;
        document.getElementById("bestParkingResultZone").innerHTML = bestparkingZone+1;
        //
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

function drawNetOfHexagons(mapCanvas, listOfCoordinates) {
    mapCanvas.clearRect(0, 0, mapWidth, mapHeight);
    for (let i = 0; i < listOfCoordinates.length; i++)
        drawHexagon(mapCanvas, listOfCoordinates[i].x, listOfCoordinates[i].y, listOfCoordinates[i].radius, '#ffffff');
    if (selectedZoneId != null) {
        drawHexagon(mapCanvas,
            listOfCoordinates[selectedZoneId - 1].x,
            listOfCoordinates[selectedZoneId- 1].y,
            listOfCoordinates[selectedZoneId - 1].radius,
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

    const x0 = mapWidth / 15;
    const y0 = mapHeight / 7;

    //todo: dać równe marginesy dla siatki hexagonów, tzn. żeby na dole była taka przerwa jak na górze

    const a = (mapWidth - margin * widthOfNet - x0) / widthOfNet + 1;
    const b = (mapHeight - margin * heightOfNet - y0) / heightOfNet + 1;
    let c = a < b ? a : b;
    const hexagonRadius = c / 1.7 + 1;

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

