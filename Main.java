import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.*;

public class Main {
    private static final int maxZapysiv = 50;

    private static LocalDateTime[] daty = new LocalDateTime[maxZapysiv];
    private static String[] teksty = new String[maxZapysiv];
    private static int kilkistZapysiv = 0;
    private static String formatDaty = "yyyy-MM-dd HH:mm:ss";
    private static boolean zminyZberezheni = true;

    public static void main(String[] args) {
        Scanner skan = new Scanner(System.in);
        boolean pratsyuye = true;

        System.out.println("( ͡°͜ʖ͡°) Мій щоденник запущено ( ͡°͜ʖ͡°)");
        
        System.out.println("\nВи хочете відновити існуючий щоденник?");
        System.out.println("1. Так, відновити з файлу");
        System.out.println("2. Ні, створити новий");
        
        int vybir = 0;
        while (vybir < 1 || vybir > 2) {
            try {
                System.out.print("Ваш вибір: ");
                vybir = Integer.parseInt(skan.nextLine());
                if (vybir < 1 || vybir > 2) {
                    System.out.println("Введіть 1 або 2!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне число!");
            }
        }
        
        if (vybir == 1) {
            System.out.print("Введіть шлях до файлу: ");
            String shlyakhDoFaylu = skan.nextLine();
            vidnovytyZFaylu(shlyakhDoFaylu);
        }
        
        vybratyFormatDaty(skan);

        while (pratsyuye) {
            pokazatyHolovneMenu();
            
            int vybirMenu = 0;
            
            try {
                System.out.print("Ваш вибір: ");
                String vybirStr = skan.nextLine();
                vybirMenu = Integer.parseInt(vybirStr);
            } catch (NumberFormatException e) {
                System.out.println("\nНевірний вибір (╬▔皿▔)╯! Спробуйте ще раз, але вже число.");
                continue;
            }

            switch (vybirMenu) {
                case 1:
                    dodatyZapys(skan);
                    break;

                case 2:
                    vydalytyZapys(skan);
                    break;

                case 3:
                    pokazatyVsiZapysy();
                    break;
                    
                case 4:
                    zberehtyShchodennyk(skan);
                    break;

                case 5:
                    if (!zminyZberezheni) {
                        System.out.println("\nУ вас є незбережені зміни. Зберегти зміни перед виходом?");
                        System.out.println("1. Так");
                        System.out.println("2. Ні");
                        
                        int vybirZberezhennya = 0;
                        while (vybirZberezhennya < 1 || vybirZberezhennya > 2) {
                            try {
                                System.out.print("Ваш вибір: ");
                                vybirZberezhennya = Integer.parseInt(skan.nextLine());
                                if (vybirZberezhennya < 1 || vybirZberezhennya > 2) {
                                    System.out.println("Введіть 1 або 2!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Введіть коректне число!");
                            }
                        }
                        
                        if (vybirZberezhennya == 1) {
                            zberehtyShchodennyk(skan);
                        }
                    }
                    
                    System.out.println("\n✓ До побачення! Щоденник закрито.");
                    pratsyuye = false;
                    break;

                default:
                    System.out.println("\nНевірний вибір (╬▔皿▔)╯! Спробуйте ще раз.");
            }
        }
        skan.close();
    }

    private static void pokazatyHolovneMenu() {
        System.out.println("\n┌───────────────────────────────────────┐");
        System.out.println("│ МІЙ ЩОДЕННИК   つ◕_◕ ༽つ               │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│ 1. Додати запис                       │");
        System.out.println("│ 2. Видалити запис                     │");
        System.out.println("│ 3. Переглянути всі записи             │");
        System.out.println("│ 4. Зберегти щоденник у файл           │");
        System.out.println("│ 5. Вийти з програми                   │");
        System.out.println("└───────────────────────────────────────┘");
    }

    private static void dodatyZapys(Scanner skan) {
        if (kilkistZapysiv >= maxZapysiv) {
            System.out.println("\nЩоденник повний (╬▔皿▔)╯! Максимальна кількість записів: " + maxZapysiv);
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ДОДАВАННЯ НОВОГО ЗАПИСУ              │");
        System.out.println("└────────────────────────────────────┘");

        LocalDateTime data = null;

        while (data == null) {
            try {
                System.out.println("Введіть дату для запису (рік, місяць, день):");
                System.out.print("Рік: ");
                int rik = Integer.parseInt(skan.nextLine());
                System.out.print("Місяць: ");
                int misyats = Integer.parseInt(skan.nextLine());
                System.out.print("День: ");
                int den = Integer.parseInt(skan.nextLine());
                
                System.out.println("Додати час? (1 - так, 2 - ні)");
                int dodatyChas = 0;
                while (dodatyChas < 1 || dodatyChas > 2) {
                    try {
                        System.out.print("Ваш вибір: ");
                        dodatyChas = Integer.parseInt(skan.nextLine());
                        if (dodatyChas < 1 || dodatyChas > 2) {
                            System.out.println("Введіть 1 або 2!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть коректне число!");
                    }
                }
                
                if (dodatyChas == 1) {
                    System.out.print("Година: ");
                    int hodyna = Integer.parseInt(skan.nextLine());
                    System.out.print("Хвилина: ");
                    int khvylyna = Integer.parseInt(skan.nextLine());
                    data = LocalDateTime.of(rik, misyats, den, hodyna, khvylyna);
                } else {
                    data = LocalDateTime.of(rik, misyats, den, 0, 0);
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка введення! Введіть ціле число.");
            } catch (DateTimeException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        for (int i = 0; i < kilkistZapysiv; i++) {
            if (daty[i] != null) {
                if (daty[i].equals(data)) {
                    System.out.println("Запис з такою датою вже існує (╬▔皿▔)╯! Спробуйте іншу дату.");
                    return;
                }
            }
        }

        System.out.println("\nВведіть текст запису. Для завершення введіть '~'.");
        System.out.println("Можна вводити декілька рядків, кожен з нових рядків.");

        String tekst = "";
        String ryadok;

        while (true) {
            ryadok = skan.nextLine();
            if (ryadok.equals("~")) {
                break;
            }

            if (!tekst.isEmpty()) {
                tekst = tekst + "\n" + ryadok;
            } else {
                tekst = ryadok;
            }
        }

        if (tekst.isEmpty()) {
            System.out.println("Запис не може бути порожнім (╬▔皿▔)╯!");
            return;
        }

        daty[kilkistZapysiv] = data;
        teksty[kilkistZapysiv] = tekst;
        kilkistZapysiv++;
        zminyZberezheni = false;

        System.out.println("\n✓ Запис успішно додано до щоденника!");
    }

    private static void vydalytyZapys(Scanner skan) {
        if (kilkistZapysiv == 0) {
            System.out.println("\nЩоденник порожній (╬▔皿▔)╯!");
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ВИДАЛЕННЯ ЗАПИСУ                     │");
        System.out.println("└────────────────────────────────────┘");

        LocalDateTime data = null;

        while (data == null) {
            try {
                System.out.println("Введіть дату запису для видалення (рік, місяць, день):");
                System.out.print("Рік: ");
                int rik = Integer.parseInt(skan.nextLine());
                System.out.print("Місяць: ");
                int misyats = Integer.parseInt(skan.nextLine());
                System.out.print("День: ");
                int den = Integer.parseInt(skan.nextLine());

                data = LocalDateTime.of(rik, misyats, den, 0, 0);
            } catch (NumberFormatException e) {
                System.out.println("Помилка введення! Введіть ціле число.");
            } catch (DateTimeException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        int indexZapysu = -1;

        for (int i = 0; i < kilkistZapysiv; i++) {
            if (daty[i] != null) {
                if (daty[i].toLocalDate().equals(data.toLocalDate())) {
                    indexZapysu = i;
                    break;
                }
            }
        }

        if (indexZapysu == -1) {
            System.out.println("Запис з такою датою не знайдено (╬▔皿▔)╯!");
            return;
        }

        for (int i = indexZapysu; i < kilkistZapysiv - 1; i++) {
            daty[i] = daty[i + 1];
            teksty[i] = teksty[i + 1];
        }

        daty[kilkistZapysiv - 1] = null;
        teksty[kilkistZapysiv - 1] = null;
        kilkistZapysiv--;
        zminyZberezheni = false;

        System.out.println("\n✓ Запис успішно видалено!");
    }

    private static void pokazatyVsiZapysy() {
        if (kilkistZapysiv == 0) {
            System.out.println("\nЩоденник порожній (╬▔皿▔)╯!");
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ПЕРЕЛІК ВСІХ ЗАПИСІВ                 │");
        System.out.println("└────────────────────────────────────┘");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDaty);

        for (int i = 0; i < kilkistZapysiv; i++) {
            String dataSformatovana = daty[i].format(formatter);
            System.out.println("\n[Запис #" + (i + 1) + ", Дата: " + dataSformatovana + "]");
            System.out.println("────────────────────────────────────");
            System.out.println(teksty[i]);
            System.out.println("────────────────────────────────────");
        }

        System.out.println("\n✓ Загальна кількість записів: " + kilkistZapysiv);
    }
    
    private static void zberehtyShchodennyk(Scanner skan) {
        if (kilkistZapysiv == 0) {
            System.out.println("\nЩоденник порожній (╬▔皿▔)╯! Немає що зберігати.");
            return;
        }
        
        System.out.print("\nВведіть шлях до файлу для збереження: ");
        String shlyakhDoFaylu = skan.nextLine();
        
        try {
            FileWriter fileWriter = new FileWriter(shlyakhDoFaylu);
            
            fileWriter.write(kilkistZapysiv + "\n");
            fileWriter.write(formatDaty + "\n");
            
            for (int i = 0; i < kilkistZapysiv; i++) {
                fileWriter.write(daty[i].toString() + "\n");
                
                String tekstZaminenyi = "";
                for (int j = 0; j < teksty[i].length(); j++) {
                    char symvol = teksty[i].charAt(j);
                    if (symvol == '\n') {
                        tekstZaminenyi = tekstZaminenyi + "\\n";
                    } else {
                        tekstZaminenyi = tekstZaminenyi + symvol;
                    }
                }
                fileWriter.write(tekstZaminenyi + "\n");
            }
            
            fileWriter.close();
            zminyZberezheni = true;
            System.out.println("\n✓ Щоденник успішно збережено у файл!");
        } catch (IOException e) {
            System.out.println("\nПомилка збереження файлу: " + e.getMessage());
        }
    }
    
    private static void vidnovytyZFaylu(String shlyakhDoFaylu) {
        try {
            File file = new File(shlyakhDoFaylu);
            
            if (!file.exists()) {
                System.out.println("\nФайл не знайдено (╬▔皿▔)╯!");
                return;
            }
            
            FileReader fileReader = new FileReader(file);
            Scanner fileSkan = new Scanner(fileReader);
            
            if (!fileSkan.hasNextLine()) {
                System.out.println("\nФайл порожній (╬▔皿▔)╯!");
                fileSkan.close();
                return;
            }
            
            int kilkist = Integer.parseInt(fileSkan.nextLine());
            formatDaty = fileSkan.nextLine();
            
            daty = new LocalDateTime[maxZapysiv];
            teksty = new String[maxZapysiv];
            kilkistZapysiv = 0;
            
            for (int i = 0; i < kilkist; i++) {
                if (!fileSkan.hasNextLine()) break;
                
                String dataStr = fileSkan.nextLine();
                LocalDateTime data = LocalDateTime.parse(dataStr);
                
                if (!fileSkan.hasNextLine()) break;
                
                String tekst = fileSkan.nextLine();
                String tekstVidnovlenyi = "";
                
                for (int j = 0; j < tekst.length(); j++) {
                    if (j < tekst.length() - 1 && tekst.charAt(j) == '\\' && tekst.charAt(j + 1) == 'n') {
                        tekstVidnovlenyi = tekstVidnovlenyi + "\n";
                        j++;
                    } else {
                        tekstVidnovlenyi = tekstVidnovlenyi + tekst.charAt(j);
                    }
                }
                
                daty[kilkistZapysiv] = data;
                teksty[kilkistZapysiv] = tekstVidnovlenyi;
                kilkistZapysiv++;
            }
            
            fileSkan.close();
            zminyZberezheni = true;
            System.out.println("\n✓ Щоденник успішно відновлено з файлу!");
        } catch (IOException e) {
            System.out.println("\nПомилка читання файлу: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\nПомилка формату файлу: неправильний формат числа");
        } catch (DateTimeException e) {
            System.out.println("\nПомилка формату дати у файлі");
        } catch (Exception e) {
            System.out.println("\nНеочікувана помилка: " + e.getMessage());
        }
    }
    
    private static void vybratyFormatDaty(Scanner skan) {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ВИБІР ФОРМАТУ ДАТИ                   │");
        System.out.println("└────────────────────────────────────┘");
        
        System.out.println("Виберіть формат відображення дати:");
        System.out.println("1. ISO 8601 (yyyy-MM-dd HH:mm:ss)");
        System.out.println("2. Східноєвропейський (dd.MM.yyyy HH:mm)");
        System.out.println("3. Формат США (MM/dd/yyyy h:mm a)");
        System.out.println("4. Формат Великобританії (dd MMMM yyyy HH:mm)");
        System.out.println("5. Власний формат");
        
        int vybir = 0;
        while (vybir < 1 || vybir > 5) {
            try {
                System.out.print("Ваш вибір: ");
                vybir = Integer.parseInt(skan.nextLine());
                if (vybir < 1 || vybir > 5) {
                    System.out.println("Введіть число від 1 до 5!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне число!");
            }
        }
        
        switch (vybir) {
            case 1:
                formatDaty = "yyyy-MM-dd HH:mm:ss";
                break;
            case 2:
                formatDaty = "dd.MM.yyyy HH:mm";
                break;
            case 3:
                formatDaty = "MM/dd/yyyy h:mm a";
                break;
            case 4:
                formatDaty = "dd MMMM yyyy HH:mm";
                break;
            case 5:
                System.out.println("\nВведіть власний формат дати (використовуйте символи y, M, d, H, m, s):");
                System.out.println("Наприклад: dd-MM-yyyy HH:mm:ss");
                
                boolean formatPravylnyi = false;
                
                while (!formatPravylnyi) {
                    try {
                        String formatKorystuvacha = skan.nextLine();
                        DateTimeFormatter.ofPattern(formatKorystuvacha);
                        formatDaty = formatKorystuvacha;
                        formatPravylnyi = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Неправильний формат! Спробуйте ще раз.");
                    }
                }
                break;
        }
        
        System.out.println("\n✓ Формат дати встановлено!");
    }
}