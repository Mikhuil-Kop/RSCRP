$(document).ready(function () {
        // Popups
    $('#popup-func').click(function () {
        let funcType = $('.popup__methods').data('popup-func-type');
        let robotID = $('.popup__methods').data('pupop-robot-id');
        if(funcType == 'add'){
            addRobotRecord();
        }
        else if (funcType == 'update'){
            updateRobotRecord(robotID);
        }
    });
    
    $('#add-robot').click(function () {
        $('.popup-func__name').text('Добавить робота');
        $('#popup-func').text('Добавить');
        $('.popup__methods').data('popup-func-type', 'add');
        $('.popup').css('display', 'block');
    });

    $('.robot__data').on('click', '.record-update', function () {
        $('.popup-func__name').text('Обновить данные робота');
        $('#popup-func').text('Обновить');
        $('.popup__methods').data('popup-func-type', 'update');
        $('.popup').css('display', 'block');
        $('.popup__methods').data('pupop-robot-id', $(this).parents('.robot-data__row').data('robot-id'));
    });

    $('.robot__data').on('click', '.record-delete', function () {
        let robotID = $(this).parents('.robot-data__row').data('robot-id');
        deleteRobotRecord(robotID);
    });

    $('#popup-close').click(popupClose);
    
    // Task
    $('#send-calc').click(function () {
        console.log("Нажата кнопка 'Рассчитать'");
        
        $.get({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=calc",
            data: {
                'calc-a': $('#calc-a').val(),
                'calc-b': $('#calc-b').val(),
                'calc-s': $('#calc-sign').val()
            },
            success: function(data) {
                console.log(data);
                $('#calc-answ').text("asd");
            },
            error: function (e) {
                console.log(e);
                $('#calc-answ').text("Ошибка");
            }
        });
    });

    // Table
    getRobotData();

    function popupClose() {
        $('.popup').css('display', 'none');
    }

    function loadRobotTable(response) {
        response = JSON.parse(response);
        response.forEach(responseElem => {
            $('.robot__data').append(
                `<div class="robot-data__row" data-robot-id="${responseElem.ID}">` +
                    `<div class="data-row row--delete"><button class="button record-delete">Удалить</button></div>` +
                    `<div class="data-row row--update"><button class="button record-update">Изменить</button></div>` +
                    `<div class="data-row row--name">${responseElem.name}</div>` +
                    `<div class="data-row row--lname">${responseElem.description}</div>` +
                    `<div class="data-row row--mname">${responseElem.skill1}</div>` +
                    `<div class="data-row row--yofb">${responseElem.skill2}</div>` +
                `</div>` 
            );
        });
    }

    function getRobotData() {
        clearRobotTable();
        let robotsData;
        $.ajax({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=table",
            data: {
                'mode': 'get',
            },
            complete: function (data) {
                loadRobotTable(data.responseText);
            }
        });
        
    }

    function clearRobotTable() {
        $('.robot-data__row').remove();
    }

    function addRobotRecord() {
        let name = $('#robot-name').val();
        let description = $('#robot-description').val();
        let skill1 = $('#robot-skill1').val();
        let skill2 = $('#robot-skill2').val();
        $.ajax({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=table",
            data: {
                'mode': 'add',
                'name': name,
                'description': description,
                'skill1': skill1,
                'skill2': skill2
            },
            success: function (data) {
                popupClose();
                getRobotData();
            }
        });

    }

    function updateRobotRecord(robotID) {
        let newRobotData = {};
        
        newRobotData['mode'] = 'update';
        newRobotData['id'] = robotID;

        if($('#robot-name').val() != ''){
            newRobotData['name'] = $('#robot-name').val();
        }
        if($('#robot-desc').val() != ''){
            newRobotData['desc'] = $('#robot-desc').val();
        }
        if($('#robot-skill1').val() != ''){
            newRobotData['skill1'] = $('#robot-skill1').val();
        }
        if($('#robot-skill2').val() != ''){
            newRobotData['skill2'] = $('#robot-skill2').val();
        }

        $.ajax({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=table",
            data: newRobotData,
            success: function (data) {
                console.log(data);
                popupClose();
                getRobotData();
            },
        });

    }

    function deleteRobotRecord(robotID) {
        $.ajax({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=table",
            data: {
                'mode': 'delete',
                'id': robotID
            },
            success: function () {
                getRobotData();
            },
        });

    }
});