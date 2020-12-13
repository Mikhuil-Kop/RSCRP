<!DOCTYPE html>
<html lang="ru">
    ${head}
    <body>
        ${header}
        <section class="robot-table">
            <div class="robot-table__wrappper">
                <div class="robot__add">
                    <button class="button" id="add-robot">Добавить</button>
                </div>
                <div class="robot-table--data">
                    <div class="robot__columns">
                        <div class="column column--delete">Удаление</div>
                        <div class="column column--update">Изменение</div>
                        <div class="column column--name">Название</div>
                        <div class="column column--lname">Описание</div>
                        <div class="column column--mname">Способность 1</div>
                        <div class="column column--yofb">Способность 2</div>
                    </div>
                    <div class="robot__data">
                        <#list robots as robot>
                            <div class="robot-data__row" data-robot-id="${robot.id}">
                                <div class="data-row row--delete"><button class="button record-delete">Удалить</button></div>
                                <div class="data-row row--update"><button class="button record-update">Изменить</button></div>
                                <div class="data-row row--name">${robot.name}</div>
                                <div class="data-row row--lname">${robot.description}</div>
                                <div class="data-row row--mname">${robot.skillName1}</div>
                                <div class="data-row row--yofb">${robot.skillName2}</div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </section>
    </body>
    <div class="popup" style="display: none;">
        <div class="popup__wrapper">
            <h1 class="popup-func__name"></h1>
            <div class="popup__methods" data-popup-func-type="" data-pupop-robot-id="">
                <label for="robot-name">Введите название</label>
                <input type="text" id="robot-name" value="Танкобот">
                <label for="robot-description">Введите описание</label>
                <input type="text" id="robot-description" value="Защитник">
                <label for="robot-name">Введите способность 1</label>
                <input type="text" id="robot-skill1" value="Стрелять">
                <label for="robot-name">Введите способность 2</label>
                <input type="text" id="robot-skill2" value="Защищать">
                <div class="popup__buttons">
                    <button class="button" id="popup-func">Добавить</button>
                    <button class="button" id="popup-close">Отмена</button>
                </div>
            </div>
        </div>
    </div>
</html>