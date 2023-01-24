document.querySelector('#submitButton').addEventListener('click', () => {
    let dataToSend = {
        haveSpaceForHandicapped: localStorage.getItem("disabledParking"),
        isGuarded: localStorage.getItem("guardedParking"),
        isPaid: localStorage.getItem("paidParking"),
        atLeast15FreePlaces: localStorage.getItem("hugeParking"),
        isPrivate: localStorage.getItem("privateParking"),
        haveSpacesForElectrics: localStorage.getItem("electricCarParking"),
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
            //hide unnecessary elements
            //document.getElementById("formContainer").style.display = "none";
            document.getElementById("mapHeaderContent").style.display = "none";
            document.getElementById("submitButton").style.display = "none";
            document.getElementById("mapCanvas").style.display = "none";
            document.getElementById("cityImage").style.display = "none";
            canvasElement.removeEventListener('click', canvasEventListener)
            showFinalCanvas();

            //show new element to display results
            document.getElementById("resultContainer").style.display = "flex";

            let results = JSON.parse(xhr.responseText);

            //highlight neighbour zones around selected zone on canvas
            for (const highlightedHexagon of results.resultZoneDtos) {
                const id = highlightedHexagon.zoneId - 1;
                if (id !== selectedZoneData.id - 1)
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
            console.log(listOfZonesCoordinates)

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

})


function showFinalCanvas(){
    document.getElementById("mapCanvas").style.display = "inherit";
    document.getElementById("cityImage2").style.display = "inherit";
    let canvasElement = document.querySelector('#finalMapCanvas')
    let mapCanvas = canvasElement.getContext('2d');

    let finalMapWidth = window.innerWidth * (40 / 100)
    let finalMapHeight = window.innerWidth * (25 / 100)

    mapCanvas.canvas.width = finalMapWidth;
    mapCanvas.canvas.height = finalMapHeight;

    document.querySelector("#cityImage2").width = finalMapWidth;
    document.querySelector("#cityImage2").height = finalMapHeight;
    drawNetOfHexagons(mapCanvas, listOfZonesCoordinates);
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