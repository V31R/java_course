# java_course

Для обеспечения работы программы было выделено 3 основных класса:
1. TerminalReader - считывает команды из консоли и подготоваливает их для дальнейшей обработки. Таким образом следующие классы отделены от способа ввода данных.  Применён паттерн Одиночка.
2. TaskList - содержит в себе сами задачи, получает на вход уже готовые команды, которые обрабатывает.
3. Controller - класс, который координирует работу TerminalReader и TaskList, по совместительсву является основным циклом программы. Применён паттерн Одиночка.

Для удобства изменения/добалвения/удаления новых команд было создано перечисление CommandType.


#Описание команд#

##add
Формат команды: add <описание задачи>

Описание задачи может содержать любые символы, кроме перевода строки. Перевод строки (нажатие клавиши Enter) означает завершение ввода описания задачи

##print
Формат команды: print [all]

Выводит на печать список задач. По-умолчанию, выводятся только невыполненные задачи, в случае если команда выполняется с аргументом all - печатаются все задачи. Печатаются следующие поля: идентификатор, который используется в команде toggle, статус задачи (x - выполнена, " " - не выполнена), описание задачи.

Пример вывода:

	1. [x] Реализовать сборку на maven
	2. [ ] Реализовать сборку на gradle

##search
Формат команды: search <substring>

Выводит на печать список задач, описание которых содержит substring. Вывод на печать в формате аналогичном команде print.

Пример вывода:

	3. [x] Реализовать сборку на maven
	8. [ ] Реализовать сборку на gradle


##toggle
Формат команды: toggle <идентификатор задачи>

Переключает состояние задачи (с "выполнена" на "не выполнена" и наоборот) по идентификатору. 



##delete
Формат команды: delete <идентификатор задачи>

Удаляет задачу из списка задач.



##edit
Формат команды: edit <идентификатор задачи> <новое значение>

Меняет описание задачи. 



##quit
Формат команды: quit

Завершает работу программы