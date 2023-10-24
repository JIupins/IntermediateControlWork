package ToysShop;

import java.util.ArrayList;
import java.util.Scanner;

public class Toy implements Comparable<Toy> {
    private int id;
    private static ArrayList<Integer> existID = new ArrayList<>();
    private String name;
    private int totalToysNum;
    private int warehouseToysNum;
    private int rafflingToysNum;
    private int dropChance;

    public Toy(String name, int num) {
        id = setID(existID);
        this.name = name;
        this.totalToysNum = num;
        this.warehouseToysNum = totalToysNum;
        this.rafflingToysNum = 0;
        this.dropChance = 0;
    }

    public Toy(String name, int num, int chance) {
        id = setID(existID);
        this.name = name;
        this.totalToysNum = num;
        this.warehouseToysNum = totalToysNum;
        this.rafflingToysNum = 0;
        this.dropChance = chance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalNumToys() {
        return totalToysNum;
    }

    public int getNumWarehouseToys() {
        return warehouseToysNum;
    }

    public int getNumLotteryToys() {
        return rafflingToysNum;
    }

    public boolean setNumLotteryToys(int num) {
        if (warehouseToysNum >= num) {
            rafflingToysNum += num;
            warehouseToysNum = totalToysNum - rafflingToysNum;
            return true;
        } else {
            return false;
        }
    }

    public void reduseTotalNumToys(int num) {
        rafflingToysNum -= num;
        totalToysNum -= num;
        warehouseToysNum = totalToysNum - rafflingToysNum;
    }

    public int deleteToy(Scanner iScan, int num) {
        int flag;
        if (num > totalToysNum) {
            System.out.println("Введеное колличество игрушек, превышает максимально существующее!");
            flag = 0;
        } else {
            if (num == totalToysNum && num == warehouseToysNum) {
                System.out.println("Все игрушки удалены");
                warehouseToysNum -= num;
                totalToysNum -= num;
                if (existID.contains(this.id)) {
                    existID.remove(existID.indexOf(this.id));
                }
                flag = 1;
            } else if (num == warehouseToysNum) {
                System.out.println("Игрушки хранящиеся на складе удалены.");
                warehouseToysNum -= num;
                totalToysNum -= num;
                flag = 2;
            } else if (num < warehouseToysNum) {
                warehouseToysNum -= num;
                totalToysNum -= num;
                System.out.println("Часть игрушек хранящичся на складе удалена.");
                flag = 2;
            } else {
                boolean flag2 = true;
                String value;
                flag = 2;
                while (flag2) {
                    System.out.println("Часть игрушек участвует в розыгрыше. Вы хотите удалить и их?" +
                            "\n1 - Да" +
                            "\n2 - Нет");
                    System.out.print("Введите символ требуемой операции: ");
                    if (iScan == iScan.useDelimiter("1")) {
                        value = "1";
                    } else {
                        value = iScan.next();
                    }

                    switch (value) {
                        case "1":
                            rafflingToysNum -= (num - warehouseToysNum);
                            warehouseToysNum = 0;
                            totalToysNum = rafflingToysNum;
                            if (totalToysNum == 0) {
                                existID.remove(existID.indexOf(this.id));
                                flag = 1;
                            } else {
                                flag = 2;
                            }
                            flag2 = false;
                            break;
                        case "2":
                            flag2 = false;
                            flag = 2;
                            break;
                        default:
                            flag = 2;
                            flag2 = true;
                            System.out.println("Cимвол операции введен неверно. Повторите ввод.");
                    }
                }
            }
        }
        return flag;
    }

    public int getDropChance() {
        return dropChance;
    }

    public void setDropChance(int chance) {
        this.dropChance = chance;
    }

    @Override
    public String toString() {
        return "Toy: " +
                "ID = " + id +
                ", Name = " + name +
                ", Total toys number = " + totalToysNum +
                ", Warehouse toys number = " + warehouseToysNum +
                ", Raffling toys number = " + rafflingToysNum +
                ", Drop chance = " + dropChance;
    }

    public int setID(ArrayList<Integer> existID) {
        int newID = 0;
        while (existID.contains(newID)) {
            newID++;
        }
        existID.add(newID);
        return newID;
    }

    @Override
    public int compareTo(Toy o) {
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}