package jvmTest;

public class CodeSortTest {
    public static int k = 0;
    public static CodeSortTest s1 = new CodeSortTest("s1");
    public static CodeSortTest s2 = new CodeSortTest("s2");
    public static int i = print("i");
    public static int n = 99;
    public int j = print("k");
    {
        print("构造块");
    }
    static {
        print("静态块");
    }
    public static int print(String s) {
        System.out.println(++k + ":" + s + "\ti=" + i + "\tn=" + n);
        ++n;
        return ++i;
    }
    public CodeSortTest(String s) {
        System.out.println(++k + ":" + s + "\ti=" + i + "\tn=" + n);
        ++i;
        ++n;
    }
    public static void main(String[] args) {
        new CodeSortTest("init");
    }
}
