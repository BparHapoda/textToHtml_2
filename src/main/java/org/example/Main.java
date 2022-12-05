package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Сложная (23 балла)
 * <p>
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 * <p>
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 * <p>
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 * <p>
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 * <p>
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 * <p>
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 * <p>
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * <p>
 * Пример входного файла:
 * ///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
 * 1. Мясо
 * Или колбаса
 * 2. Майонез
 * 3. Картофель
 * 4. Что-то там ещё
 * Помидоры
 * Фрукты
 * 1. Бананы
 * 2. Яблоки
 * 1. Красные
 * 2. Зелёные
 * ///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * <p>
 * <p>
 * Соответствующий выходной файл:
 * ///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * <html>
 * <body>
 * <p>
 * <ul>
 * <li>
 * Утка по-пекински
 * <ul>
 * <li>Утка</li>
 * <li>Соус</li>
 * </ul>
 * </li>
 * <li>
 * Салат Оливье
 * <ol>
 * <li>Мясо
 * <ul>
 * <li>Или колбаса</li>
 * </ul>
 * </li>
 * <li>Майонез</li>
 * <li>Картофель</li>
 * <li>Что-то там ещё</li>
 * </ol>
 * </li>
 * <li>Помидоры</li>
 * <li>Фрукты
 * <ol>
 * <li>Бананы</li>
 * <li>Яблоки
 * <ol>
 * <li>Красные</li>
 * <li>Зелёные</li>
 * </ol>
 * </li>
 * </ol>
 * </li>
 * </ul>
 * </p>
 * </body>
 * </html>
 * ///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 **/
public class Main {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings = readFile();
        analyser(strings);

    }

    public static ArrayList<String> readFile() {

        String fileName = "markdown_simple.md";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                fileWriter.write("" +

                        "* Утка по-пекински\n" +
                        "    * Утка\n" +
                        "    * Соус\n" +
                        "* Салат Оливье\n" +
                        "    1. Мясо\n" +
                        "        * Или колбаса\n" +
                        "    2. Майонез\n" +
                        "    3. Картофель\n" +
                        "    4. Что-то там ещё\n" +
                        "* Помидоры\n" +
                        "* Фрукты\n" +
                        "    1. Бананы\n" +
                        "    23. Яблоки\n" +
                        "        1. Красные\n" +
                        "        2. Зелёные");


                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        ArrayList<String> listStrings = new ArrayList<>();
        String string;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            string = bufferedReader.readLine();

            while (string != null) {
                listStrings.add(string);
                string = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listStrings;
    }

    public static void analyser(List<String> textLines) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        String patternUl = new String("^(\\s*)[*]{1}.+");
        String patternNl = new String("^(\\s*)(\\d+)[.]{1}.+");


        Stack stack = new Stack();

        for (String line : textLines) {
            if (line.length() == 0) {
                break;
            }
            if (line.matches(patternUl)) {

                lexemes.add(new Lexeme(LexemeType.UNNUMBERED, getTextFromLine(line), getSpacesFromLine(line)));
            } else if (line.matches(patternNl)) {

                lexemes.add(new Lexeme(LexemeType.NUMBERED, getTextFromLine(line), getSpacesFromLine(line), getNumberFromLine(line)));
            }
        }


        System.out.println(convertToHtmlLexemes(lexemes));


        saveFile(lexemes);


    }

    public static int getSpacesFromLine(String string) {
        char[] array = string.toCharArray();
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != ' ') {
                count = i;
                break;
            }
        }
        return count;
    }

    public static int getNumberFromLine(String string) {
        char[] array = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= '0' & array[i] <= '9') {
                stringBuilder.append(array[i]);
            } else {
                if (array[i] == '.') {
                    break;
                }
            }
        }
        int result = Integer.parseInt(stringBuilder.toString());
        return result;
    }

    public static String getTextFromLine(String string) {
        char[] array = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            boolean flag = false;
            if (array[i] == '.' || array[i] == '*') {
                for (int j = i + 1; j < array.length; j++) {
                    stringBuilder.append(array[j]);
                    flag = true;
                }
            }
            if (flag) {
                break;
            }
        }
        return stringBuilder.toString();
    }


    public static void saveFile(ArrayList<Lexeme> lexemes) {

        File output = new File("output.html");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(output);

            for (Lexeme lexeme : lexemes) {
                if (lexeme.getText() == null) {
                    fileWriter.write(lexeme.getTag().name + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static String convertToHtmlLexemes(ArrayList<Lexeme> lexemes) {

        StringBuilder result = new StringBuilder();
        result.append(Tag.DOC_START.name);
        result.append(Tag.PARAGRAPH_START.name);
        Stack stack = new Stack();
        stack.push(lexemes.get(0));
        result.append(addStartList(lexemes.get(0)));
        result.append(addLi(lexemes.get(0)));
        for (int i = 1; i < lexemes.size(); i++
        ) {


            if (lexemes.get(i).getSpaces() > lexemes.get(i - 1).getSpaces()) {
                stack.push(lexemes.get(i));
                result.append(addStartList(lexemes.get(i)));
                result.append(addLi(lexemes.get(i)));
                continue;
            } else if (lexemes.get(i).getSpaces() == lexemes.get(i - 1).getSpaces()) {
                result.append(addLi(lexemes.get(i)));
                continue;
            } else if (lexemes.get(i).getSpaces() < lexemes.get(i - 1).getSpaces()) {
                result.append(addEndList(stack.pop()));
                result.append(addLi(lexemes.get(i)));
            }


        }
        while (stack.getStack().size() != 0) {
            result.append(addEndList(stack.pop()));
        }


        result.append(Tag.PARAGRAPH_END.name);
        result.append(Tag.DOC_END.name);


        return result.toString();
    }


    public static String addStartList(Lexeme lexeme) {
        if (lexeme.getType() == LexemeType.UNNUMBERED) {
            return Tag.UL_START.name;
        } else {
            return Tag.OL_START.name;
        }
    }

    public static String addEndList(Lexeme lexeme) {
        if (lexeme.getType() == LexemeType.UNNUMBERED) {
            return Tag.UL_END.name;
        } else {
            return Tag.OL_END.name;
        }
    }

    public static String addLi(Lexeme lexeme) {
        String text = lexeme.getText();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Tag.LI_START.name);
        stringBuilder.append(text);
        stringBuilder.append(Tag.LI_END.name);
        return stringBuilder.toString();
    }


}

