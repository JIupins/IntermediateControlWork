import datetime
import traceback


def create_note(filename):
    print(u"\n----Создание заметки----")
    dictionary_notes = {}
    key = assign_key(filename)
    note_text = input(u"Добавьте заметку: ")

    now = datetime.datetime.now()
    date_time = now.strftime("%d.%m.%Y %H:%M")

    dictionary_notes[key] = note_text + "| " + date_time
    with open(filename, "a") as file:
        for key, value in dictionary_notes.items():
            file.write(f"{key}: {value}\n")


def remove_note(filename):
    print(u"\n----Удаление заметки----")
    dictionary_notes1 = {}

    with open(filename, "r") as file:
        for line in file:
            key, value = line.strip().split(": ")
            dictionary_notes1[int(key)] = value

    if len(dictionary_notes1) == 0:
        print(u"Ни одной заметки пока не создано (файл пустой).\n")
    else:
        print(u"Существуют следующие заметки:")
        for (k, v) in dictionary_notes1.items():
            print(k, v)

        while True:
            try:
                note_num = int(input(u"\nВведите номер заметки которую вы хотите удалить: "))
                if note_num in dictionary_notes1:
                    dictionary_notes1.pop(note_num)

                    with open(filename, "w") as file:
                        for key, value in dictionary_notes1.items():
                            file.write(f"{key}: {value}\n")

                    print(f"Заметка №{note_num} удалена!")
                    break
                else:
                    print(u"Заметки с таким номером не существует! Повторите ввод.\n")
            except Exception as e:
                print(f"Получено исключение:\n{e}\n")
                print(traceback.format_exc())


def edit_note(filename):
    print(u"\n----Редактирование заметки----")
    dictionary_notes1 = {}

    with open(filename, "r") as file:
        for line in file:
            key, value = line.strip().split(": ")
            dictionary_notes1[int(key)] = value

    if len(dictionary_notes1) == 0:
        print(u"Ни одной заметки пока не создано (файл пустой).\n")
    else:
        print(u"Существуют следующие заметки:")
        for (k, v) in dictionary_notes1.items():
            print(k, v)

        while True:
            try:
                note_num = int(input(u"\nВведите номер заметки которую вы хотите отредактировать: "))
                if note_num in dictionary_notes1:
                    new_text = input(u"\nВведите заметку: ")
                    dictionary_notes1[note_num] = new_text

                    with open(filename, "w") as file:
                        for key, value in dictionary_notes1.items():
                            file.write(f"{key}: {value}\n")

                    print(f"Заметка №{note_num} изменена!")
                    break
                else:
                    print(u"Заметки с таким номером не существует! Повторите ввод.\n")
            except Exception as e:
                print(f"Получено исключение:\n{e}\n")


def read_notes(filename):
    print(u"\n----Перечень заметок----")
    dictionary_notes1 = {}

    with open(filename, "r") as file:
        for line in file:
            key, value = line.strip().split(": ")
            dictionary_notes1[int(key)] = value

    if len(dictionary_notes1) == 0:
        print(u"Ни одной заметки пока не создано (файл пустой).\n")
    else:
        print(u"Созданы следующие заметки:")
        for (k, v) in dictionary_notes1.items():
            print(k, v)


def find_note(filename):
    print(u"\n----Найти заметку (по дате)----")
    dictionary_notes1 = {}
    dictionary_notes2 = {}
    dictionary_notes_full = {}

    with open(filename, "r") as file:
        for line in file:
            key, value = line.strip().split(": ")
            dictionary_notes_full[int(key)] = value

            key, value, date = line.split(": ")[0].strip(), line.split("| ")[0].split(": ")[1].strip(), line.split("| ")[1].strip()
            dictionary_notes1[int(key)] = value
            dictionary_notes2[int(key)] = date

    if len(dictionary_notes1) == 0:
        print(u"Ни одной заметки пока не создано (файл пустой).\n")
    else:
        find_date = input(u"Введите дату заметки, в формате 15.08.2023 18:51: ")

        flag = True

        for k, d in dictionary_notes2.items():
            if find_date in d:
                print(f"{k}: {dictionary_notes1[k]}| {dictionary_notes2[k]}")
                flag = False

        if flag:
            print(u"Ни одной заметки c подобной датой не найдено.\n")


def close_program(filename):
    print(u"Программа закрыта.\n")
    return False


def assign_key(filename):
    dictionary_notes2 = {}

    with open(filename, "r") as file:
        for line in file:
            key, value = line.strip().split(": ")
            dictionary_notes2[int(key)] = value

    if len(dictionary_notes2) == 0:
        print(u"Ни одной заметки пока не создано (файл пустой).\n")
        num = 1
    else:
        missing_keys = [key for key in range(1, max(dictionary_notes2) + 1) if key not in dictionary_notes2]

        if missing_keys:
            num = min(missing_keys)
        else:
            num = max(dictionary_notes2) + 1
        return num
    return num
