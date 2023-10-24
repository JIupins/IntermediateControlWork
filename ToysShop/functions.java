package ToysShop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

public class functions {
    public static void main(String[] args) {
        Comparator<Toy> idComparator1 = Comparator.comparingInt(Toy::getId);
        TreeSet<Toy> toysWarehouse = new TreeSet<>(idComparator1);
        TreeSet<Toy> toysRaffling = new TreeSet<>(idComparator1);

        mainMenu(toysWarehouse, toysRaffling);
    }

    static void mainMenu(TreeSet<Toy> toysWarehouse, TreeSet<Toy> toysRaffling) {
        boolean flag1 = true;
        try (Scanner iScan = new Scanner(System.in)) {
            while (flag1) {
                System.out.println("\nВыберите операцию:" +
                        "\n1 - Добавить игрушку на склад" +
                        "\n2 - Посмотреть игрушки на складе" +
                        "\n3 - Удалить игрушку" +
                        "\n4 - Определить игрушки участвующие в розыгрыше" +
                        "\n5 - Провести розыгрыш" +
                        "\n6 - Выйти из программы\n");

                System.out.print("Введите символ требуемой операции: ");
                String value1 = iScan.next();

                switch (value1) {
                    case "1":
                        createToy(iScan, toysWarehouse);
                        break;
                    case "2":
                        showToysList(toysWarehouse);
                        break;
                    case "3":
                        showToysList(toysWarehouse);
                        deleteToy(iScan, toysWarehouse, toysRaffling);
                        break;
                    case "4":
                        showToysList(toysWarehouse);
                        boolean flag2 = true;
                        while (flag2) {
                            System.out.println("\nВыберите операцию:" +
                                    "\n1 - Добавить игрушку в розыгрыш" +
                                    "\n2 - Посмотреть список разыгрываемых игрушек" +
                                    "\n3 - Выйти в предыдущее меню\n");
                            System.out.print("Введите символ требуемой операции: ");
                            String value2 = iScan.next();
                            switch (value2) {
                                case "1":
                                    createToysRafflingList(iScan, toysWarehouse, toysRaffling);
                                    break;
                                case "2":
                                    showToysList(toysRaffling);
                                    break;
                                case "3":
                                    flag2 = false;
                                    break;
                                default:
                                    System.out.println("Cимвол операции введен неверно. Повторите ввод.");
                            }
                        }
                        break;
                    case "5":
                        doRaffling(iScan, toysWarehouse, toysRaffling);
                        break;
                    case "6":
                        flag1 = false;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Cимвол операции введен неверно. Повторите ввод.");
                }
            }
        }
    }

    static void createToy(Scanner iScan, TreeSet<Toy> toysWarehouse) {
        System.out.print("\nВведите название игрушки: ");
        String name = iScan.next();
        System.out.print("Введите колличество игрушек поступивших на склад: ");
        int num = iScan.nextInt();
        Toy newToy = new Toy(name, num);
        toysWarehouse.add(newToy);
    }

    static void showToysList(TreeSet<Toy> toysWarehouse) {
        System.out.println("Игрушек в списке: ");
        for (Toy item : toysWarehouse) {
            System.out.println(item.toString());
        }
    }

    static void deleteToy(Scanner iScan, TreeSet<Toy> toysWarehouse, TreeSet<Toy> toysRaffling) {
        int flag3;
        System.out.print("Введите ID удаляемой игрушки: ");
        String id = iScan.next();

        for (Toy toy : toysWarehouse) {
            if (toy.getId() == Integer.parseInt(id)) {
                System.out.print("Введите колличество игрушек,которые хотите удалить: ");
                int number = iScan.nextInt();
                flag3 = toy.deleteToy(iScan, number);

                if (flag3 == 1) {
                    toysWarehouse.remove(toy);
                    toysRaffling.remove(toy);
                }
                break;
            }
        }
    }

    static void createToysRafflingList(Scanner iScan, TreeSet<Toy> toysWarehouse, TreeSet<Toy> toysRaffling) {
        int count = 0;

        System.out.print("Введите ID игрушки которая будет участвовать в розыгрыше: ");
        String id = iScan.next();

        for (Toy toy : toysWarehouse) {
            if (toy.getId() == Integer.parseInt(id)) {
                System.out.print("Введите колличество игрушек,которые будут участвовать в розыгрыше: ");
                int number = iScan.nextInt();
                if (toy.setNumLotteryToys(number) == false) {
                    count = 1;
                    break;
                }

                System.out.print("Введите шанс выигрыша игрушки, в процентах от 0 до 100%: ");
                int chance = iScan.nextInt();
                toy.setDropChance(chance);

                toysRaffling.add(toy);
                count = 2;
                break;
            }
        }

        if (count == 0)
            System.out.println("Такой игрушки на складе нет!");
        else if (count == 1)
            System.out.println("На складе недостаточно этих игрушек!");
        else
            System.out.println("Игрушка добавлена в список розыгрыша.");
    }

    static void doRaffling(Scanner iScan, TreeSet<Toy> toysWarehouse, TreeSet<Toy> toysRaffling) {
        int flag4;
        String choose ="1";
        Comparator<Toy> idComparator2 = Comparator.comparingInt(Toy::getDropChance).reversed();
        TreeSet<Toy> toysRafflingNew = new TreeSet<>(idComparator2);
        toysRafflingNew.addAll(toysRaffling);

        writerLine(getDate());
        writerLine("Всего игрушек:");
        writerCollection(toysWarehouse);
        writerLine("Всего разыгрываемых игрушек:");
        writerCollection(toysRafflingNew);

        int randomNum = (int) (Math.random() * 100);
        int chance = 0;
        for (Toy toy : toysRafflingNew) {
            chance += toy.getDropChance();
            if (randomNum <= chance) {
                System.out.printf("По здравляем! Вы выиграли игрушку: %s\n!", toy.getName());
                writerLine(String.format("Поздравляем! Вы выиграли игрушку: %s!", toy.getName()));
                flag4 = toy.deleteToy(new Scanner(choose).useDelimiter("1"), 1);

                if (flag4 == 1) {
                    toysWarehouse.remove(toy);
                    toysRaffling.remove(toy);
                }
                break;
            }
            if (toy == toysRafflingNew.last()) {
                System.out.println("К сожалению вы ничего не выиграли!");
                writerLine("К сожалению вы ничего не выиграли!");
            }
        }
    }

    public static void writerCollection(TreeSet<Toy> toys) {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File("report.json");

        try {
            FileWriter writer = new FileWriter(file, true);

            for (Toy toy : toys) {
                String json = mapper.writeValueAsString(toy);
                writer.write(json + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writerLine(String line) {
        ObjectMapper mapper = new ObjectMapper();
    
        File file = new File("report.json");
    
        try {
            FileWriter writer = new FileWriter(file, true);
    
            String json = mapper.writeValueAsString(line);
            writer.write(json + "\n");
    
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }
}