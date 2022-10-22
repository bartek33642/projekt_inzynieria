

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




