//setting up canvas element
let canvasElement = document.getElementById('mapCanvas')
let mapCanvas = canvasElement.getContext('2d');

let mapWidth = window.innerWidth * (40/100)
let mapHeight = window.innerWidth * (25/100)

mapCanvas.canvas.width  = mapWidth;
mapCanvas.canvas.height = mapHeight;

let backgroundCityImage = new Image(mapWidth, mapHeight)
backgroundCityImage.src = "images/tarnów.png"
//TODO: zmienić rozmiar mapy tarnowa w tle (nie wiem kurde jak to zrobić xd)

backgroundCityImage.onload = () =>
    mapCanvas.drawImage(backgroundCityImage, 0, 0)

const Http = new XMLHttpRequest();
Http.open('GET', 'http://localhost:8081/zones');
Http.send();

let Zones;
let listOfZonesCoordinates;

//async function called when JSON is received
Http.onreadystatechange = (e) => {
    Zones = JSON.parse(Http.responseText);
    listOfZonesCoordinates = getListOfZonesCoordinates(Zones);
    drawNetOfHexagons(listOfZonesCoordinates);
}

canvasElement.addEventListener("click", (e) =>{
    let {x,y} = getMousePositionFromCanvas(e);
    let idOfClickedZone = getIdOfClickedZone(x, y, listOfZonesCoordinates);
    console.log(idOfClickedZone);
});
//end of "main"


getMousePositionFromCanvas = (e) => {
    let rect = canvasElement.getBoundingClientRect();
    return {
        x: e.clientX - rect.left,
        y: e.clientY - rect.top
    };
}

getIdOfClickedZone = (mouseX, mouseY, zonesCoords) => {
    let bestCandidateId;
    let bestCandidateDistance=9999;

    for (let i = 0; i < zonesCoords.length; i++) {
        const distance = Math.sqrt( (mouseX - zonesCoords[i].x) ** 2 + (mouseY - zonesCoords[i].y) ** 2 );
        if (distance < bestCandidateDistance && distance <= zonesCoords[i].radius){
            bestCandidateDistance = distance;
            bestCandidateId = zonesCoords[i].zoneId;
        }

    }
    return bestCandidateId;
}

getListOfZonesCoordinates = (zones) => {
    let widthOfNet = 0, heightOfNet = 0;

    for(let i = 0; i < zones.length; i++){
        if( zones[i].cordY > heightOfNet)
            heightOfNet = zones[i].cordY;
        if( zones[i].cordX > widthOfNet)
            widthOfNet = zones[i].cordX;
    }

    const margin = 0;

    const x0 = 40;
    const y0 = 40;

    //todo: dać równe marginesy dla siatki hexagonów, tzn. żeby na dole była taka przerwa jak na górze

    const a = (mapWidth - margin * widthOfNet - x0 * 2) / widthOfNet+1;
    const b = (mapHeight - margin * heightOfNet - y0 * 2) / heightOfNet+1;
    let c = a < b ? a : b;
    const hexagonRadius = c/2 -1;

    let listOfCoordinatesOfHexagons = new Array(zones.length);

    for(let i = 0; i < zones.length; i++){
        const cordX = zones[i].cordX;
        const cordY = zones[i].cordY;

        const oddRowAdjustment = cordX % 2 ? hexagonRadius * Math.sin( Math.PI / 3 ) : 0;

        const x = (cordX * hexagonRadius) * (1 + Math.cos( Math.PI / 3 )) + x0 + margin * cordX;
        const y = mapHeight - ( ( cordY * 2 * hexagonRadius) * Math.sin( Math.PI / 3 ) + oddRowAdjustment + y0 + margin * cordY);
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

drawNetOfHexagons = (listOfCoordinates) =>{
    for (let i = 0; i < listOfCoordinates.length; i++) {
        drawHexagon( listOfCoordinates[i].x, listOfCoordinates[i].y, listOfCoordinates[i].radius );
    }
}

drawHexagon = (posX, posY, radius, borderColor = "#000000", isFilled = false, fillColor = "#ffffff") => {
    let pi = Math.PI;

    mapCanvas.lineWidth = 1;

    mapCanvas.fillStyle = fillColor;
    mapCanvas.strokeStyle  = borderColor;

    mapCanvas.beginPath();
    mapCanvas.moveTo( posX + radius * Math.cos( pi/3 ), posY + radius * Math.sin( pi/3 ));
    mapCanvas.lineTo( posX + radius , posY);
    mapCanvas.lineTo( posX + radius * Math.cos( 5*pi/3 ), posY + radius * Math.sin( 5*pi/3 ));
    mapCanvas.lineTo( posX + radius * Math.cos( 4*pi/3 ), posY + radius * Math.sin( 4*pi/3 ));
    mapCanvas.lineTo( posX - radius , posY);
    mapCanvas.lineTo( posX + radius * Math.cos( 2*pi/3 ), posY + radius * Math.sin( 2*pi/3 ));
    mapCanvas.lineTo( posX + radius * Math.cos( pi/3 ), posY + radius * Math.sin( pi/3 ));
    mapCanvas.lineTo( posX + radius , posY);

    if(isFilled){
        mapCanvas.fill();
        drawHexagon(posX, posY, radius, borderColor, 0);
    } else {
        mapCanvas.stroke();
    }
}


