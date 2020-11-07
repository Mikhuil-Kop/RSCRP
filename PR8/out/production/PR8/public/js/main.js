$(document).ready(function () {
    // Task

    function renderMatrices(response) {
        $('body').append(
            `<section class="matrices"><div class="matrices__wrapper"></div></section>`
        );
        console.log(response);
        response.forEach(matrix => {
            let matrixBody = `<h1>${matrix.matrixName}</h1>`;
            matrix.values.forEach(matrixValues => {
                let matrixRow = '';
                matrixValues.forEach(matrixItem => {
                    matrixRow += matrixItem.toString() + ' ';
                });
                matrixBody += `<span>${matrixRow}</span>`;
            });
            $('.matrices').append(`<div class="matrices__wrapper">${matrixBody}</div>`);
        });
    }

    $('#clear-matrices').click(function () {
        $('section.matrices').remove();
    });

    $('#send-matrices').click(function () {
        $.get({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=mtrcombine",
            data: {
                'matrix1': $('#matrix-first').val(),
                'matrix2': $('#matrix-second').val()
            },
            success: function(data) {
                if($('body').has('.matrices')){
                    $('section.matrices').remove();
                }
                renderMatrices(data);
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

    // Table
    getStudentData();

    function popupClose() {
        $('.popup').css('display', 'none');
    }

    function loadStudentTable(response) {
        response = JSON.parse(response);
        response.forEach(responseElem => {
            $('.student__data').append(
                `<div class="student-data__row" data-student-id="${responseElem.ID}">` +
                    `<div class="data-row row--delete"><button class="button record-delete">Удалить</button></div>` +
                    `<div class="data-row row--update"><button class="button record-update">Изменить</button></div>` +
                    `<div class="data-row row--name">${responseElem.name}</div>` +
                    `<div class="data-row row--lname">${responseElem.lastName}</div>` +
                    `<div class="data-row row--mname">${responseElem.middleName}</div>` +
                    `<div class="data-row row--yofb">${responseElem.course}</div>` +
                `</div>` 
            );
        });
    }

    function getStudentData() {
        clearStudentTable();
        let studentsData;
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
                loadStudentTable(data.responseText);
            }
        });
        
    }

    function clearStudentTable() {
        $('.student-data__row').remove();
    }

    function addStudentRecord() {
        let studentName = $('#student-name').val();
        let studentLName = $('#student-lname').val();
        let studentMName = $('#student-mname').val();
        let studentСourse = $('#student-course').val();
        $.ajax({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=table",
            data: {
                'mode': 'add',
                'fname': studentName,
                'lname': studentLName,
                'mname': studentMName,
                'course': studentСourse
            },
            success: function (data) {
                popupClose();
                getStudentData();
            }
        });

    }

    function updateStudentRecord(studentID) {
        let newStudentData = {};

        //http://localhost:8080/api/?func=table&mode=update&id=1&name=Ivan&lname=Ivanov&mname=Ivanovich&yob=2000
        newStudentData['mode'] = 'update';
        newStudentData['id'] = studentID;

        if($('#student-name').val() != ''){
            newStudentData['fname'] = $('#student-name').val();
        }
        if($('#student-lname').val() != ''){
            newStudentData['lname'] = $('#student-lname').val();
        }
        if($('#student-mname').val() != ''){
            newStudentData['mname'] = $('#student-mname').val();
        }
        if($('#student-course').val() != ''){
            newStudentData['course'] = $('#student-course').val();
        }

        $.ajax({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=table",
            data: newStudentData,
            success: function (data) {
                console.log(data);
                popupClose();
                getStudentData();
            },
        });

    }

    function deleteStudentRecord(studentID) {
        $.ajax({
            headers: {
                Accept: 'application/json'
            },
            type: 'GET',
            url: "/api/?func=table",
            data: {
                'mode': 'delete',
                'id': studentID
            },
            success: function () {
                getStudentData();
            },
        });

    }

    // Popups
    $('#popup-func').click(function () {
        let funcType = $('.popup__methods').data('popup-func-type');
        let studentID = $('.popup__methods').data('pupop-student-id');
        if(funcType == 'add'){
            addStudentRecord();
        }
        else if (funcType == 'update'){
            updateStudentRecord(studentID);
        }
    });
    
    $('#add-student').click(function () {
        $('.popup-func__name').text('Добавить студента');
        $('#popup-func').text('Добавить');
        $('.popup__methods').data('popup-func-type', 'add');
        $('.popup').css('display', 'block');
    });

    $('.student__data').on('click', '.record-update', function () {
        $('.popup-func__name').text('Обновить данные студента');
        $('#popup-func').text('Обновить');
        $('.popup__methods').data('popup-func-type', 'update');
        $('.popup').css('display', 'block');
        $('.popup__methods').data('pupop-student-id', $(this).parents('.student-data__row').data('student-id'));
    });

    $('.student__data').on('click', '.record-delete', function () {
        let studentID = $(this).parents('.student-data__row').data('student-id');
        deleteStudentRecord(studentID);
    });

    $('#popup-close').click(popupClose);
});