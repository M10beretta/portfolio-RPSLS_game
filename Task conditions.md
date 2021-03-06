При запуске параметрами командной строки (аргументы метода main или Main) передаётся нечётное число >=3 неповторяющихся строк (при неправильно заданных аргументах вывести аккуратное сообщение об ошибке — что неверно, пример как правильно). Эти строки — это ходы (например, Камень Ножницы Бумага или Камень Ножницы Бумага Ящерица Спок). Победа определяется так — половина следующих выигрывает, половина предыдущих проигрывает (по кругу).
Скрипт генерирует случайный ключ (SecureRandom или RandomNumberGenerator — обязательно!) длиной 128 бит, делает свой ход, вычисляет HMAC (на базе SHA2 или SHA3) от хода со сгенерированным ключом, показывает пользователя HMAC. После этого пользователь получает "меню" 1 - Камень, 2 - Ножницы, ...., 0 - Exit. Пользователь делает свой выбор (при некорректном вводе опять отображается "меню"). Скрипт показывает кто победил, ход компьютера и исходный ключ.
Таким образом, пользователь может проверить, что компьютер играет честно (не поменял свой ход после хода пользователя).
Пример:
>java -jar gamr.jar rock paper scissors lizard Spock
HMAC: FAAC40C71B4B12BF0EF5556EEB7C06925D5AE405D447E006BB8A06565338D411
Available moves:
1 - rock
2 - paper
3 - scissors
4 - lizard
5 - Spock
0 - exit
Enter your move: 2
Your move: paper
Computer move: rock
You win!
HMAC key: BD9B5544739FCE7359C267E734E380A2

Пример. Допустим, есть 7 ходов (A,B,C,D,E,F и G). Представим, что они "зациклены", за A идёт B, за B идёт C, за F идёт G, а за G идёт A. "Половина" в данном случае 7/2 = 3. Три следующих выигрывают, три предыдущих проигрывают.