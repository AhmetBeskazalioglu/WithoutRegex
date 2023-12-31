import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;

public class WithoutRegex {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the pattern: ");
        String pattern = scanner.nextLine();

        System.out.print("How many words return to console: "); // For Example ab(a+b*)*+a*b
        int returnWords = scanner.nextInt();
        scanner.nextLine();

        System.out.println("patternWithoutRegex(pattern,returnWords) = " + patternWithoutRegex(pattern, returnWords));

        System.out.print("Can this word be made from the pattern: ");
        String canWordBe = scanner.nextLine();

        canWordBe(canWordBe, pattern);

    }

    public static ArrayList<String> patternWithoutRegex(String pattern, int returnWords) {
        ArrayList<String> arrayList = new ArrayList<>();
        while (returnWords > 0) {
            arrayList.add(braceAndPlusProcess(pattern));
            returnWords--;
        }
        return arrayList;
    }

    public static String braceAndPlusProcess(String pattern) {
        LinkedHashSet<String> braceLinkedHashSet = new LinkedHashSet<>();
        if (pattern.contains("(")) {
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) == '(') {         //  Önce parantez olan bölümler bukunup arrayliste atılıyor.
                    braceLinkedHashSet.add(brace(pattern, i));
                }
            }
            for (int i = 0; i < braceLinkedHashSet.size(); i++) {            // Daha sonra patterndeki bütün parantaz içleri parantezlerle birlikte i değerine dönüştürülür ki + için işlem yapmamız kolaylaşsın.
                pattern = pattern.replace(pattern.substring(pattern.indexOf('('), pattern.indexOf(')') + 1), String.valueOf(i)); //Bu işlemden sonra patterni + lardan elementlere ayıracağız
            }
            // Buraya kadar yapılan işlem sonucu:
            // INPUT:   ab*(a+b)*(a+b+ab)+(a+b)
            // OUTPUT:  ab*  0  *   1    +  0
        }
        if (pattern.contains("+"))
            pattern = plus(pattern);        //      Patterndeki + işaretlerinde ayrı ayrı elementler oluşturuldu ve rastgele bir element seçildi.
        // OUTPUT: Ya  ab*0*1   ya da   0 (Burada 2 adet 0 çıkması 2 elementinde aynı string ifade olması (a+b))

        pattern = cleanPattern(pattern);
        // OUTPUT: abb0*1
        ArrayList<String> braceList = new ArrayList<>(braceLinkedHashSet);

        pattern = takeOutTheBraces(pattern, braceList);

        return pattern;
    }

    public static boolean isAlphabeticADigit(String pattern, int i) {                // ilgili basamağın temiz( sonrasında işaret olmadığını) harf olup olmadığını kontrol eder.
        return Character.isAlphabetic(pattern.charAt(i)) && Character.isAlphabetic(pattern.charAt(i + 1));
    }

    public static String brace(String pattern, int indexOf) {            // ilgili parametrelere göre  parantez bloğunu çıktı olarak verir.
        int firstBraceIndex = indexOf;
        int lastBraceIndex = 0;
        for (int i = firstBraceIndex + 1; i < pattern.length(); i++) {
            if (pattern.charAt(i) == ')') {
                lastBraceIndex = i;
                break;
            }
        }
        return pattern.substring(firstBraceIndex + 1, lastBraceIndex);
    }

    /**
     * abb0*1 = 5 // abbbbb*1 =8
     *
     * @param pattern
     * @param braceList
     * @return
     */
    public static String takeOutTheBraces(String pattern, ArrayList<String> braceList) {
        String newPattern ="";
        first:
        for (int i = 0; i < pattern.length(); i++) {
            if (Character.isDigit(pattern.charAt(i))) {
                for (int j = 0; j < braceList.size(); j++) {
                    if (String.valueOf(pattern.charAt(i)).equals(String.valueOf(j))) {
                        if (i + 1 == pattern.length()) {
                            newPattern+= braceAndPlusProcess(braceList.get(j));
                            break ;
                        } else if (pattern.charAt(i + 1) == '*') {
                            newPattern += derivative(braceAndPlusProcess(braceList.get(j)));    // Attention
                        } else if ((pattern.charAt(i + 1) != '*')) {
                            pattern = pattern.replace(String.valueOf(pattern.charAt(i)), braceAndPlusProcess(braceList.get(j)));
                        }
                    }
                }
            } else
                newPattern += pattern.charAt(i); // abbaaab
        }
        return newPattern.replaceAll("\\*", "");
    }

    public static String derivative(String str) {
        Random random = new Random();
        int x = random.nextInt(1, 7);
        return str.repeat(x);
    }

    public static String plus(String str) {
        Random random = new Random();
        String[] strArr = str.split("\\+");
        int i = random.nextInt(strArr.length);
        return strArr[i];
    }

    public static String cleanPattern(String str) {
        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (i + 1 == str.length()) {                             // outofbound uyarsısı almamak için                                                        // OUTOFBOND ERROR
                if (Character.isAlphabetic(str.charAt(i))) {           // harf ise yaz ve çık
                    newStr += str.charAt(i);
                    break;
                } else {
                    newStr += str.charAt(i);
                    break;
                }
            } else if (Character.isAlphabetic(str.charAt(i))) {
                if (isAlphabeticADigit(str, i)) {    //  char ın temiz( sonrasında işaret olmadığını) alfabetik olup olmadığına bkıyoruz                                                                   //ALPHABETIC
                    newStr += str.charAt(i);
                } else if (str.charAt(i + 1) == '*') {
                    newStr += derivative(str.substring(i, i + 1));
                    i++;
                }
            } else
                newStr += str.charAt(i);
        }
        return newStr;
    }

    public static void canWordBe(String str, String pattern) {
        try {
            ArrayList<String> arrayList = patternWithoutRegex(pattern, 1000);
            int a = 0;
            for (int i = 0; i < patternWithoutRegex(pattern, 1000).size(); i++) {
                if (str.equals(arrayList.get(i))) {
                    a++;
                    break;
                }
            }
            if (a > 0) {
                System.out.println("Yes, this word can be made from the pattern");
            } else
                System.out.println("No, this word can not made from the pattern");
        } catch (Exception e) {
            System.out.println("canbeword");
        }
    }
}
// ab*(a+b)*(a+b+ab)+(a+b)
// abb*(ab+ba+aaa+bbb)*+(ba+a)*
// abbbaaaab