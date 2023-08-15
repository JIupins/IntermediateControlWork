import os
import traceback

from functions import *

filename = 'notes.txt'

if os.path.exists(filename):
    print(u"Файл с заметами notes.txt уже существует! \n")
else:
    data = open(filename, 'a', encoding='utf-8')
    data.close()
    print(u"Создан файл с заметами: notes.txt \n")

flag = True

while flag:

    try:
        print(u"\nСписок доступных команд:\n\t"
              "1 - Создать заметку\n\t"
              "2 - Удалить заметку\n\t"
              "3 - Редактировать заметку\n\t"
              "4 - Ознакамиться с заметками\n\t"
              "5 - Выход из программы\n")
        choice = int(input(u"Введите число от 1 до 5: "))
        if 0 < choice < 6:
            if choice == 1:
                create_note(filename)
            elif choice == 2:
                remove_note(filename)
            elif choice == 3:
                edit_note(filename)
            elif choice == 4:
                read_notes(filename)
            elif choice == 5:
                flag = close_program(filename)
        else:
            print(u"Значение введено неверно! Повторите вводю\n")
    except Exception as e:
        print(f"Введеное значение не является числом! Повторите ввод.\n{e}\n")