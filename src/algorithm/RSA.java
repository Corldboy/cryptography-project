package algorithm;
import java.math.BigInteger;

import static utils.MathUtil.*;
import static utils.UtilChar.toChar;

public class RSA {
    private char[]p;
    private char[]q;

    public static char[] getN() {
        return n;
    }

    private static char[]n;
    private static char[]e;

    public static char[] getE() {
        return e;
    }

    public static char[] getD() {
        return d;
    }

    private static char[]d;
    private static char[]phi;
    public RSA(char[]p,char[]q,char[]e){//��������������
        this.q=q;
        this.p=p;
        //����n
        n = bigMultiBig(p,q);
        char[]one = new char[1];
        one[0] = '1';
        //����phi
        char[]pone = bigSubBig(p,one);
        char[]qone = bigSubBig(q,one);
        phi = bigMultiBig(pone,qone);
        //���ѡȡe ȡeΪ����65537\
        this.e = e;
        BigInteger ee = new BigInteger(String.valueOf(e));
        //����d ��e����phi��ģ��Ԫ��
        BigInteger newD = ee.modPow(new BigInteger("-1"),new BigInteger(String.valueOf(phi)));
        d = newD.toString().toCharArray();
    }
    public static void getParemeters(RSA rsa){
        System.out.println("RSA�������� ");
        System.out.println("p "+String.valueOf(rsa.p));
        System.out.println("q "+String.valueOf(rsa.q));
        System.out.println("phi "+String.valueOf(phi));
        System.out.println("e "+String.valueOf(e));
        System.out.println("d "+String.valueOf(d));
        System.out.println("n "+String.valueOf(n));
    }
    public static void alphaTable(RSA r){
        for(int i=0;i<26;i++){
            long c = r.encrypt((char)('a'+i),e,n);
            System.out.println("���� "+c);
            System.out.println("���� "+decrypt(c,d,n));
            System.out.println();
        }
    }
    public static void main(String[]args){
        char []p = "885320963".toCharArray();
        char []q = "238855417".toCharArray();
        RSA r = new RSA(p,q,"1001".toCharArray());
        //getParemeters(r);
        char[]plaintext = toChar("china is beautiful");
        long []cipher = encrypt(plaintext,e,n);
        char[]decipher = decrypt(cipher,d,n);
        System.out.println(String.valueOf(decipher));
    }

    /**
     * �Ե��ַ�m���м���
     * @param m
     * @param e
     * @param n
     * @return cipher
     */
    public static long encrypt(char m,char[]e,char[]n){  //m^e mod n
        long a = m;
        long b = Long.parseLong(String.valueOf(e));
        long c = Long.parseLong(String.valueOf(n));
        long cipher = mulmod(a,b,c);
        return cipher;
    }

    /**
     * ��cipher���н���
     * @param cipher
     * @param d
     * @param n
     * @return
     */
    public static char decrypt(long cipher,char[]d,char[]n){//��˽Կd���н���
        long a = cipher;
        long b = Long.parseLong(String.valueOf(d));
        long c = Long.parseLong(String.valueOf(n));
        long plainchar = mulmod(a,b,c);
        return (char)(plainchar);
    }

    /**
     * �ù�Կ��һ�������ַ�������
     * @param plaintext
     * @param e
     * @param n
     * @return
     */
    public static long[] encrypt(char[]plaintext,char[]e,char[]n){//�ù�Կ��(e,n)���м���
        long[]cipher = new long[plaintext.length];
        for(int i=0;i<plaintext.length;i++){
            cipher[i] = encrypt(plaintext[i],e,n);
        }
        return cipher;
    }

    /**
     * ��һ�������ַ�������˽Կd��p��q����
     * @param cipher
     * @param d
     * @param n
     * @return
     */
    public static char[] decrypt(long []cipher,char[]d,char[]n){
        char[]plainttext = new char[cipher.length];
        for(int i=0;i<plainttext.length;i++){
            plainttext[i] = decrypt(cipher[i],d,n);
        }
        return plainttext;
    }
}