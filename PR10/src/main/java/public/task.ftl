<!DOCTYPE html>
<html lang="ru">
    ${head}
    <body>
        ${header}
        <section class="controls">
            <div class="controls__wrapper">
                <div class="calc-field">
                    <label for="calc-a">A:</label>
                    <input id="calc-a" type="text" value="1">
                </div>
                <div class="calc-field">
                    <label for="calc-sign">Знак:</label>
                    <input id="calc-sign" type="text" value="+">
                </div>
                <div class="calc-field">
                    <label for="calc-b">Б:</label>
                    <input id="calc-b" type="text" value="2">
                </div>
                <div class="calc-button">
                    <button class="button" id="send-calc">Рассчитать</button>
                </div>
                
                <div id="calc-answ">
                    Ответ будет тут.
                </div>
            </div>
        </section>
    </body>
</html>