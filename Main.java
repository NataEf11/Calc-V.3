import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите выражение (например: X * X):");
        String input = in.nextLine();
        try {
            String result = calc(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] list = input.split(" ");
        if (list.length != 3) {
            throw new Exception("Неверный формат ввода");
        }

        String num1 = list[0];
        String oper = list[1];
        String num2 = list[2];


        String regexNumber = "^[1-9]$|10";
        String regexRim = "^(X|IX|IV|V?I{0,3})$";

        int number1;
        int number2;
        boolean isRoman = false;

        if (num1.matches(regexNumber) && num2.matches(regexNumber)) {
            number1 = Integer.parseInt(num1);
            number2 = Integer.parseInt(num2);
            if (number1 < 1 || number2 < 1 || number1 > 10 || number2 > 10) {
                throw new Exception("Арабские числа должны быть в диапазоне от 1 до 10 включительно");
            }
        } else if (num1.matches(regexRim) && num2.matches(regexRim)) {
            number1 = romanToArabic(num1);
            number2 = romanToArabic(num2);
            isRoman = true;
            if (number1 < 1 || number2 < 1 || number1 > 10 || number2 > 10) {
                throw new Exception("Римские числа должны быть в диапазоне от I до X включительно");
            }
        } else {
            throw new Exception("Одна из переменных не удовлетворяет условию");
        }

        int result = operation(number1, number2, oper);

        if (isRoman) {
            if (result < 1) {
                throw new Exception("Результат операции с римскими числами не может быть меньше 1");
            }
            if (result > 100) {
                throw new Exception("Результат операции с римскими числами не может превышать 100");
            }
            return arabicToRoman(result);
        } else {
            if (result < -100 || result > 100) {
                throw new Exception("диапазон тут...");
            }
            return Integer.toString(result);
        }
    }

    public static int operation(int num1, int num2, String oper) throws Exception {
        switch (oper) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new Exception("На ноль делить нельзя");
                }
                return num1 / num2;
            default:
                throw new Exception("Неверный оператор");
        }
    }

    public static int romanToArabic(String roman) {
        int result = 0;
        roman = roman.toUpperCase();
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = romanCharToInt(roman.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        return result;
    }

    public static int romanCharToInt(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            default:
                return 0;
        }
    }

    public static String arabicToRoman(int num) throws Exception {
        if (num < 1 || num > 100) {
            throw new Exception("что то придумаешь");
        }
        StringBuilder result = new StringBuilder();
        while (num >= 100) {
            result.append("C");
            num -= 100;
        }
        while (num >= 90) {
            result.append("XC");
            num -= 90;
        }
        while (num >= 50) {
            result.append("L");
            num -= 50;
        }
        while (num >= 40) {
            result.append("XL");
            num -= 40;
        }
        while (num >= 10) {
            result.append("X");
            num -= 10;
        }
        while (num >= 9) {
            result.append("IX");
            num -= 9;
        }
        while (num >= 5) {
            result.append("V");
            num -= 5;
        }
        while (num >= 4) {
            result.append("IV");
            num -= 4;
        }
        while (num >= 1) {
            result.append("I");
            num -= 1;
        }

        return result.toString();
    }
}