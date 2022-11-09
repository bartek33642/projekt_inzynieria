//setting up canvas element
let mapCanvas = document.getElementById('mapCanvas').getContext('2d');

let mapWidth = window.innerWidth * (40/100)
let mapHeight = window.innerWidth * (25/100)

mapCanvas.canvas.width  = mapWidth;
mapCanvas.canvas.height = mapHeight;


let backgroundCityImage = new Image(mapWidth, mapHeight)
backgroundCityImage.src = "images/tarnów.png"

backgroundCityImage.onload = () =>
    mapCanvas.drawImage(backgroundCityImage, 0, 0)

// fetch('http://localhost:8081/zones', {
//     method: 'GET',
//     headers: {
//         'Accept': 'application/json',
//     },
// })
let headers = new Headers();
headers.append('Access-Control-Allow-Origin', 'http://localhost:8081');
headers.append('Access-Control-Allow-Credentials', 'true');

const Http = new XMLHttpRequest();
Http.open('GET', 'http://localhost:8081/zones');
Http.send();

Http.onreadystatechange = (e) => {
    console.log(Http.responseText)
}


drawHexagon = (posX, posY, size, borderColor = "#000000", isFilled = false, fillColor = "#ffffff") => {
    let pi = Math.PI;
    let r = size / 2;

    mapCanvas.lineWidth = 5;

    mapCanvas.fillStyle = fillColor;
    mapCanvas.strokeStyle  = borderColor;

    mapCanvas.beginPath();
    mapCanvas.moveTo( posX + r * Math.cos( pi/3 ), posY + r * Math.sin( pi/3 ));
    mapCanvas.lineTo( posX + r , posY);
    mapCanvas.lineTo( posX + r * Math.cos( 5*pi/3 ), posY + r * Math.sin( 5*pi/3 ));
    mapCanvas.lineTo( posX + r * Math.cos( 4*pi/3 ), posY + r * Math.sin( 4*pi/3 ));
    mapCanvas.lineTo( posX - r , posY);
    mapCanvas.lineTo( posX + r * Math.cos( 2*pi/3 ), posY + r * Math.sin( 2*pi/3 ));
    mapCanvas.lineTo( posX + r * Math.cos( pi/3 ), posY + r * Math.sin( pi/3 ));
    mapCanvas.lineTo( posX + r , posY);

    isFilled ? mapCanvas.fill() : mapCanvas.stroke()

    if(isFilled){
        mapCanvas.fill();
        drawHexagon(posX, posY, size, borderColor, 0);
    } else {
        mapCanvas.stroke();
    }
}

drawHexagon(100, 100, 150, '#007074', 1, '#ad1a1a');




