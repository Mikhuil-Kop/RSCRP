/*
Игра «Жизнь».
Игра моделирует жизнь поколений гипотетической колонии живых клеток на прямоугольном игровом поле,
которые выживают, размножаются или погибают в соответствии со следующими правилами.

Для каждого поколения (шага игры) применяются следующие правила: каждая живая клетка, количество
соседей которой меньше двух или больше трёх, погибает; каждая живая клетка, у которой от двух до трёх соседей, 
живёт до следующего хода; каждая мёртвая клетка, у которой есть ровно три соседа, оживает. 
Соседи клетки – это все соседние с ней клетки по горизонтали, вертикали и диагонали, всего восемь соседей.

Правила применяются ко всему игровому полю одновременно, а не к каждой из клеток по очереди.
То есть подсчёт количества соседей происходит в один момент перед следующим шагом, и изменения, происходящие в соседних клетках, не влияют на новое состояние клетки.
Среди прочего использовать возможности, предоставляемые фреймворком jQuery.
*/

var osmos;
var cells;
var count = 0;
var size = 30;

$(function () {
    size = prompt("Введите размер поля", 10);
    
    cells = new Array(size);
    for (var i = 0; i < size; i++) {
        cells[i] = new Array(size);
        for(var j = 0; j < size; j++){
            $("#osmos").append("<div class='cell' id='"+idOf(i,j)+"'>");
            var c = new Cell($("#osmos").find("#" + idOf(i,j)));
            c.element.css("top", (100.0 * i / size) +'%');
            c.element.css("left", (100.0 * j / size) +'%');
            c.element.css("height", (100.0 / size) +'%');
            c.element.css("width", (100.0 / size) +'%');
            
            if(getRandomInt(10) > 6)
                c.Spawn();
            
            cells[i][j] = c;
        }
    }
    
    setTimeout(update, 500);
});


function update(){
    for(var i = 0; i < size; i++){
        for(var j = 0; j < size; j++){
            var c = cells[i][j];
            var n = countNeighbors(i,j);
            if(c.alive && (n < 2 || n > 4)){
                c.Kill();
            }
            if(!c.alive && n == 3)
                c.Spawn();
        }
    }
    
    count++;
    $("#counter").text(count);
    
    setTimeout(update, 500);
}

function countNeighbors(x, y){
    var c = 0;
    
    for(var i = -1; i <= 1; i++){
        for(var j = -1; j <= 1; j++){
            var nx = x + i;
            var ny = y + j;
            if(nx >= 0 && nx < size
               && ny >= 0 && ny < size 
               && i != 0 && j != 0
               && cells[nx][ny].alive){
                c = c + 1;
            }
        }
    }
    return c;
}

function idOf(x,y){
    return "cell_" + (x * size + y);
}

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max));
}

class Cell {
    constructor(element) {
        this.element = element;
        this.Kill();
    }
    
    Spawn(){
        this.alive = true;
        this.element.removeClass("dead");
        this.element.addClass("alive");
    }
    
    Kill(){
        this.alive = false;
        this.element.removeClass("alive");
        this.element.addClass("dead");
    }

}